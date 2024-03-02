package tn.esprit.pidev.repositories;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Matiere;

import java.util.Optional;

@Repository
@EnableScheduling
@SpringBootApplication
public interface MatiereRepository extends MongoRepository<Matiere,String> {

    @Query("{ 'nom_matiere' : ?0 }")

    Optional<Matiere> findByNom_matiere(String nom_matiere);

}

