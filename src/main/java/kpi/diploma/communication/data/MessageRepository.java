package kpi.diploma.communication.data;

import kpi.diploma.communication.dto.ChatMessage;
import kpi.diploma.communication.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findMessagesByChatTitle(String title);
}
