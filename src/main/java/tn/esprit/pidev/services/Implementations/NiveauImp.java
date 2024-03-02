package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Niveau;
import tn.esprit.pidev.repositories.NiveauRepository;
import tn.esprit.pidev.services.Interfaces.INiveauService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class NiveauImp implements INiveauService {

    @Autowired
     private NiveauRepository niveauRepository;

    @Override
    public Niveau addNiveau(Niveau niveau) {
        // Vérifier si un niveau avec le même nom existe déjà
        List<Niveau> existingNiveaux = niveauRepository.findByNomNiveau(niveau.getNom_niveau());
        if (!existingNiveaux.isEmpty()) {
            throw new NiveauException("Niveau  " + niveau.getNom_niveau() + " already exists");
        }

        // Générer un ID unique
        String niveauId = generateUniqueNiveauId();

        // Attribuer l'ID au niveau
        niveau.setId_niveau(niveauId);

        // Enregistrer le niveau dans la base de données
        return niveauRepository.save(niveau);
    }


    private String generateUniqueNiveauId() {
        // Générer l'ID unique ici, vous pouvez utiliser UUID ou une autre méthode de génération d'ID
        // Par exemple :
        return UUID.randomUUID().toString().split("-")[0];
    }


    @Override
    public Niveau updateNiveau(String id_niveau, Niveau niveau) {
        // Assurez-vous que le module avec l'ID spécifié existe
        Niveau existingModule = niveauRepository.findById(id_niveau)
                .orElseThrow(() -> new NiveauException("Module not found with ID: " + id_niveau));

        // Vérifier si un module avec le même nom existe déjà
        List<Niveau> existingNiveaux = niveauRepository.findByNomNiveau(niveau.getNom_niveau());
        existingNiveaux.remove(existingNiveaux); // Exclure le module actuel de la recherche
        if (!existingNiveaux.isEmpty()) {
            throw new NiveauException("Niveau with name " + niveau.getNom_niveau() + " already exists");
        }

        // Mettre à jour les champs du module avec les nouvelles valeurs
        existingModule.setNom_niveau(niveau.getNom_niveau());

        // Enregistrer les modifications dans la base de données
        return niveauRepository.save(existingModule);
    }

    @Override
    public List<Niveau> getAllNiveau() {return niveauRepository.findAll();}

    @Override
    public Niveau getNiveauById(String id_niveau) {
        return niveauRepository.findById(id_niveau).orElse(null);
    }

    @Override
    public void deleteNiveau(String id_niveau) {
        niveauRepository.deleteById(id_niveau);
    }
}
