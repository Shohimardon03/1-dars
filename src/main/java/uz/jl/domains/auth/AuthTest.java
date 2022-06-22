package uz.jl.domains.auth;

import jakarta.persistence.*;
import lombok.*;
import uz.jl.domains.Auditable;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthTest extends Auditable {

    @Column(unique = true, nullable = false)
    private String title;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private List<AuthAnswer> answerList=new ArrayList<>();
    @Column(columnDefinition = "text")
    private String correctAns;

}
