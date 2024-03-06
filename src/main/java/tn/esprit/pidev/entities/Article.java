package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private List<String> score;
    private String category;
    private List<String> followedBy;
    private Date createdAt;
    @DBRef(lazy = true)
    @JsonIgnoreProperties("article")
    private List<Post> posts;
    private String userId;
}
