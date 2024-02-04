package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.services.Interfaces.IReplyService;

@RestController
//@RequestMapping("/**")
public class ReplyController {
    @Autowired
    IReplyService iReplyService;
    @PostMapping(value = "/save")
    private Reply saveStudent(@RequestBody Reply reply) {

      return iReplyService.addReply(reply);
    }

}
