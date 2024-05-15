package kpi.diploma.communication.data;

import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.model.Message;
import kpi.diploma.communication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findMessagesByChatTitle(String title);
    @Query("SELECT m.user FROM Message m WHERE m.recipient.email = :recipientEmail AND m.viewed=false ")
    List<User> findUsersByRecipientEmail(String recipientEmail);
//    @Query(value = "SELECT * FROM message m INNER JOIN ( SELECT chat_id, MAX(date_time) AS last_message_time FROM message WHERE recipient_id = :userEmail OR user_id = :userEmail GROUP BY chat_id ) last_messages ON m.chat_id = last_messages.chat_id AND m.date_time = last_messages.last_message_time ORDER BY m.date_time DESC", nativeQuery = true)
//    List<Message> findMessagesByUserEmailAndGroupChatAndSortTime(@Param("userEmail") String userEmail);
//    @Query(value = "SELECT * FROM message m INNER JOIN ( SELECT chat_id, MAX(date_time) AS last_message_time FROM message WHERE recipient_id = :userEmail OR user_id = :userEmail GROUP BY chat_id ) last_messages ON m.chat_id = last_messages.chat_id AND m.date_time = last_messages.last_message_time ORDER BY m.date_time DESC", nativeQuery = true)
//    List<Message> findMessagesByUserEmailAndGroupChatAndSortTime(@Param("userEmail") String userEmail);
    @Query(value = "SELECT * FROM message m INNER JOIN ( SELECT chat_id AS chat_id_last_messages, MAX(date_time) AS last_message_time FROM message WHERE recipient_id = :userEmail OR user_id = :userEmail GROUP BY chat_id ) last_messages ON m.chat_id = last_messages.chat_id_last_messages AND m.date_time = last_messages.last_message_time ORDER BY m.date_time DESC", nativeQuery = true)
    List<Message> findMessagesByUserEmailAndGroupChatAndSortTime(@Param("userEmail") String userEmail);

    @Query("SELECT COUNT(*) AS unread_message_count FROM Message m WHERE m.recipient.email = :userEmail AND m.chat.id = :chatId AND m.viewed = false")
    int getCountUnReadMessage(@Param("userEmail") String userEmail, @Param("chatId") Long chatId);
}
