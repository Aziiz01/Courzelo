package tn.esprit.pidev.services.Interfaces;

import tn.esprit.pidev.entities.UserAttempt;
import tn.esprit.pidev.entities.Avis;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;


import java.util.List;

public interface IService {
    public Quiz addQuiz(Quiz quiz);

    public Question addQuestion(Question question);

    public Avis addAvis(Avis avis);

    public UserAttempt addAttempt(UserAttempt userAttempt);

    public Question updateQuestion(String idQuestion, Question updateQuestion);

//    public Avis updateAvis(String id, Avis avis);

    public UserAttempt updateAttempt(String id, UserAttempt userAttempt);

    public Quiz updateQuiz(String _id, Quiz quiz);

    public Quiz getQuizById(String _id);

    List<Quiz> getAllQuizzes();

    public Avis getAvisById(String idEvaluation);

    List<Avis> getAllAvis();

    List<Question> getAllQuestions();

    Question getQuestionById(String _id);

    public void deleteQuizById(String _id);

    public void deleteQuestionById(String _id);

    public void deleteAvisById(String idEvaluation);

    public Quiz addQuestionsToQuiz(String quizId, List<Question> questions);
    public List<Question> getQuestionsForQuiz(String _id);
//  public int evaluateQuiz(UserAttempt userAttempt);
}