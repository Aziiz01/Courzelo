package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Quiz")
public class Quiz {
    @Id
    private String _id;
    private float score;
    private String title;
    private String numberOfQuestions;
    private int attemptsAllowed;
    private boolean randomizeQuestions;
    private boolean visibilite;
    private long dureequiz;
    @DBRef
    @JsonIgnore
    private List<Question> questions;
    @DBRef
    @JsonIgnore
    private List<Attempt> attempts;
    @DBRef
    @JsonIgnore
    private Cour cour;






    // Constructor with title parameter

}


