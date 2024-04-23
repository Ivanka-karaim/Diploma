package kpi.diploma.communication.data;

import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Response;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Long> {
    List<Response> findResponsesByCommentToWhichReply(Comment comment, Sort sort);
}
