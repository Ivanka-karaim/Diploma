package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Chat;
import kpi.diploma.communication.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository  extends CrudRepository<Chat, Long> {
    Optional<Chat> findChatByTitle(String title);

}
