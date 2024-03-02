package tn.esprit.pidev.repositories;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.stereotype.Repository;

import tn.esprit.pidev.entities.Cours;

import java.util.List;

@Repository
@EnableScheduling
@SpringBootApplication
public interface CoursRepository extends MongoRepository<Cours,String> {
    List<Cours> findAllByNomCours(String  nomCours);
    List<Cours> findAllByOrderByDateInscriptionDesc();
}
