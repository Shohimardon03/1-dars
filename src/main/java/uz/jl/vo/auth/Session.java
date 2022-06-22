package uz.jl.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.enums.AuthRole;
import uz.jl.enums.Language;

public class Session {
    public static SessionUser sessionUser;

    public static void setSessionUser(AuthUserVO session) {
        sessionUser = new SessionUser(session);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SessionUser {
        private Long id;
        private String username;
        private AuthRole role;
        private Language lang;

        public SessionUser(AuthUserVO session) {
            this.id = session.getId();
            this.username = session.getUsername();
            this.role = session.getRole();
            this.lang = session.getLang();
        }
    }
}
