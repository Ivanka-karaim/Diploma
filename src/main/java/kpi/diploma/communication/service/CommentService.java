package kpi.diploma.communication.service;

import kpi.diploma.communication.data.CommentRepository;
import kpi.diploma.communication.data.ResponseRepository;
import kpi.diploma.communication.dto.CommentDTO;
import kpi.diploma.communication.dto.ResponseDTO;
import kpi.diploma.communication.dto.UserDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<CommentDTO> getCommentsForPost(Long postId){

        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        return parsingCommentDTO(comments);

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
