package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Matiere;
import tn.esprit.pidev.entities.Niveau;
import tn.esprit.pidev.repositories.NiveauRepository;
import tn.esprit.pidev.repositories.MatiereRepository;
import tn.esprit.pidev.services.Interfaces.IMatiereService;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MatiereImp implements IMatiereService {
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private NiveauRepository niveauRepository;

    @Override
    public ResponseEntity<?> addMatiere(Matiere matiere) {
        // Vérifier si le module associé existe
        Niveau niveau = matiere.getNiveau();
        if (niveau != null && niveau.getNom_niveau() != null) {
            Niveau existingNiveau = niveauRepository.findByNomNiveau(niveau.getNom_niveau()).stream().findFirst().orElse(null);
            if (existingNiveau != null) {
                matiere.setNiveau(existingNiveau); // Mettre à jour la matiere avec le niveau existant
                Matiere addedMatiere = matiereRepository.save(matiere);
                return ResponseEntity.ok().body(addedMatiere);
            } else {
                String errorMessage = String.format("Le module associé avec le nom '%s' n'existe pas.", niveau.getNom_niveau());
                log.error(errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        } else {
            String errorMessage = "Le niveau associé est manquant ou son nom est null.";
            log.error(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @Override
    public Matiere updateMatiere(String id_matiere, Matiere updatedMatiere) {
        // Assurez-vous que le matiere avec l'ID spécifié existe
        Matiere existingMatiere = matiereRepository.findById(id_matiere)
                .orElseThrow(() -> new RuntimeException("Matiere not found with ID: " + id_matiere));

        // Vérifier si le niveau associé existe
        Niveau niveau = updatedMatiere.getNiveau();
        if (niveau != null && niveau.getNom_niveau() != null) {
            Niveau existingNiveau = niveauRepository.findByNomNiveau(niveau.getNom_niveau()).stream().findFirst().orElse(null);
            if (existingNiveau != null) {
                existingMatiere.setNiveau(existingNiveau); // Mettre à jour le niveau associé du matiere
            } else {
                String errorMessage = String.format("Le niveau '%s' n'existe pas.", niveau.getNom_niveau());
                log.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
        } else {
            String errorMessage = "Le niveau associé est manquant ou son nom est null.";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        // Mettre à jour les autres champs du sous-module
        // Par exemple, si vous avez d'autres champs à mettre à jour, faites-le ici
        existingMatiere.setNom_matiere(updatedMatiere.getNom_matiere());
        // Mettez à jour d'autres champs au besoin

        // Enregistrer les modifications dans la base de données
        return matiereRepository.save(existingMatiere);
    }


    @Override
    public List<Matiere> getAllMatiere() {
        return matiereRepository.findAll();
    }

    @Override
    public Matiere getMatiereById(String id_matiere) {
        return matiereRepository.findById(id_matiere).orElse(null);
    }

    @Override
    public void deleteMatiere(String id_matiere) {
        matiereRepository.deleteById(id_matiere);
    }

    public Optional<Matiere> getMatiereByNom(String nom_matiere) {
        return matiereRepository.findByNom_matiere(nom_matiere);
    }
}
