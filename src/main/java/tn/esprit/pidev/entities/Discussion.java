package tn.esprit.pidev.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection ="Discussion")
public class Discussion {
    @Id
    private String idDiscussion;
    @DBRef
    private List<Post> posts;
    @DBRef
    private Article article;
    private String idArticle;

}
