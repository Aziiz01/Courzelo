package tn.esprit.pidev.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "Reply")
public class Re_reply {
    @Id
    private String idRe_reply;
    private int idUser;
    private String context;
    private int recommondations;
    private boolean visibility;
    private String replyId;



}
