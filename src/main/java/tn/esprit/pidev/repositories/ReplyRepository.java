package tn.esprit.pidev.repositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.PidevApplication;
import tn.esprit.pidev.entities.Reply;

@Repository
public interface ReplyRepository extends MongoRepository<Reply,String> {

}
