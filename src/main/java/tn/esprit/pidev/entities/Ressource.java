package tn.esprit.pidev.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Ressource")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ressource {
    @Id
    private String idRessource;
    private String nomRessource;
    private String photo;

    @DBRef
    private Cours cours;


}
