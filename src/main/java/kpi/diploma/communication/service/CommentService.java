package kpi.diploma.communication.service;

import kpi.diploma.communication.data.CommentRepository;
import kpi.diploma.communication.data.PostRepository;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<CommentDTO> getCommentsForPost(Long postId){

        List<Comment> comments = commentRepository.findCommentsByPostId(postId, Sort.by(Sort.Direction.DESC, "dateTime"));
        return parsingCommentDTO(comments);

    }
    public boolean saveComment(String userName, String text, Long postId){
        try {
            User user = userService.getUserById(userName);
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setText(text);
            comment.setDateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            comment.setPost(postRepository.findById(postId).orElseThrow());
            commentRepository.save(comment);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }

    private List<CommentDTO> parsingCommentDTO(List<Comment> list) {
        List<CommentDTO> CommentDTOs = new ArrayList<>();
        for (Comment comment : list) {
            CommentDTOs.add(CommentDTO.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .dateTime(comment.getDateTime())
                    .user(userService.getUserDTO(comment.getUser()))
                    .responses(responseService.getResponsesForComment(comment))
                    .build());
        }
        return CommentDTOs;
    }
}
