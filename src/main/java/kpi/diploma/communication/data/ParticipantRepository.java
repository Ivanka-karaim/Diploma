package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Chat;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Participant;
import kpi.diploma.communication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository  extends CrudRepository<Participant, Long> {
    @Query("SELECT p1.chat FROM Participant p1 JOIN Participant p2 ON p1.chat = p2.chat WHERE p1.user.email = :user1 AND p2.user.email = :user2")
    Optional<Chat> findChatByUsers(@Param("user1") String user1, @Param("user2") String user2);
}
