package tn.esprit.pidev.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Attempt")
public class Attempt {
    @Id
    private String Attemptid;


    private String username;
    @DBRef
    private Quiz quiz;
    private  long quizId;

}
