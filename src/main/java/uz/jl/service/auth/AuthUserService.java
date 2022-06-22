package uz.jl.service.auth;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import uz.jl.Colors;
import uz.jl.configs.ApplicationContextHolder;
import uz.jl.configs.PasswordConfigurer;
import uz.jl.dao.AbstractDAO;
import uz.jl.dao.auth.AuthUserDAO;
import uz.jl.domains.auth.AuthAnswer;
import uz.jl.domains.auth.AuthTest;
import uz.jl.domains.auth.AuthUser;
import uz.jl.enums.AuthRole;
import uz.jl.service.GenericCRUDService;
import uz.jl.settings.Lang;
import uz.jl.utils.BaseUtils;
import uz.jl.vo.auth.AuthUserCreateVO;
import uz.jl.vo.auth.AuthUserUpdateVO;
import uz.jl.vo.auth.AuthUserVO;
import uz.jl.vo.auth.Session;
import uz.jl.vo.http.Response;

import java.util.*;

public class AuthUserService extends AbstractDAO<AuthUserDAO> implements GenericCRUDService<
        AuthUserVO,
        AuthUserCreateVO,
        AuthUserUpdateVO,
        Long> {

    private static AuthUserService instance;
    //    private final AuthUserValidator validator;
    static Lang lang = Lang.getInstance();

    private AuthUserService() {
        super(
                ApplicationContextHolder.getBean(AuthUserDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }

    @Override
    @Transactional
    public Response<Long> create(@NonNull AuthUserCreateVO vo) {
        // TODO: 6/21/2022 validate input
        Optional<AuthUser> optionalAuthUser = dao.findByUserName(vo.getUsername());
        if (optionalAuthUser.isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        AuthUser authUser = AuthUser
                .childBuilder()
                .username(vo.getUsername())
                .password(utils.encode(vo.getPassword()))
                .email(vo.getEmail())
                .role(AuthRole.USER)
                .language(vo.getLanguage())
                .build();
        dao.save(authUser);
        return new Response<>(authUser.getId());
    }

    @Override
    public Response<Void> update(@NonNull AuthUserUpdateVO vo) {
        return null;
    }

    @Override
    public Response<Void> delete(@NonNull Long id) {
        return null;
    }

    @Override
    public Response<AuthUserVO> get(@NonNull Long id) {
        return null;
    }

    @Override
    public Response<List<AuthUserVO>> getAll() {
        return null;
    }

    public static AuthUserService getInstance() {
        if (instance == null) {
            instance = new AuthUserService();
        }
        return instance;
    }

    public Response<AuthUserVO> login(String username, String password) {
        Optional<AuthUser> response = dao.findByUserName(username);

        if (response.isEmpty()) {
            throw new RuntimeException("Username does not exist!");
        }

        AuthUser authUser = response.get();
        if (!utils.matchPassword(password, authUser.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }


        AuthUserVO authUserVO = AuthUserVO.builder()
                .username(authUser.getUsername())
                .email(authUser.getEmail())
                .createdAt(authUser.getCreatedAt())
                .role(authUser.getRole())
                .lang(authUser.getLanguage())
                .build();
        Session.setSessionUser(authUserVO);
        return new Response<>(authUserVO);
    }


    public void register() {

    }


    public List<AuthTest> STest(List<AuthTest> tests, String option) {
        List<AuthTest> currentTest = new ArrayList<>();
        Set<Integer> numbers = new TreeSet<>();

        int opt = 0;
        try {
            opt = Integer.parseInt(option);

            if (opt > tests.size()) {
                uz.jl.BaseUtils.println(lang.lang("Invalid number "));
            }

        } catch (Exception e) {
            uz.jl.BaseUtils.println(lang.lang(e.getMessage()));
        }

        while (numbers.size() != opt) {

            numbers.add(new Random().nextInt(0, opt));
        }
        if (tests.size() == 1) {
            currentTest.add(tests.get(0));
        }

        for (Integer number : numbers) {
            AuthTest test = tests.get(number);

            AuthAnswer authAnswer = new AuthAnswer(
                    lang.lang(test.getAnswerList().get(0).getVariant_A()),
                    lang.lang(test.getAnswerList().get(0).getVariant_B()),
                    lang.lang(test.getAnswerList().get(0).getVariant_D())

            );
            AuthTest authTest = new AuthTest(lang.lang(test.getTitle()), Arrays.asList(authAnswer), lang.lang(test.getCorrectAns())


            );

            currentTest.add(authTest);
        }
        return currentTest;

    }
}
