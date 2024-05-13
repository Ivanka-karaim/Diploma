package kpi.diploma.communication.data;

import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.model.Message;
import kpi.diploma.communication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findMessagesByChatTitle(String title);
    @Query("SELECT m.user FROM Message m WHERE m.recipient.email = :recipientEmail AND m.viewed=false ")
    List<User> findUsersByRecipientEmail(String recipientEmail);
}
