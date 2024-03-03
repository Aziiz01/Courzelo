package tn.esprit.pidev.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Avis")

public class Avis {
    @Id
    private String idEvaluation;
    private String titre;
    private boolean visibilite;
    private Date dateEvaluation;
    private int starRating;
}
