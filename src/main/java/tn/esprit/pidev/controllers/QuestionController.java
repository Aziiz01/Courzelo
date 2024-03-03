package tn.esprit.pidev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Avis;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:4200")

public class QuestionController {
    @Autowired
    IService iService;

    @PostMapping(value = "/saveQuestion")
    private Question addQuestion(@RequestBody Question question) {

        return iService.addQuestion(question);
    }

    @GetMapping("/{idQuestion}")
    public Question getQuestion(@PathVariable String idQuestion) {
        return iService.getQuestionById(idQuestion);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return iService.getAllQuestions();
    }

    @DeleteMapping("/delete/{_id}")
    public void deleteQuestionById(@PathVariable String _id) {
        iService.deleteQuestionById(_id);
    }

    @PutMapping("/update/{_id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable String _id, @RequestBody Question updatedQuestion) {
        Question updated = iService.updateQuestion(_id, updatedQuestion);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    @GetMapping("/errr/{_id}")
//    public ResponseEntity<?> getAllQuestion(@PathVariable("_id") String _id) throws Exception{
//        Quiz quiz=this.iService.getQuizById(_id);
//        Set<Question> question=quiz.getQuestion();
//        List<Question> listOfQuestions=new ArrayList<>(question);
//        if(listOfQuestions.size()>Integer.parseInt(quiz.getNumberOfQuestions())) {
//            listOfQuestions=listOfQuestions.subList(0,Integer.parseInt(quiz.getNumberOfQuestions())+1);
//        }
//        return ResponseEntity.ok(listOfQuestions);
//    }
}

