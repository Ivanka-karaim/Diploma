package kpi.diploma.communication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kpi.diploma.communication.model.Comment;
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
public class ResponseDTO {
    private Long id;
    private String text;
    private UserDTO user;
    private Timestamp dateTime;
}
