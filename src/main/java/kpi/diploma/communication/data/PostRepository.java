package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
