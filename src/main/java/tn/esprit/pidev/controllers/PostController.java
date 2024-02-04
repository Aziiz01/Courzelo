package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.Interfaces.IService;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    IService iService;
    @PostMapping(value = "/savePost")
    public Post addPost (@RequestBody Post post) {
        return iService.addPost(post);
    }
}
