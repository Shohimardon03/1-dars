package uz.jl.domains.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jl.domains.Auditable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board_Column extends Auditable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long order;

    @Column(nullable = false, unique = true)
    private String code;


}
