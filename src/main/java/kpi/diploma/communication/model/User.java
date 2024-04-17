package kpi.diploma.communication.model;


import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="usr")
public class User {
    @Id
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String surname;

    @NotNull
    private String patronymic;

    @NotNull
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

}
