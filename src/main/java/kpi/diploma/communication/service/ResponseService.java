package kpi.diploma.communication.service;

import kpi.diploma.communication.data.ResponseRepository;
import kpi.diploma.communication.dto.ResponseDTO;
import kpi.diploma.communication.model.Comment;
import kpi.diploma.communication.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private UserService userService;
    public List<ResponseDTO> getResponsesForComment(Comment comment){
        List<Response> responses = responseRepository.findResponsesByCommentToWhichReply(comment);
        return parsingResponsesDTO(responses);

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
