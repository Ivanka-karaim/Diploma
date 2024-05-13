package kpi.diploma.communication.service;

import kpi.diploma.communication.data.CommentRepository;
import kpi.diploma.communication.data.PostRepository;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.ResponseDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Post;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Value("${aiID}")
    private String aiID;

    public void removeCommentsByPost(Post post){
        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId(), null);
        for(Comment comment:comments){
            responseService.removeResponse(comment);
        }
        commentRepository.deleteCommentsByPost(post);
    }

    public List<CommentDTO> getCommentsForPost(Long postId){

        List<Comment> comments = commentRepository.findCommentsByPostId(postId, Sort.by(Sort.Direction.DESC, "dateTime"));
        return parsingCommentDTO(comments);

    }
    public Comment saveComment(String userName, String text, Long postId){
        try {
            User user = userService.getUserById(userName);
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setText(text);
            comment.setDateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            comment.setPost(postRepository.findById(postId).orElseThrow());
            commentRepository.save(comment);
            return comment;
        }catch (Exception e){
            throw new RuntimeException();
        }


    }

    private List<CommentDTO> parsingCommentDTO(List<Comment> list) {
        List<CommentDTO> CommentDTOs = new ArrayList<>();
        for (Comment comment : list) {
            List<ResponseDTO> responseDTOS = responseService.getResponsesForComment(comment);
            Optional<ResponseDTO> ai = responseDTOS.stream()
                    .filter(responseDTO -> responseDTO.getUser().getEmail().equals(aiID))
                    .findFirst();

            ai.ifPresent(responseDTOS::remove);

            CommentDTOs.add(CommentDTO.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .dateTime(comment.getDateTime())
                    .user(userService.getUserDTO(comment.getUser()))
                    .responses(responseDTOS)
                    .responseAI(ai.orElse(null))
                    .build());
        }
        return CommentDTOs;
    }
}
