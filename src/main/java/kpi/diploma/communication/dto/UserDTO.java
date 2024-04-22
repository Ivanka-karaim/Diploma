package kpi.diploma.communication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kpi.diploma.communication.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private Set<Role> roles;
}
