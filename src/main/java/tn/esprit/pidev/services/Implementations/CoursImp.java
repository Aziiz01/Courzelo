package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Cours;
import tn.esprit.pidev.entities.Ressource;
import tn.esprit.pidev.repositories.CoursRepository;
import tn.esprit.pidev.repositories.RessourceRepository;
import tn.esprit.pidev.services.Interfaces.ICoursService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CoursImp implements ICoursService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private CoursRepository coursRepository;
    /*@Autowired
    private MatiereRepository matiereRepository;*/
    @Autowired
    private RessourceRepository ressourceRepository;


  @Override
  public Cours addCours(Cours c) {
      String idc = RandomStringUtils.randomAlphabetic(10);
      c.setId_cours(idc);
      Date date = new Date();
      c.setDateInscription(date);
      for (Ressource ressource : c.getRessourceList()) {
          String idRessource = RandomStringUtils.randomAlphabetic(10);
          ressource.setIdRessource(idRessource);
      }

      List<Ressource> ressourceList = c.getRessourceList();
      ressourceRepository.saveAll(ressourceList);
      return coursRepository.save(c);
  }

    @Override
    public Cours updateCours(String id_cours, Cours cours) {
        // Recherche du cours existant par son ID
        Optional<Cours> existingCoursOptional = coursRepository.findById(id_cours);
        if (existingCoursOptional.isPresent()) {
            // Obtenir le cours existant
            Cours existingCours = existingCoursOptional.get();

            // Mettre à jour les champs du cours existant avec les nouvelles valeurs
            existingCours.setNomCours(cours.getNomCours());
            existingCours.setNomProfesseur(cours.getNomProfesseur());
            existingCours.setTypeCours(cours.getTypeCours());
            existingCours.setDateInscription(cours.getDateInscription());
            existingCours.setMatiere(cours.getMatiere());
            existingCours.setDescriptionCours(cours.getDescriptionCours());
            existingCours.setPrix(cours.getPrix());

            // Sauvegarder les modifications dans la base de données
            return coursRepository.save(existingCours);
        } else {
            // Gérer le cas où le cours n'est pas trouvé
            throw new RuntimeException("Cours not found with ID: " + id_cours);
        }
    }

    @Override
    public List<Cours> getAllCourse() {
        return coursRepository.findAll();
    }

    @Override
    public Cours getCoursById(String id_cours) {
        return coursRepository.findById(id_cours).orElse(null);
    }

    @Override
    public void deleteCours(String id_cours) {
        coursRepository.deleteById(id_cours);
    }

    @Override
    public List<Cours> findAllByOrderByDateInscriptionDesc() {
        for (Cours c : coursRepository.findAllByOrderByDateInscriptionDesc()) {
            log.info("le nom est \n" + c.getNomCours());
        }
        return coursRepository.findAllByOrderByDateInscriptionDesc();
    }


    @Override
    public List<Cours> findAllByNomCours(String nomCours) {
        return coursRepository.findAllByNomCours(nomCours);
    }


    private String generateNewFileName(String originalFileName) {
        // You can customize this method to generate a unique file name.
        // For example, appending a timestamp or using a UUID.
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + "_" + originalFileName;
    }

    @Override
    public String storeFile(MultipartFile file, String id_cours) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = generateNewFileName(originalFileName);

        Path uploadPath = Paths.get(uploadDir);

        try {
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            Cours cours = coursRepository.findById(id_cours).orElseThrow(() -> new RuntimeException("Cour not found with id: " + id_cours));
            cours.setPhoto(newFileName);
            coursRepository.save(cours);

            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + newFileName, e);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + fileName, e);
        }
    }

    @Override
    public Cours affecterRessourcesACour(Ressource res, String idc) {
        Cours cours = coursRepository.findById(idc)
                .orElseThrow(() -> new RuntimeException("Le cours avec l'ID spécifié n'existe pas"));

        // Vérifier si la ressource existe déjà dans la base de données
        Ressource existingRessource = ressourceRepository.findById(res.getIdRessource()).orElse(null);

        if (existingRessource != null) {
            // La ressource existe déjà dans la base de données, l'ajouter directement au cours
            cours.getRessourceList().add(existingRessource);
            log.info("La ressource existante a été ajoutée au cours avec succès.");
        } else {
            // La ressource n'existe pas dans la base de données, donc la sauvegarder d'abord
            Ressource savedRessource = ressourceRepository.save(res);
            cours.getRessourceList().add(savedRessource);
            log.info("La nouvelle ressource a été sauvegardée et ajoutée au cours avec succès.");
        }

        return coursRepository.save(cours);
    }


    @Override
    public String storeFileRessource(MultipartFile file, String idRessource) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = generateNewFileName(originalFileName);

        Path uploadPath = Paths.get(uploadDir);

        try {
            if (Files.notExists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            Ressource ressource = ressourceRepository.findById(idRessource).get();
            ressource.setPhoto(newFileName);
            ressourceRepository.save(ressource);

            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + newFileName, e);
        }
    }


}