package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {

}
