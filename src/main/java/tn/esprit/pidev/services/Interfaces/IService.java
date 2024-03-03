package tn.esprit.pidev.services.Interfaces;

import tn.esprit.pidev.entities.Attempt;
import tn.esprit.pidev.entities.Avis;
import tn.esprit.pidev.entities.Question;
import tn.esprit.pidev.entities.Quiz;


import java.util.List;
import java.util.Set;

public interface IService {
    public Quiz addQuiz(Quiz quiz);

    public Question addQuestion(Question question);

    public Avis addAvis(Avis avis);

    public Attempt addAttempt(Attempt attempt);

    public Question updateQuestion(String idQuestion, Question updateQuestion);

//    public Avis updateAvis(String id, Avis avis);

    public Attempt updateAttempt(String id, Attempt attempt);

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

}