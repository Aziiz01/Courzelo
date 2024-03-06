package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}
