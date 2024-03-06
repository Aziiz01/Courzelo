package tn.esprit.pidev.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.UserAttempt;

@Repository
public interface AttemptRepository extends MongoRepository<UserAttempt, String> {
}
