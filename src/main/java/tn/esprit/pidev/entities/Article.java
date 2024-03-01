package tn.esprit.pidev.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection ="Article")
public class Article {
    @Id
    private String idArticle;
    private String titre;
    private int score;
    private String category;
    private String createdBy;
    private Date createdAt;
    @DBRef
    private List<Post> posts;
    private String userId;
}
