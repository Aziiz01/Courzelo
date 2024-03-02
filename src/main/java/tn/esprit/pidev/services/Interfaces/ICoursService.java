package tn.esprit.pidev.services.Interfaces;



import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Cours;
import org.springframework.core.io.Resource;
import tn.esprit.pidev.entities.Ressource;

import java.util.List;

public interface ICoursService {
    public Cours addCours(Cours c);
    public Cours updateCours(String idCours,Cours cours);
    public List<Cours> getAllCourse();
    public Cours getCoursById(String id_cours);
    public void deleteCours(String id_cours);


    List<Cours> findAllByOrderByDateInscriptionDesc();
    List<Cours> findAllByNomCours(String  nomCours);
    public String storeFile(MultipartFile file, String blogCode);
    public Resource loadFileAsResource(String fileName);
    public Cours affecterRessourcesACour(Ressource r , String idc);
    public String storeFileRessource(MultipartFile file, String idRessource);




}
