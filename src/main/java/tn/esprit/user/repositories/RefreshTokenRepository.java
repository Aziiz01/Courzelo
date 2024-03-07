package tn.esprit.user.repositories;

import tn.esprit.user.entities.RefreshToken;
import tn.esprit.user.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    RefreshToken findByToken(String token);
    List<RefreshToken> findAllByUser(User user);
}
