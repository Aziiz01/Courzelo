package tn.esprit.user.repositories;

import tn.esprit.user.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserById(String id);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);
}