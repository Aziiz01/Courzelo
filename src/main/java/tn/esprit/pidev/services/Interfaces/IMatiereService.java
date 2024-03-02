package tn.esprit.pidev.services.Interfaces;


import org.springframework.http.ResponseEntity;
import tn.esprit.pidev.entities.Matiere;

import java.util.List;
import java.util.Optional;


public interface IMatiereService {
    public ResponseEntity<?> addMatiere(Matiere matiere);
    public Matiere updateMatiere(String id_matiere, Matiere updatedmatiere);
    public List<Matiere> getAllMatiere();
    public Matiere getMatiereById(String id_matiere);
    public void deleteMatiere(String id_matiere);
    public Optional<Matiere> getMatiereByNom(String nom_matiere) ;

    }
