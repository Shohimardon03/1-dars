package uz.jl.ui;


import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.enums.AuthRole;
import uz.jl.service.auth.AuthUserService;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.Response;

import java.util.Objects;

public class AuthUI {
    static AuthUserService service = ApplicationContextHolder.getBean(AuthUserService.class);
    static AuthUI authUI = new AuthUI();

    public static void main(String[] args) {

        if (Objects.isNull(Session.sessionUser)) {
            BaseUtils.println("Login -> 1");
            BaseUtils.println("Register -> 2");
        } else if (Session.sessionUser.getRole().equals(AuthRole.USER)){
            BaseUtils.println("\nStart quiz -> 3");
            BaseUtils.println("Settings -> 4");
            BaseUtils.println("Logout -> 0");
        } else if (Session.sessionUser.getRole().equals(AuthRole.TEACHER)){
            BaseUtils.println("\nSettings -> 4");
            BaseUtils.println("Test CRUD -> 5");
            BaseUtils.println("Show statistics -> 6");
            BaseUtils.println("Logout -> 0");
        } else {
            BaseUtils.println("\nSettings -> 4");
            BaseUtils.println("Test CRUD -> 5");
            BaseUtils.println("Show statistics -> 6");
            BaseUtils.println("Subject CRUD -> 7");
            BaseUtils.println("Change role -> 8");
            BaseUtils.println("Logout -> 0");
        }

        BaseUtils.println("Quit -> q");
        String choice = BaseUtils.readText("?:");
        switch (choice) {
            case "1" -> authUI.login();
            case "2" -> authUI.register();
            case "3" -> authUI.startQuiz();
            case "4" -> authUI.Settings();
            case "5" -> authUI.testCrud();
            case "6" -> authUI.showStatistics();
            case "7" -> authUI.subjectCrud();
            case "8" -> authUI.changeRole();
            case "0" -> authUI.logout();
            case "q" -> {
                BaseUtils.println("Bye", Colors.CYAN);
                System.exit(0);
            }
            default -> BaseUtils.println("Wrong Choice", Colors.RED);
        }
        main(args);
    }

    private void changeRole() {

    }

    private void subjectCrud() {

    }

    private void showStatistics() {

    }

    private void testCrud() {

    }

    private void Settings() {
    }

    private void startQuiz() {

    }


    private void logout() {
        Session.sessionUser = null;
    }

    private void register() {
        AuthUserCreateVO vo = AuthUserCreateVO.builder()
                .username(BaseUtils.readText("Enter username: "))
                .email(BaseUtils.readText("Enter email: "))
                .password(BaseUtils.readText("Enter password: "))
                .build();
        print_response(service.create(vo));
    }

    private void login() {
        String username = BaseUtils.readText("Enter username: ");
        String password = BaseUtils.readText("Enter password: ");
        print_response(service.login(username, password));
    }

    public void print_response(Response response) {
        String color = response.isOk() ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response.getBody()), color);
    }
}
