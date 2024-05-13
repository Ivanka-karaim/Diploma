package kpi.diploma.communication.data;

import kpi.diploma.communication.model.PostUser;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostForUserRepository extends JpaRepository<PostUser, Long> {
    Optional<List<PostUser>> findPostUsersByUser(User user, Pageable pageable);
    @Query("SELECT pu.user FROM PostUser pu WHERE pu.post.id = :id ")
    List<User> findUsersByPost(@Param("id") Long id);
    List<PostUser> findByUserEmailAndPostId(String userEmail, Long postId);

    void deletePostUserByPost(Post post);




}
