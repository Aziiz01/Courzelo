package tn.esprit.pidev.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> getPostsByArticleId(String articleId);
    @Query("{ 'followedBy' : ?0 }")
    List<Post> getFollowedPostsByUserId(String userId);
}
