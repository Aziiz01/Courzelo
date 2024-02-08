package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {

    @Autowired
    private IService iservice;

    @PostMapping("/add")
    public ResponseEntity<Discussion> addDiscussion(@RequestBody Discussion discussion) {
        Discussion addedDiscussion = iservice.addDiscussion(discussion);
        return new ResponseEntity<>(addedDiscussion, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Discussion>> getAllDiscussions() {
        List<Discussion> discussions = iservice.getAllDiscussions();
        return new ResponseEntity<>(discussions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discussion> getDiscussionById(@PathVariable String id) {
        Optional<Discussion> discussion = iservice.getDiscussionById(id);
        return discussion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Discussion> updateDiscussion(@PathVariable String id, @RequestBody Discussion updatedDiscussion) {
        Discussion updated = iservice.updateDiscussion(id, updatedDiscussion);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable String id) {
        boolean deleted = iservice.deleteDiscussion(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
