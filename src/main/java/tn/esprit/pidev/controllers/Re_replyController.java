package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Re_reply;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/re_reply") // Set common parent path
public class Re_replyController {
    @Autowired
    IService iService;
    @PostMapping(value = "/saveRe_reply")
    private Re_reply addRe_reply(@RequestBody Re_reply re_reply) {

        return iService.addRe_reply(re_reply);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Re_reply>> getAllRe_replies() {
        List<Re_reply> re_replies = iService.getAllRe_replies();
        return new ResponseEntity<>(re_replies, HttpStatus.OK);
    }
/////////finish here
    @GetMapping("/{id}")
    public ResponseEntity<Re_reply> getRe_replyById(@PathVariable String id) {
        Optional<Re_reply> re_reply = iService.getRe_replyById(id);
        return re_reply.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Re_reply> updateRe_reply(@PathVariable String id, @RequestBody Re_reply updatedRe_reply) {
        Re_reply updated = iService.updateRe_reply(id, updatedRe_reply);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRe_reply(@PathVariable String id) {
        boolean deleted = iService.deleteRe_reply(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
