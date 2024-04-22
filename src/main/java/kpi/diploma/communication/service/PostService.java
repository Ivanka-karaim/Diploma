package kpi.diploma.communication.service;

import kpi.diploma.communication.data.PostForUserRepository;
import kpi.diploma.communication.data.PostRepository;
import kpi.diploma.communication.dto.PostDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.PostUser;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public List<PostDTO> getPostsForUser(User user, int page) {
        Pageable pageable = PageRequest.of((page - 1) * COUNT_POSTS, COUNT_POSTS, Sort.by("email"));
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
                .author(new UserDTO(post.getAuthor().getEmail(), post.getAuthor().getName(),
                        post.getAuthor().getSurname(), post.getAuthor().getPatronymic(),
                        post.getAuthor().getRoles()))
                .dateTime(post.getDateTime())
                .build();
    }


}



