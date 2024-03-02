package tn.esprit.pidev.services.Implementations;

import tn.esprit.pidev.entities.Cours;
import tn.esprit.pidev.entities.Ressource;
import tn.esprit.pidev.repositories.CoursRepository;
import tn.esprit.pidev.repositories.RessourceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.services.Interfaces.IRessourceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class RessourceImp implements IRessourceService {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


    @Autowired
    RessourceRepository ressourceRepository;
    @Autowired
    CoursRepository coursRepository;
    @Override
    public Ressource ajouterRessource(Ressource ressource) {

        return ressourceRepository.save(ressource);

    }

    @Override
    public List<Ressource> getRessource() {
        return ressourceRepository.findAll();
    }

    @Override
    public void supprimerRessource(String idr) {
        Ressource r= ressourceRepository.findById(idr).get();
        ressourceRepository.delete(r);
    }

    @Override
    public Ressource modifierRessource(Ressource r, String idr) {

        Ressource res = ressourceRepository.findById(idr).get();
        res.setNomRessource(r.getNomRessource());
        return ressourceRepository.save(res);
    }

    @Override
    public List<Ressource> getRessourcesByCourId(String id) {
        Cours cours = coursRepository.findById(id).get();
        return cours.getRessourceList(); // Suppose que getRessources() est une méthode qui retourne le tableau de ressources
    }



    @Override
    public String uploadImage(Model model, MultipartFile file) {

        StringBuilder fileNames = new StringBuilder();
        try {
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            fileNames.append(file.getOriginalFilename());
            model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        } catch (IOException e) {
            // Gérer les erreurs liées à l'écriture du fichier, par exemple, en ajoutant un message d'erreur au modèle.
            model.addAttribute("error", "Error uploading the image.");
            e.printStackTrace(); // Vous pouvez également logger l'exception.
        }
        return "imageupload/index";
    }

}
