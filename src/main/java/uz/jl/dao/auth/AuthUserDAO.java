package uz.jl.dao.auth;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import uz.jl.BaseUtils;
import uz.jl.Colors;
import uz.jl.dao.GenericDAO;
import uz.jl.domains.auth.AuthTest;
import uz.jl.domains.auth.AuthUser;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDAO extends GenericDAO<AuthUser, Long> {
    private static AuthUserDAO instance;


    public static AuthUserDAO getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AuthUserDAO();
        }
        return instance;
    }


    public Optional<AuthUser> findByUserName(String username) {
        Session session = getSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        Query<AuthUser> query = session
                .createQuery("select t from AuthUser t where lower(t.username) = lower(:username) ",
                        AuthUser.class);
        query.setParameter("username", username);
        Optional<AuthUser> result = Optional.ofNullable(query.getSingleResultOrNull());


        session.close();
        return result;
    }

    public void changeLang() {
        Session session = getSession();


        session.beginTransaction();

        session.createQuery("update AuthUser  set language = UZ where id = 3 ");
    }

    public List<AuthTest> getAllTest() {
        Session session = getSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();

        }
        return session.createQuery("SELECT a FROM AuthTest a", AuthTest.class).getResultList();
    }


    public void update() {
        /*Authuservo vo*/

        Session session = getSession();

        uz.jl.vo.auth.Session.SessionUser sessionUser = uz.jl.vo.auth.Session.sessionUser;
        Optional<AuthUser> authUser = findByUserName(sessionUser.getUsername());
        AuthUser user = authUser.get();
        session.beginTransaction();
        user.setLanguage(sessionUser.getLang());
        session.getEntityManagerFactory().unwrap(user.getClass());
//        session.update(user.getClass());
        session.getTransaction().commit();
        session.close();

//        Query query;
//        query = session.createQuery("update  AuthUser  set language ='UZ' where username = 'alibek'");
////        query.setParameter("v_language", vo.getStatus());
////        query.setParameter("u_username", vo.getUsername());
//
////        if (Objects.isNull(vo.getEmail())) {
////            query = session.createQuery("update  AuthUser  set email =v_email where lower(username) = lower(u_username)");
////            query.setParameter("v_email", vo.getEmail());
////            query.setParameter("u_username", vo.getUsername());
////        }
////        if (Objects.isNull(vo.getRole())) {
////            query = session.createQuery("update  AuthUser  set role =v_role where lower(username) = lower(u_username)");
////            query.setParameter("v_role", vo.getRole());
////            query.setParameter("u_username", vo.getUsername());
////        }
////        if (Objects.isNull(vo.getStatus())) {
////            query = session.createQuery("update  AuthUser  set status =v_status where lower(username) = lower(u_username)");
////            query.setParameter("v_status", vo.getStatus());
////            query.setParameter("u_username", vo.getUsername());
////        }
////        if (Objects.isNull(vo.getLanguage())) {
////            query = session.createQuery("update  AuthUser  set language =v_language where lower(username) = lower(u_username)");
////            query.setParameter("v_language", vo.getStatus());
////            query.setParameter("u_username", vo.getUsername());
////        }
//        int i = query.executeUpdate();
//        BaseUtils.println(i, Colors.RED);
    }
}
