package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Attempt;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;

import java.util.Set;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    Set<Question> findByQuiz(Quiz quiz);

}
