package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "Cours")
public class Cours {
    @Id
    @JsonIgnore
    private String id_cours;
    private String nomCours;
    private String nomProfesseur;
    private TypeC typeCours;
    private Date dateInscription;
    private String descriptionCours;
    private Float prix;
    private String photo;
    @DBRef
    @JsonIgnore
    private Matiere matiere;

    @DBRef
    private List<Ressource> ressourceList=new ArrayList<>();



}
