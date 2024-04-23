package kpi.diploma.communication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Timestamp dateTime;

    @NotNull
    private String description;

    @NotNull
    private String type;

    @ManyToOne
    @JoinColumn(name="author_id")
    @NotNull
    private User author;



}
