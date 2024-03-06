package tn.esprit.pidev.services.Implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.UserAttempt;
import tn.esprit.pidev.entities.Avis;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;
import tn.esprit.pidev.repositories.AttemptRepository;
import tn.esprit.pidev.repositories.AvisRepository;
import tn.esprit.pidev.repositories.QuestionRepository;
import tn.esprit.pidev.repositories.QuizRepository;
import tn.esprit.pidev.services.Interfaces.EmailSenderService;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ServicesImp implements IService {

    private QuizRepository quizRepository;

    private QuestionRepository questionRepository;
    private AvisRepository avisRepository;
    private AttemptRepository attemptRepository;
    private JavaMailSender mailSender;
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        Quiz addedQuiz = quizRepository.save(quiz);
        sendEmailConfirmation(addedQuiz); // Send email confirmation
        return addedQuiz;
    }

    private void sendEmailConfirmation(Quiz quiz) {
        String toEmail = "arbi.ferchichi@esprit.tn"; // Set the recipient email
        String subject = "Quiz Added Confirmation"; // Set email subject
        String body = "Dear user,\n\nYour quiz titled '" + quiz.getTitle() + "' has been successfully added."; // Set email body
        emailSenderService.sendSimpleEmail(toEmail, subject, body); // Sending email using EmailSenderService
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Avis addAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    @Override
    public UserAttempt addAttempt(UserAttempt userAttempt) {
        return attemptRepository.save(userAttempt);
    }


    private MongoTemplate mongoTemplate;

    @Override
    public Quiz updateQuiz(String _id, Quiz updatedQuiz) {
        Query query = new Query(Criteria.where("_id").is(_id));
        Update update = new Update()
                .set("title", updatedQuiz.getTitle()) // Add this line for the title field
                .set("score", updatedQuiz.getScore())
                .set("numberOfQuestions", updatedQuiz.getNumberOfQuestions())
                .set("attemptsAllowed", updatedQuiz.getAttemptsAllowed())
                .set("randomizeQuestions", updatedQuiz.isRandomizeQuestions())
                .set("visibilite", updatedQuiz.isVisibilite())
                .set("dureequiz", updatedQuiz.getDureequiz());

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, Quiz.class);
    }


    @Override
    public Question updateQuestion(String _id, Question updatedQuestion) {
        Query query = new Query(Criteria.where("_id").is(_id));
        Update update = new Update()
                .set("questionText", updatedQuestion.getQuestionText())
                .set("options", updatedQuestion.getOptions())
                .set("correctOption", updatedQuestion.getCorrectOption())
                .set("explication", updatedQuestion.getExplication())
                .set("notequstion", updatedQuestion.getNotequstion())
                .set("dureequestion", updatedQuestion.getDureequestion());

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, Question.class);
    }

    @Override
    public UserAttempt updateAttempt(String id, UserAttempt userAttempt) {
        return null;
    }

//    @Override
//    public Avis updateAvis(String avisId, Avis updatedAvis) {
//        Query query = new Query(Criteria.where("_id").is(avisId));
//        Update update = new Update()
//                .set("titre", updatedAvis.getTitre())
//                .set("visibilite", updatedAvis.isVisibilite())
//                .set("dateEvaluation", updatedAvis.getDateEvaluation())
//                .set("starRating", updatedAvis.getStarRating());
//
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
//        return mongoTemplate.findAndModify(query, update, options, Avis.class);
//    }


//    @Override
//    public Attempt updateAttempt(String attemptId, Attempt updatedAttempt) {
//        Query query = new Query(Criteria.where("_id").is(attemptId));
//        Update update = new Update()
//                .set("score", updatedAttempt.getScore())
//                .set("startTime", updatedAttempt.getStartTime())
//                .set("endTime", updatedAttempt.getEndTime())
//                .set("completed", updatedAttempt.isCompleted())
//                .set("dureeetu", updatedAttempt.getDureeetu())
//                .set("currentQuestionIndex", updatedAttempt.getCurrentQuestionIndex());
//
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
//        return mongoTemplate.findAndModify(query, update, options, Attempt.class);
//    }


    @Override
    public Quiz getQuizById(String _id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(_id);
        return optionalQuiz.orElse(null);

    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Avis getAvisById(String idEvaluation) {
        Optional<Avis> optionalAvis = avisRepository.findById(idEvaluation);
        return optionalAvis.orElse(null);
    }

    @Override
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(String _id) {
        Optional<Question> optionalQuestion = questionRepository.findById((_id));
        return optionalQuestion.orElse(null);
    }

    @Override
    public void deleteQuizById(String _id) {
        Query query = new Query(Criteria.where("_id").is(_id));
        mongoTemplate.remove(query, Quiz.class);
    }

    @Override
    public void deleteQuestionById(String idQuestion) {
        Query query = new Query(Criteria.where("_id").is(idQuestion));
        mongoTemplate.remove(query, Question.class);
    }

    @Override
    public void deleteAvisById(String idEvaluation) {
        avisRepository.deleteById(idEvaluation);
    }

    @Override
    public Quiz addQuestionsToQuiz(String _id, List<Question> questions) {
        // Fetch the quiz by its ID
        Quiz quiz = quizRepository.findById(_id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + _id));

        // Initialize the questions list if it's null
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new ArrayList<>());
        }

        // Add the questions to the quiz's questions list
        quiz.getQuestions().addAll(questions);

        // Save the updated quiz (which will cascade save the associated questions)
        return quizRepository.save(quiz);
    }

    public List<Question> getQuestionsForQuiz(String _id) {
        Optional<Quiz> quizOptional = quizRepository.findById(_id);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            return quiz.getQuestions();
        } else {
            // Handle the case where the quiz is not found
            throw new RuntimeException("Quiz not found with id: " + _id);
        }
    }

//    public int evaluateQuiz(String quizId, UserAttempt userAttempt) {
//        int score = 0;
//
//        // Retrieve the list of questions attempted by the user
//        List<Question> attemptedQuestions = (List<Question>) userAttempt.getQuestion();
//
//        if (attemptedQuestions.size() != userAttempt.getSelectedOptions().size()) {
//            throw new IllegalArgumentException("Number of selected options doesn't match the number of attempted questions.");
//        }
//
//        // Iterate over the attempted questions
//        for (int i = 0; i < attemptedQuestions.size(); i++) {
//            Question question = attemptedQuestions.get(i);
//            int correctOption = question.getCorrectOption();
//            int selectedOption = userAttempt.getSelectedOptions().get(i);
//
//            if (correctOption == selectedOption) {
//                score += question.getNotequstion(); // Increment score if the selected option is correct
//            }
//        }
//
//        // Set the final score in the user attempt object
//        userAttempt.setScore(score);
//
//        return score;
//    }

}






