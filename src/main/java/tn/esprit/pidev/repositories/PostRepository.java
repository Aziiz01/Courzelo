package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;
@Repository
public interface PostRepository extends MongoRepository<Post,String> {
}
