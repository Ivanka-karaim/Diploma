package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findPostsByAuthorEmailOrderByDateTimeDesc(String authorEmail);
}
