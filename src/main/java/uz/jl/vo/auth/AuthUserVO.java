package uz.jl.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.jl.enums.AuthRole;
import uz.jl.enums.Language;
import uz.jl.settings.Lang;
import uz.jl.vo.GenericVO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserVO extends GenericVO {
    private String username;
    private String email;
    private AuthRole role;
    private Language lang;
    private Timestamp createdAt;

}
