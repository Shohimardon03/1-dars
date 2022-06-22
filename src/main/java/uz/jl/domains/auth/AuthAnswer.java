package uz.jl.domains.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.jl.domains.Auditable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthAnswer extends Auditable {
    @Column()
    private String variant_A
            ;
    private String variant_B;

    private String variant_D;
}
