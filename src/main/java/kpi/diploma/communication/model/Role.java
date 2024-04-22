package kpi.diploma.communication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT, TEACHER, CURATOR, RESPONSIBLE, MAIN ;

    @Override
    public String getAuthority() {
        return name();
    }
}
