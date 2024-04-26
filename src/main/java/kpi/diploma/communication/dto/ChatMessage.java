package kpi.diploma.communication.dto;

import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String text;
    private Timestamp timestamp;
}
