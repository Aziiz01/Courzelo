package tn.esprit.pidev.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Cours;
import tn.esprit.pidev.entities.Matiere;
import tn.esprit.pidev.entities.Ressource;
import tn.esprit.pidev.repositories.CoursRepository;
import tn.esprit.pidev.repositories.RessourceRepository;
import tn.esprit.pidev.services.Implementations.MatiereException;
import tn.esprit.pidev.services.Interfaces.ICoursService;
import tn.esprit.pidev.services.Interfaces.IMatiereService;


import java.io.File;

import tn.esprit.pidev.repositories.MatiereRepository;
import tn.esprit.pidev.services.Interfaces.IRessourceService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/cours")
@CrossOrigin("http://localhost:8089")
public class CoursController {
        @Value("${file.upload-dir}")
        private String uploadDir;

        @Autowired
        ICoursService iCoursService;
        @Autowired
        IMatiereService iMatiereService;

        @Autowired
        IRessourceService iRessourceService;

        @Autowired
        RessourceRepository ressourceRepository;
        @Autowired
        CoursRepository coursRepository;

        @PostMapping("/addCours")
        private Cours addCours(@RequestBody Cours cours) {
                return iCoursService.addCours(cours);
        }

        @PutMapping("/updateCours/{id_cours}")
        public Cours updateCours(@PathVariable String id_cours,@RequestBody Cours cours){
                return iCoursService.updateCours(id_cours,cours);
        }
        @GetMapping("/getCours")
        public List<Cours> getAllCours(){return iCoursService.getAllCourse();}
        @GetMapping("/get/{id_cours}")
        public Cours getCoursById(@PathVariable String id_cours){return  iCoursService.getCoursById(id_cours);}
        @DeleteMapping("/delete/{id_cours}")
        public void deleteCoursById(@PathVariable String id_cours){
                iCoursService.deleteCours(id_cours);
        }

        @GetMapping("/findAllByNomCour")
        public List<Cours> findAllByNomCours(String nomCours) {
                return iCoursService.findAllByNomCours(nomCours);
        }
        @GetMapping("/findAllByOrderByDateDesc")
        public List<Cours> findAllByOrderByDateInscriptionDesc() {
                return iCoursService.findAllByOrderByDateInscriptionDesc();
        }

       @PostMapping("/upload/{id}")
       public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("id") String courId) {
           String fileName = iCoursService.storeFile(file, courId); // Utilisez correctement la méthode storeFile
           Cours c = coursRepository.findById(courId).orElseThrow(() -> new RuntimeException("Cours not found with ID: " + courId));
           c.setPhoto(fileName);
           log.info("File uploaded successfully");
           return ResponseEntity.ok().body("File uploaded successfully: " + fileName); // Renvoyer le nom du fichier stocké
       }
       
    @PostMapping("/uploadRessource/{id}")
    public ResponseEntity<String> storeFileRessource(@RequestParam("file") MultipartFile file, @PathVariable("id") String idRessource) {
        String fileName = iCoursService.storeFileRessource(file, idRessource); // Utilisez correctement la méthode storeFile
        Ressource r = ressourceRepository.findById(idRessource).orElseThrow(() -> new RuntimeException("Ressource not found with ID: " + idRessource));
        r.setPhoto(fileName);
        log.info("File uploaded successfully");
        return ResponseEntity.ok().body("File uploaded successfully: " + fileName); // Renvoyer le nom du fichier stocké
    }

        @GetMapping("/download/{fileName:.+}")
        public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
                Resource resource = iCoursService.loadFileAsResource(fileName);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
        }


        @PostMapping("/affecterRessourcesACour/{idc}")
        public Cours affecterRessourcesACour( @RequestBody  Ressource res ,@PathVariable("idc") String idc) {
                return iCoursService.affecterRessourcesACour(res,idc);
        }
        @PostMapping("/ajouterRessource")
        public Ressource ajouterRessource( @RequestBody Ressource ressource)
        {
                return iRessourceService.ajouterRessource(ressource);
        }


        @DeleteMapping("/supprimerRessource/{id}")
        public void supprimerRessource(@PathVariable("id") String id) {
                iRessourceService.supprimerRessource(id);
        }


}
