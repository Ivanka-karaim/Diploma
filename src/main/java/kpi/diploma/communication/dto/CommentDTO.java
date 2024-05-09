package kpi.diploma.communication.dto;


import kpi.diploma.communication.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long id;
    private String text;
    private UserDTO user;
    private Timestamp dateTime;
    private ResponseDTO responseAI=null;
    private List<ResponseDTO> responses;
}
