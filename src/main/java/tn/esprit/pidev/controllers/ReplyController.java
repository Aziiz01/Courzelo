package tn.esprit.pidev.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reply") // Set common parent path
public class ReplyController {
    @Autowired
    IService iService;
    @PostMapping(value = "/saveReply")
    private Reply addReply(@RequestBody Reply reply) throws MessagingException {

      return iService.addReply(reply);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Reply>> getAllReplies() {
        List<Reply> replies = iService.getAllReplies();
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reply> getReplyById(@PathVariable String id) {
        Optional<Reply> reply = iService.getReplyById(id);
        return reply.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reply> updateReply(@PathVariable String id, @RequestBody Reply updatedReply) {
        Reply updated = iService.updateReply(id, updatedReply);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable String id) {
        boolean deleted = iService.deleteReply(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
