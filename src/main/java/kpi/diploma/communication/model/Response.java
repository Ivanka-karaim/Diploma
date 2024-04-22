package kpi.diploma.communication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="response")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="to_comment_id")
    @NotNull
    private Comment commentToWhichReply;

    @NotNull
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private User user;

    @NotNull
    private Timestamp dateTime;
}
