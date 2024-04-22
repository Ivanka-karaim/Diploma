package kpi.diploma.communication.data;

import kpi.diploma.communication.model.PostUser;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PostForUserRepository extends JpaRepository<PostUser, Long> {
    Optional<List<PostUser>> findPostUsersByUser(User user, Pageable pageable);



}
