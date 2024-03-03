package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Article;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IService iService;

    @PostMapping(value = "/voteUp/{id}/{userId}")
    public void voteUp(@PathVariable String id, @PathVariable String userId){
        iService.voteUpArticle(id, userId);
    }
    @PostMapping(value = "/voteDown/{id}/{userId}")
    public void voteDown(@PathVariable String id, @PathVariable String userId){
        iService.voteDownArticle(id,userId);
    }

    @GetMapping("/followed/{userId}")
    public List<Post> getFollowedArticlesByUserId(@PathVariable String userId) {
        return iService.getFollowedArticlesByUserId(userId);
    }
    @PostMapping("/add")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article addedArticle = iService.addArticle(article);
        return new ResponseEntity<>(addedArticle, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = iService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable String id) {
        Optional<Article> article = iService.getArticleById(id);
        return article.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article updatedArticle) {
        Article updated = iService.updateArticle(id, updatedArticle);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String id) {
        boolean deleted = iService.deleteArticle(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
