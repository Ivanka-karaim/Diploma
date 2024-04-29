package kpi.diploma.communication.service;

import kpi.diploma.communication.data.PostForUserRepository;
import kpi.diploma.communication.data.PostRepository;
import kpi.diploma.communication.data.UserRepository;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Group;
import kpi.diploma.communication.model.PostUser;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private void addPost(String authorEmail, List<User> users, PostDTO postDTO ){
        User author = userService.getUserById(authorEmail);
        Post post = Post.builder()
                .author(author)
                .dateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .description(postDTO.getDescription())
                .title(postDTO.getTitle())
                .type(postDTO.getType())
                .build();
        postRepository.save(post);
        for(User user:users){
            PostUser postUser = PostUser.builder()
                    .post(post)
                    .user(user)
                    .build();
            postForUserRepository.save(postUser);
        }

    }

    public void addPostForUsers(String authorEmail,List<String> listUser,PostDTO postDTO){
        List<User> users = new ArrayList<>();
        for(String user: listUser){
            users.add(userService.getUserById(user));

        }
        addPost(authorEmail, users, postDTO);

    }

    public void addPostForStudents(String authorEmail,List<String> groups, PostDTO postDTO){
        List<User> students = new ArrayList<>();
        for(String group: groups) {
            students.addAll( userRepository.findByGroupTitle(group));

        }
        addPost(authorEmail, students, postDTO);

    }

    public List<PostDTO> getPostsForAuthor(String authorEmail){
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



