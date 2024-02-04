package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.services.Interfaces.IService;

@RestController
@RequestMapping("/reply") // Set common parent path
public class ReplyController {
    @Autowired
    IService iService;
    @PostMapping(value = "/saveReply")
    private Reply addReply(@RequestBody Reply reply) {

      return iService.addReply(reply);
    }

}
