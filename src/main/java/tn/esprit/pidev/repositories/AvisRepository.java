package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Attempt;
import tn.esprit.pidev.entities.Avis;
@Repository
public interface AvisRepository extends MongoRepository<Avis, String> {
}
