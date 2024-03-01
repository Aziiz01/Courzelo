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
@Document(collection = "Reply")
public class Reply {
    @Id
    private String idReply;
    private int idUser;
    private String context;
    private int recommondations;
    private boolean visibility;
    private String postId;
    @DBRef
    private List<Re_reply> re_replies;



}
