package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentRepository  extends CrudRepository<Comment, Long> {
    List<Comment> findCommentsByPostId(Long id, Sort sort);
}
