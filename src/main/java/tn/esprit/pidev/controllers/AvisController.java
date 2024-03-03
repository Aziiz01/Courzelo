package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Attempt;
import tn.esprit.pidev.entities.Avis;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;

@RestController
@RequestMapping("/avis")
public class AvisController {
    @Autowired
    IService iService;

    @PostMapping(value = "/saveAvis")
    private Avis addAvis(@RequestBody Avis avis) {

        return iService.addAvis(avis);
    }

    @GetMapping("/{idEvaluation}")
    public Avis getAvis(@PathVariable String idEvaluation) {
        return iService.getAvisById(idEvaluation);
    }


    @GetMapping
    public List<Avis> getAllAVis() {
        return iService.getAllAvis();
    }

    @DeleteMapping("/delete/{idEvaluation}")
    public void deleteAvisById(@PathVariable String idEvaluation) {
        iService.deleteAvisById(idEvaluation);
    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Avis> updateAvis(@PathVariable String id, @RequestBody Avis updatedAvis) {
//        Avis updated = iService.updateAvis(id, updatedAvis);
//        if (updated != null) {
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
