package uz.jl.ui;


import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthAnswer;
import uz.jl.domains.auth.AuthTest;
import uz.jl.enums.AuthRole;
import uz.jl.enums.Language;
import uz.jl.service.auth.AuthUserService;
import uz.jl.settings.Lang;
import uz.jl.vo.auth.AuthTestVo;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AuthUI {
    static AuthUserService service = ApplicationContextHolder.getBean(AuthUserService.class);
    static AuthUI authUI = new AuthUI();
    static Lang lang = Lang.getInstance();
    static AuthUserDAO dao = AuthUserDAO.getInstance();

    public static void main(String[] args) {
        try {


            if (Objects.isNull(Session.sessionUser)) {
                BaseUtils.println(lang.lang("Login") + "-> 1");
                BaseUtils.println(lang.lang("Register") + "-> 2");
            } else if (Session.sessionUser.getRole().equals(AuthRole.USER)) {

//


                BaseUtils.println((lang.lang("Start quiz") + " -> 3"));
                BaseUtils.println(lang.lang("Settings ") + "-> 4");
                BaseUtils.println(lang.lang("Logout") + " -> 0");
            } else if (Session.sessionUser.getRole().equals(AuthRole.TEACHER)) {
                BaseUtils.println(lang.lang("Settings ") + "-> 4");
                BaseUtils.println(lang.lang("Test settings ") + "-> 5");
                BaseUtils.println(lang.lang("Show statistics ") + "-> 6");
                BaseUtils.println(lang.lang("Logout ") + "-> 0");
            } else {
                BaseUtils.println(lang.lang("Settings ") + "-> 4");
                BaseUtils.println(lang.lang("Test settings ") + "-> 5");
                BaseUtils.println(lang.lang("Show statistics ") + "-> 6");
                BaseUtils.println(lang.lang("Subject settings ") + "-> 7");
                BaseUtils.println(lang.lang("Change role ") + "-> 8");
                BaseUtils.println(lang.lang("Log out ") + "-> 0");
            }

            BaseUtils.println(lang.lang("Quit ") + "-> q");
            String choice = BaseUtils.readText("?:");

            switch (choice) {
                case "1" -> authUI.login();
                case "2" -> authUI.register();
                case "3" -> authUI.startQuiz();
                case "4" -> authUI.Settings();
                case "5" -> TestUI.testCrud();
                case "6" -> authUI.showStatistics();
                case "7" -> authUI.subjectCrud();
                case "8" -> authUI.changeRole();
                case "0" -> authUI.logout();
                case "q" -> {
                    BaseUtils.println(lang.lang("Bye"), Colors.CYAN);
                    System.exit(0);
                }
                default -> BaseUtils.println(lang.lang("Wrong Choice"), Colors.RED);
            }
        } catch (Exception e) {
            System.out.println(lang.lang(e.getMessage()));
            e.printStackTrace();
        }
        main(args);
    }

    private void changeRole() {

    }

    private void subjectCrud() {

    }

    private void showStatistics() {

    }


    private void Settings() {


        BaseUtils.println(lang.lang("Select language  "));
        BaseUtils.println(Session.sessionUser.getLang().toString() + " ✔️  ");
        Map<Integer, Language> languages = new TreeMap<>();

        List<Language> lan = new ArrayList<>(Arrays.asList(Language.ENG, Language.RU, Language.UZ));
        Integer i = 1;

        for (Language language : lan) {
            if (!language.equals(Session.sessionUser.getLang())) {
                languages.put(i, language);
                i++;
            }
        }


        languages.forEach((integer, language) -> BaseUtils.println(integer + " -> " + language.toString()));


        String option = BaseUtils.readText(" ? ");

        if (Integer.parseInt(option) > languages.size()) {
            BaseUtils.println(lang.lang("Error"));
        }


        Session.sessionUser.setLang(languages.get(Integer.parseInt(option)));

//        dao.update();

    }

    private void startQuiz() {
        List<AuthTest> tests = dao.getAllTest();
        BaseUtils.println(lang.lang("how many tests do you want to perform (") + (tests.size()) + ")  ->1 ", Colors.GREEN);
        String option = BaseUtils.readText("? ");
        List<AuthTest> sTest = service.STest(tests, option);

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime endTime = start.plusSeconds(sTest.size() * 10L);

        BaseUtils.println(lang.lang("---------------  Start time     ") + start.format(DateTimeFormatter.ofPattern("  dd-MM-yyyy HH:mm:ss  ")) + " ---------------");

        BaseUtils.println(lang.lang("---------------  End time     ") + endTime.format(DateTimeFormatter.ofPattern("  dd-MM-yyyy HH:mm:ss  ")) + " ---------------");
        Map<String, LocalDateTime> resList = new TreeMap<>();
        int i = 0;
        long until = 1;
        for (AuthTest test : sTest) {
            if (until <= 0) {
                BaseUtils.println(lang.lang("time is up"), Colors.RED);
                for (int j = i; i <tests.size(); i++) {
                    resList.put("?", LocalDateTime.now());
                }
                break;
            }
            AuthTestVo vo = new AuthTestVo(test.getTitle(), test.getAnswerList());
            print_response(new Response(vo));
            until = LocalDateTime.now().until(endTime, ChronoUnit.SECONDS);
            BaseUtils.println(lang.lang("You have " + until + " seconds "));
            String res = BaseUtils.readText(lang.lang("Enter your choice : "));

            if (res.equalsIgnoreCase("a")) {
                resList.put(test.getAnswerList().get(0).getVariant_A(), LocalDateTime.now());
            } else if (res.equalsIgnoreCase("b")) {
                resList.put(test.getAnswerList().get(0).getVariant_B(), LocalDateTime.now());

            } else if (res.equalsIgnoreCase("d")) {
                resList.put(test.getAnswerList().get(0).getVariant_D(), LocalDateTime.now());

            } else {
                resList.put("?", LocalDateTime.now());

            }
            i++;
            until = LocalDateTime.now().until(endTime, ChronoUnit.SECONDS);


        }
        i = 0;
        for (Map.Entry<String, LocalDateTime> entry : resList.entrySet()) {

            if (entry.getValue().isAfter(endTime)) {
                if (tests.get(i).getCorrectAns().equalsIgnoreCase(entry.getKey())) {
                    BaseUtils.println(" >>>> ");
                } else {
                    BaseUtils.println("?????");
                }
            } else {
                BaseUtils.println("time out");
            }

            i++;
        }


    }


    private void logout() {
        Session.sessionUser = null;
    }

    private void register() {
        AuthUserCreateVO vo = AuthUserCreateVO.builder()
                .username(BaseUtils.readText(lang.lang("Enter username: ")))
                .email(BaseUtils.readText(lang.lang("Enter email: ")))
                .password(BaseUtils.readText(lang.lang("Enter password: ")))
                .build();


        Language userLang;
        if (Objects.isNull(Session.sessionUser)) {
            userLang = Language.UZ;
        } else
            userLang = Session.sessionUser.getLang();
        BaseUtils.println(lang.lang("Select language "));
        BaseUtils.println(userLang.toString() + " ✔️ \n ");
        Map<Integer, Language> languages = new TreeMap<>();

        List<Language> lan = new ArrayList<>(Arrays.asList(Language.ENG, Language.RU, Language.UZ));
        Integer i = 1;


        for (Language language : lan) {
            if (!language.equals(userLang)) {
                languages.put(i, language);
                i++;
            }
        }

        languages.forEach((integer, language) -> BaseUtils.println(integer + " -> " + language.toString()));


        String option = BaseUtils.readText(" ? ");

        if (Integer.parseInt(option) > languages.size()) {
            BaseUtils.println(lang.lang("Error"));
        }

        vo.setLanguage(languages.get(Integer.parseInt(option)));


        print_response(service.create(vo));
    }

    private void login() {
        String username = BaseUtils.readText(lang.lang("Enter username: "));
        String password = BaseUtils.readText(lang.lang("Enter password: "));
        print_response(service.login(username, password));
    }

    public void print_response(Response response) {
        String color = response.isOk() ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response.getBody()), color);
    }
}
