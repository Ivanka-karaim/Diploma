package kpi.diploma.communication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kpi.diploma.communication.model.Chat;
import kpi.diploma.communication.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalMessage {
    private Long id;
    private UserDTO user;
    private String text;
    private Timestamp dateTime;
    private int countUnreadMessage;
}
