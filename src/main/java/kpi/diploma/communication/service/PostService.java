package kpi.diploma.communication.service;

import jakarta.transaction.Transactional;
import kpi.diploma.communication.data.PostForUserRepository;
import kpi.diploma.communication.data.PostRepository;
import kpi.diploma.communication.data.SavedRepository;
import kpi.diploma.communication.data.UserRepository;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    public final int COUNT_POSTS = 20;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostForUserRepository postForUserRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedRepository savedRepository;

    @Autowired
    private CommentService commentService;
    @Transactional
    public void removePost(Long postId, String authorEmail) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && Objects.equals(post.getAuthor().getEmail(), authorEmail)) {
            savedRepository.deleteSavedsByPost(post);
            commentService.removeCommentsByPost(post);
            postForUserRepository.deletePostUserByPost(post);
            postRepository.delete(post);
        }else{
            throw new RuntimeException();
        }
    }

    public List<PostDTO> getListSaved(String userEmail) {
        List<Post> posts = savedRepository.findPostByUserEmail(userEmail);
        return parsePostListToDTO(posts);

    }

    public boolean checkUserHasAccessForPost(String userEmail, Long postId) {
        List<PostUser> postUser = postForUserRepository.findByUserEmailAndPostId(userEmail, postId);
        return !postUser.isEmpty();

    }


    public void savedPost(String userEmail, Long postId) {
        Saved savedPost = savedRepository.findByUserEmailAndPostId(userEmail, postId).orElse(null);
        if (savedPost != null) {
            savedRepository.delete(savedPost);
        } else {
            User user = userService.getUserById(userEmail);
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null && user != null) {
                Saved saved = Saved.builder()
                        .user(user)
                        .post(post).build();
                savedRepository.save(saved);
            } else {
                throw new RuntimeException("Not found post or user");
            }
        }
    }

    private void addPost(String authorEmail, List<User> users, String title, String description) {
        User author = userService.getUserById(authorEmail);
        Post post = Post.builder()
                .author(author)
                .dateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .description(description)
                .title(title)
                .type("IMPORTANT")
                .build();
        postRepository.save(post);
        for (User user : users) {
            PostUser postUser = PostUser.builder()
                    .post(post)
                    .user(user)
                    .build();
            postForUserRepository.save(postUser);
        }

    }

    public void addPostForUsers(String authorEmail, List<String> listUser, String title, String description) {
        List<User> users = new ArrayList<>();
        for (String user : listUser) {
            users.add(userService.getUserById(user));

        }
        addPost(authorEmail, users, title, description);

    }

    public void addPostForStudents(String authorEmail, List<String> groups, String title, String description, boolean isLeaderGroup, boolean isCurator) {
        List<User> users = new ArrayList<>();

        for (String group : groups) {
            users.addAll(userRepository.findByGroupTitle(group));
        }
        List<User> filteredUsers;
        if (isLeaderGroup && isCurator) {
            filteredUsers = users.stream()
                    .filter(user -> user.getRoles().containsAll(List.of(Role.CURATOR, Role.LEADER_GROUP)))
                    .toList();
        } else if (isCurator) {
            filteredUsers = users.stream()
                    .filter(user -> user.getRoles().contains(Role.CURATOR))
                    .toList();
        } else if (isLeaderGroup) {
            filteredUsers = users.stream()
                    .filter(user -> user.getRoles().contains(Role.LEADER_GROUP))
                    .toList();
        } else {
            filteredUsers = users;
        }
        addPost(authorEmail, filteredUsers, title, description);

    }

    public List<PostDTO> getPostsForAuthor(String authorEmail) {
        List<Post> posts = postRepository.findPostsByAuthorEmailOrderByDateTimeDesc(authorEmail);
        return parsePostListToDTO(posts);
    }


    public List<PostDTO> getPostsForUser(User user, int page) {
        Pageable pageable = PageRequest.of((page - 1) * COUNT_POSTS, COUNT_POSTS, Sort.by(Sort.Direction.DESC, "post.dateTime"));
        Optional<List<PostUser>> postsForUser = postForUserRepository.findPostUsersByUser(user, pageable);
        return parsePostListToDTO(postsForUser.map(postUsers ->
                        postUsers.stream()
                                .map(PostUser::getPost)
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("No posts found for the user")));
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return post != null ? parsingPostDTO(post) : null;

    }


    private List<PostDTO> parsePostListToDTO(List<Post> posts) {
        return posts.stream()
                .map(this::parsingPostDTO)
                .collect(Collectors.toList());
    }


    private PostDTO parsingPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .type(post.getType())
                .author(userService.getUserDTO(post.getAuthor()))
                .dateTime(post.getDateTime())
                .build();
    }


}



