package kpi.diploma.communication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="to_comment_id")
    @NotNull
    private Comment commentToWhichReply;

    @ManyToOne
    @JoinColumn(name="comment_id")
    @NotNull
    private Comment comment;
}
