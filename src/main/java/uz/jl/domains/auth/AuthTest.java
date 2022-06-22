package uz.jl.domains.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jl.domains.Auditable;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTest extends Auditable {

    @Column(unique = true, nullable = false)
    private String title;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private List<AuthAnswer> answerList=new ArrayList<>();

}
