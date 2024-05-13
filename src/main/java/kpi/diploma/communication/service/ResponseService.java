package kpi.diploma.communication.service;

import kpi.diploma.communication.data.CommentRepository;
import kpi.diploma.communication.data.ResponseRepository;
import kpi.diploma.communication.dto.ResponseDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Response;
import kpi.diploma.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;
    public List<ResponseDTO> getResponsesForComment(Comment comment){
        List<Response> responses = responseRepository.findResponsesByCommentToWhichReply(comment, Sort.by(Sort.Direction.DESC, "dateTime"));
        return parsingResponsesDTO(responses);

    }
    public void removeResponse(Comment comment){
        responseRepository.deleteResponsesByCommentToWhichReply(comment);
    }

    public boolean saveResponse(String userEmail, String text, Long commentId){
        try {
            User user = userService.getUserById(userEmail);
            Response response = new Response();
            response.setUser(user);
            response.setText(text);
            response.setDateTime(java.sql.Timestamp.valueOf(LocalDateTime.now()));
            response.setCommentToWhichReply(commentRepository.findById(commentId).orElseThrow());
            responseRepository.save(response);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private List<ResponseDTO> parsingResponsesDTO(List<Response> responses){
        List<ResponseDTO> ResponseDTOs = new ArrayList<>();
        for(Response response: responses){
            ResponseDTOs.add(ResponseDTO.builder()
                    .id(response.getId())
                    .dateTime(response.getDateTime())
                    .text(response.getText())
                    .user(userService.getUserDTO(response.getUser()))
                    .build());

        }
        return ResponseDTOs;
    }
}
