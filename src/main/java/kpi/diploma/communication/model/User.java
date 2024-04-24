package kpi.diploma.communication.model;


import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

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
    @JoinColumn(name="group_id")
    private Group group;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean enabled=true;

}
