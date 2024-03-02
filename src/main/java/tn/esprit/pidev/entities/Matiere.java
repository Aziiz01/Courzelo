package tn.esprit.pidev.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "Matiere")
public class Matiere {
    @Id
    private String id_matiere;
    @Field("nom_matiere")
    private String nom_matiere;
    @DBRef
    private Niveau niveau;

}
