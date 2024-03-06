package tn.esprit.pidev.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="UserAttempt")
public class UserAttempt {
    private String userName; // Username of the user attempting the quiz
    private Question question; // The quiz being attempted
    private List<Integer> selectedOptions; // List of selected options by the user for each question
    private int score;
    private List<Question> attemptedQuestions; // List of questions attempted by the user in this quiz attempt



}
