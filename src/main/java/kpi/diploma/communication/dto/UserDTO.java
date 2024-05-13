package kpi.diploma.communication.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

    private String group;
    private String speciality;
    private int course;
    private boolean hasMessage=false;
}
