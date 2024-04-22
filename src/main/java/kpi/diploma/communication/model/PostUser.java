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
@Table(name="post_user")
public class PostUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    @NotNull
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private User user;

}
