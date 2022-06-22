package uz.jl.vo.auth;/*
  @author "Abdurashitov Shohimardon"
  @since 23/06/2022 03:38 (Thursday)
  trello_V/IntelliJ IDEA
*/

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import uz.jl.domains.auth.AuthAnswer;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
public class AuthTestVo {
    private String title;

    private List<AuthAnswer> answerList = new ArrayList<>();
}
