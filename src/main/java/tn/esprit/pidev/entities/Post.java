package tn.esprit.pidev.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Document(collection ="Post")
public class Post {
    @Id
    private String idPost;
    private String titre;
    private String context;
    private List<String> rating;
    private String imageUrl;
    private String idUser;
    private String articleId;
    private List<String> followedBy;
    @DBRef
    private List<Reply> replies;
    private Date createdAt;
    private Date updatedAt;

}
