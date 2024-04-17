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
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name="chat_id")
    @NotNull
    private Chat chat;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private User user;

    @NotNull
    private String text;

    @NotNull
    private Timestamp dateTime;
    
}
