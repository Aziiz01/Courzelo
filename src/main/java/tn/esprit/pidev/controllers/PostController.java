package tn.esprit.pidev.controllers;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    IService iService;
    @PostMapping(value = "/savePost")
    public Post addPost (@RequestBody Post post) throws MessagingException {
        return iService.addPost(post);
    }
    @PostMapping(value = "/voteUp/{id}/{userId}")
    public void voteUp(@PathVariable String id,@PathVariable String userId){
        iService.voteUpPost(id,userId);
    }
    @PostMapping(value = "/voteDown/{id}/{userId}")
    public void voteDown(@PathVariable String id,@PathVariable String userId){
        iService.voteDownPost(id,userId);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = iService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/by/{id}")
    public ResponseEntity<List<Post>> getPostsByArticle(@PathVariable String id) {
        List<Post> posts = iService.getPostsByArticle(id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Optional<Post> post = iService.getPostById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/followed/{userId}")
    public List<Post> getFollowedPostsByUserId(@PathVariable String userId) {
        return iService.getFollowedPostsByUserId(userId);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        Post updated = iService.updatePost(id, updatedPost);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        boolean deleted = iService.deletePost(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/followPost/{idPost}/{idUser}")
    public void followPost(@PathVariable String idPost, @PathVariable String idUser) throws MessagingException{
        iService.followPost(idPost, idUser);
    }

    @PostMapping("/followArticle/{idArticle}/{idUser}")
    public void followArticle(@PathVariable String idArticle, @PathVariable String idUser) {
        iService.followArticle(idArticle, idUser);
    }

    @PostMapping("/unfollowPost/{idPost}/{idUser}")
    public void unfollowPost(@PathVariable String idPost, @PathVariable String idUser) {
        iService.unfollowPost(idPost, idUser);
    }

    @PostMapping("/unfollowArticle/{idArticle}/{idUser}")
    public void unfollowArticle(@PathVariable String idArticle, @PathVariable String idUser) {
        iService.unfollowArticle(idArticle, idUser);
    }
}
