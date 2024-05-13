package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.Response;
import kpi.diploma.communication.model.Saved;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavedRepository extends CrudRepository<Saved, Long> {
    Optional<Saved> findByUserEmailAndPostId(String userEmail, Long postId);

    @Query("SELECT s.post FROM Saved s WHERE s.user.email = :userEmail")
    List<Post> findPostByUserEmail(@Param("userEmail") String userEmail);

    void deleteSavedsByPost(Post post);

}
