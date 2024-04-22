package kpi.diploma.communication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class PostDTO {
    public Long id;
    public String title;
    public Timestamp dateTime;
    public String description;
    public String type;
    public UserDTO author;

}
