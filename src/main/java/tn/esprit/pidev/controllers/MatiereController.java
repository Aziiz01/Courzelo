package tn.esprit.pidev.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Matiere;
import tn.esprit.pidev.services.Interfaces.IMatiereService;

import java.util.List;

@RestController
@RequestMapping("/matiere")
public class MatiereController {
    @Autowired
    IMatiereService iMatiereService;
    @PostMapping(value = "/addMatiere")
    public ResponseEntity<?> addMatiere(@RequestBody Matiere matiere) {
        return iMatiereService.addMatiere(matiere);
    }
    @PutMapping("/updateMatiere/{id_matiere}")
    public ResponseEntity<?> updatedMatiere(@PathVariable String id_matiere, @RequestBody Matiere updatedMatiere) {
        try {
            Matiere updatedNiveau = iMatiereService.updateMatiere(id_matiere, updatedMatiere);
            return ResponseEntity.ok().body(updatedNiveau);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/getAllMatiere")
    public List<Matiere> getAllMatiere(){return iMatiereService.getAllMatiere();}
    @GetMapping("/getMatiere/{id_matiere}")
    public Matiere getMatiereById(@PathVariable String id_matiere){return  iMatiereService.getMatiereById(id_matiere);}
    @DeleteMapping("/deleteMatiere/{id_matiere}")
    public void deleteMatiereById(@PathVariable String id_matiere){iMatiereService.deleteMatiere(id_matiere);}

}
