package tn.esprit.pidev.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/**")
public class ExempleController {
   // @Autowired
    //ITestService testService;

    @GetMapping("/test")
    public String apiRoot() {
        return "Hello, World!";
    }

    //    @PostMapping("/add-evenement")
//    public Evenement addChaeazembre(@RequestBody Evenement c) {
//        Evenement chambre = evenementService.ajoutAffectEvenParticip(c);
//        return chambre;


//    @GetMapping("/retrieve-logistique/{dd}/{df}")
//    public List<Logistique> getChambres(@PathVariable("dd")@DateTimeFormat(pattern = "yyyy-MM-dd") Date dd, @PathVariable("df")@DateTimeFormat(pattern = "yyyy-MM-dd") Date df) {
//        return logistiqueService.getLogistiquesDates(dd,df);
//

//    @GetMapping("/retrieve-par")
//    public List<Participant> getChambres() {
//        return participantService.getParReservLogis();
//    }
}
