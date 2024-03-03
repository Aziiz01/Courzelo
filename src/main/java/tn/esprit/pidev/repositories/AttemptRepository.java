package tn.esprit.pidev.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Attempt;
@Repository
public interface AttemptRepository extends MongoRepository<Attempt, String> {
}
