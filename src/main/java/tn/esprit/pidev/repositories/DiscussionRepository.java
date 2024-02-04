package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Discussion;
@Repository
public interface DiscussionRepository extends MongoRepository<Discussion,String> {
}
