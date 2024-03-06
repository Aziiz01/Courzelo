package tn.esprit.pidev.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;
import tn.esprit.pidev.entities.UserAttempt;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:4200")

public class QuizController {
    @Autowired
    IService iService;

    @PostMapping(value = "/saveQuiz")
    private Quiz addQuiz(@RequestBody Quiz quiz) {

        return iService.addQuiz(quiz);
    }

    @PutMapping("/update/{_id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable String _id, @RequestBody Quiz updatedQuiz) {
        Quiz updated = iService.updateQuiz(_id, updatedQuiz);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{_id}")
    public Quiz getQuiz(@PathVariable String _id) {
        return iService.getQuizById(_id);
    }

    @GetMapping("/getAll")
    public List<Quiz> getAllQuizzes() {
        return iService.getAllQuizzes();
    }

    @DeleteMapping("/delete/{_id}")
    public void deleteQuizById(@PathVariable String _id) {
        iService.deleteQuizById(_id);

    }

    @PutMapping("/{_id}/questions")
    public ResponseEntity<String> addQuestionsToQuiz(@PathVariable String _id, @RequestBody List<Question> questions) {
        try {
            iService.addQuestionsToQuiz(_id, questions);
            return ResponseEntity.ok("Questions added to the quiz successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }

    @GetMapping("/{_id}/questions")
    public ResponseEntity<List<Question>> getQuestionsForQuiz(@PathVariable String _id) {
        try {
            List<Question> questions = iService.getQuestionsForQuiz(_id);
            return ResponseEntity.ok(questions);
        } catch (RuntimeException e) {
            // Handle the case where the quiz is not found or any other error
            return ResponseEntity.notFound().build();
        }
    }
//    @PostMapping("/evaluate-quiz")
//    public ResponseEntity<Integer> evaluateQuiz(@RequestBody UserAttempt userAttempt) {
//        int score = iService.evaluateQuiz(userAttempt);
//        return ResponseEntity.ok(score);
//    }

}




