package tn.esprit.user.repositories;

import tn.esprit.user.entities.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
}
