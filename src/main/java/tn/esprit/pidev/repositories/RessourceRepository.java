package tn.esprit.pidev.repositories;

import tn.esprit.pidev.entities.Ressource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRepository extends MongoRepository<Ressource,String> {
}