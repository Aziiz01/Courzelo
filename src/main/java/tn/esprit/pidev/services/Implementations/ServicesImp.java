package tn.esprit.pidev.services.Implementations;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.pidev.controllers.CustomBotController;
import tn.esprit.pidev.dto.ChatGPTRequest;
import tn.esprit.pidev.dto.ChatGptResponse;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Re_reply;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.entities.Article;
import tn.esprit.pidev.repositories.ArticleRepository;
import tn.esprit.pidev.repositories.PostRepository;
import tn.esprit.pidev.repositories.Re_replyRepository;
import tn.esprit.pidev.repositories.ReplyRepository;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final RestTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(CustomBotController.class); // Declare logger
    @Autowired
    private EmailService emailService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private Re_replyRepository re_replyRepository;
    private QuizRepository quizRepository;

    private QuestionRepository questionRepository;
    private AvisRepository avisRepository;
    private AttemptRepository attemptRepository;
    private JavaMailSender mailSender;
    @Autowired
    private EmailSenderService emailSenderService;


    public void triggerMail() throws MessagingException {
        emailService.sendSimpleEmail("mohamedaziz.nacib@esprit.tn",
                "Courzelo Platform",
                "Post successfully added!");

    }
    public void followMail() throws MessagingException {
        emailService.sendSimpleEmail("mohamedaziz.nacib@esprit.tn",
                "Courzelo Platform",
                "User x followed your post!");

    }
    public void followedArticle(String article) throws MessagingException {
        emailService.sendSimpleEmail("mohamedaziz.nacib@esprit.tn",
                "Courzelo Platform",
                "A new post was added to your followed article "+article+"!");

    }
    @Override
    public Reply addReply(Reply reply) {
        String response = chat(reply.getContext());
        if (!response.equals("valid")) {
            return null;
        } else {
            // Get the post ID from the reply
            String postId = reply.getPostId();

            // Fetch the associated post
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

            // Initialize the replies list if it's null
            if (post.getReplies() == null) {
                post.setReplies(new ArrayList<>());
            }

            // Add the reply to the post's replies list
            post.getReplies().add(reply);


            // Save the post (which will cascade save the associated reply)
            postRepository.save(post);


            return replyRepository.save(reply);
        }
    }
    @Override
    public Re_reply addRe_reply(Re_reply re_reply) {
        String response = chat(re_reply.getContext());
        if (!response.equals("valid")) {
            return null;
        } else {
            // Get the post ID from the reply
            String replyId = re_reply.getReplyId();

            // Fetch the associated post
            Reply reply = replyRepository.findById(replyId)
                    .orElseThrow(() -> new EntityNotFoundException("Reply not found with id: " + replyId));

            // Initialize the replies list if it's null
            if (reply.getRe_replies() == null) {
                reply.setRe_replies(new ArrayList<>());
            }

            reply.getRe_replies().add(re_reply);


            replyRepository.save(reply);

            return re_replyRepository.save(re_reply);
        }
    }

    @Override
    public Post addPost(Post post) throws MessagingException {
        String response = chat(post.getContext());
        if (!response.equals("valid")) {
            return null;
        } else {
            if (!post.getArticleId().isEmpty()) {

                Optional<Article> articleOptional = articleRepository.findById(post.getArticleId());

                if (articleOptional.isPresent()) {
                    Article article = articleOptional.get();
                    post.setArticle(article);
                    if (checkArticleIsFollowed(post.getArticleId(), "static")) {
                        followedArticle(article.getTitre());
                    }

                } else {
                    return null;
                }
            }

            triggerMail();

            return postRepository.save(post);
        }
    }

public boolean checkArticleIsFollowed(String articleId, String userId){
        Article article = articleRepository.findById(articleId) .orElseThrow(() -> new EntityNotFoundException("article not found with id: " + articleId));
    List<String> followedBy = article.getFollowedBy();
    if (followedBy.contains(userId)) {
        return true;
    }
return false;
}

    @Override
    public Article addArticle(Article article) {
        String response = chat(article.getTitre() +"/n"+article.getCategory());
        if (!response.equals("valid")) {
            return null;
        } else {
        return articleRepository.save(article);
    }}

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article updateArticle(String id, Article updatedArticle) {
        String response = chat(updatedArticle.getTitre() +"/n"+updatedArticle.getCategory());
        if (!response.equals("valid")) {
            return null;
        } else {
        Optional<Article> existingArticle = articleRepository.findById(id);

        if (existingArticle.isPresent()) {
            Article originalArticle = existingArticle.get();

            if (updatedArticle.getTitre() != null) {
                originalArticle.setTitre(updatedArticle.getTitre());
            }            if (updatedArticle.getScore() != null) {
                originalArticle.setScore(updatedArticle.getScore());
            }
            if (updatedArticle.getCategory() != null) {
                originalArticle.setCategory(updatedArticle.getCategory());
            }
            if (updatedArticle.getPosts() != null) {
                originalArticle.setPosts(updatedArticle.getPosts());
            }

            // Save the updated article
            return articleRepository.save(originalArticle);
        }

        return null; // Handle the case where the article with the given id doesn't exist
    }}

    @Override
    public boolean deleteArticle(String id) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isPresent()) {
           List<Post> posts = postRepository.findAll();

                posts.forEach(post -> {
                    if (id.equals(post.getArticleId())) {
                        postRepository.deleteById(post.getIdPost());
                    }
                });


            // Delete the article
            articleRepository.deleteById(id);

            return true;
        }

        return false; // Handle the case where the article with the given id doesn't exist
    }





    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }
    @Override
    public Post updatePost(String id, Post updatedPost) {
        String response = chat(updatedPost.getTitre()+"/n"+updatedPost.getContext());
        if (!response.equals("valid")) {
            return null;
        } else {
        Optional<Post> existingPost = postRepository.findById(id);
        if (!updatedPost.getArticleId().isEmpty()) {

            Optional<Article> articleOptional = articleRepository.findById(updatedPost.getArticleId());

            if (articleOptional.isPresent()) {
                Article article = articleOptional.get();
                existingPost.get().setArticle(article);

            } else {
                return null;
            }
        }

        if (existingPost.isPresent()) {
          //  String reponse = chat(updatedPost.getContext());
          //  if ("valid".equals(reponse)) {
                Post originalPost = existingPost.get();

                // Update only the fields that are not null in the updatedPost
                if (updatedPost.getTitre() != null) {
                    originalPost.setTitre(updatedPost.getTitre());
                }
            if (updatedPost.getContext() != null) {
                originalPost.setContext(updatedPost.getContext());
            }
                if (updatedPost.getImageUrl() != null) {
                    originalPost.setImageUrl(updatedPost.getImageUrl());
                }
                if (updatedPost.getIdUser() != null) {
                    originalPost.setIdUser(updatedPost.getIdUser());
                }
                if (updatedPost.getArticleId() != null) {
                    originalPost.setArticleId(updatedPost.getArticleId());
                }

                if (updatedPost.getReplies() != null) {
                    originalPost.setReplies(updatedPost.getReplies());
                }

                // Save the updated post
                return postRepository.save(originalPost);
           // } else {
           //     return null;
          //  }

        }

        return null; // Handle the case where the post with the given id doesn't exist
    }}

    @Override
    public boolean deletePost(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the post with the given id doesn't exist
    }
    @Override
    public List<Reply> getAllReplies() {
        return replyRepository.findAll();
    }

    @Override
    public List<Re_reply> getAllRe_replies() {
        return re_replyRepository.findAll();
    }

    @Override
    public Optional<Reply> getReplyById(String id) {
        return replyRepository.findById(id);
    }

    @Override
    public Optional<Re_reply> getRe_replyById(String id) {
        return re_replyRepository.findById(id);
    }

    @Override
    public List<Post> getPostsByArticleId(String articleId) {
        return postRepository.getPostsByArticleId(articleId);
    }

    @Override
    public Reply updateReply(String id, Reply updatedReply) {

            Optional<Reply> existingReply = replyRepository.findById(id);

            if (existingReply.isPresent()) {
                String response = chat(updatedReply.getContext());
                if (!response.equals("valid")) {
                    return null;
                } else {
                Reply originalReply = existingReply.get();

                // Update only the fields that are not null in the updatedReply
                if (updatedReply.getIdUser() != 0) {
                    originalReply.setIdUser(updatedReply.getIdUser());
                }
                if (updatedReply.getContext() != null) {
                    originalReply.setContext(updatedReply.getContext());
                }
                if (updatedReply.getRecommondations() != 0) {
                    originalReply.setRecommondations(updatedReply.getRecommondations());
                }
                if (updatedReply.isVisibility() != originalReply.isVisibility()) {
                    originalReply.setVisibility(updatedReply.isVisibility());
                }

                // Save the updated reply
                return replyRepository.save(originalReply);
                // } else {
                //          return null;
                //      }
            }

        }
            return null; // Handle the case where the reply with the given id doesn't exist

    }
    @Override
    public Re_reply updateRe_reply(String id, Re_reply updatedRe_reply) {
        Optional<Re_reply> existingRe_reply = re_replyRepository.findById(id);

        if (existingRe_reply.isPresent()) {
            String response = chat(updatedRe_reply.getContext());
            if (!response.equals("valid")) {
                return null;
            } else {
                Re_reply originalRe_reply = existingRe_reply.get();

                // Update only the fields that are not null in the updatedReply
                if (updatedRe_reply.getIdUser() != 0) {
                    originalRe_reply.setIdUser(updatedRe_reply.getIdUser());
                }
                if (updatedRe_reply.getContext() != null) {
                    originalRe_reply.setContext(updatedRe_reply.getContext());
                }
                if (updatedRe_reply.getRecommondations() != 0) {
                    originalRe_reply.setRecommondations(updatedRe_reply.getRecommondations());
                }
                if (updatedRe_reply.isVisibility() != originalRe_reply.isVisibility()) {
                    originalRe_reply.setVisibility(updatedRe_reply.isVisibility());
                }

                // Save the updated reply
                return re_replyRepository.save(originalRe_reply);
                // } else {
                //          return null;
                //      }
            }

        }
        return null; // Handle the case where the reply with the given id doesn't exist    }
    }

    @Override
    public void followPost(String idPost, String idUser)throws MessagingException {
        Optional<Post> optionalPost = postRepository.findById(idPost);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getFollowedBy() == null) {
                post.setFollowedBy(new ArrayList<>());
            }
            // Check if the userId is not already in the followedBy list to avoid duplicates
            if (!post.getFollowedBy().contains(idUser)) {
                post.getFollowedBy().add(idUser);
                followMail();
                postRepository.save(post);
            }
        } else {
            // Handle the case where the Post with the given idPost doesn't exist
            throw new EntityNotFoundException("Post not found with id: " + idPost);
        }
    }
    @Override
    public List<Post> getFollowedPostsByUserId(String userId) {
        return postRepository.getFollowedPostsByUserId(userId);
    }
    @Override
    public void followArticle(String idArticle,String idUser) {
        Optional<Article> optionalPost = articleRepository.findById(idArticle);

        if (optionalPost.isPresent()) {
            Article a = optionalPost.get();
            if (a.getFollowedBy() == null) {
                a.setFollowedBy(new ArrayList<>());
            }
            // Check if the userId is not already in the followedBy list to avoid duplicates
            if (!a.getFollowedBy().contains(idUser)) {
                a.getFollowedBy().add(idUser);
                articleRepository.save(a);
            }
        } else {
            // Handle the case where the Post with the given idPost doesn't exist
            throw new EntityNotFoundException("Article not found with id: " + idArticle);
        }
    }
    @Override
    public void unfollowPost(String idPost, String idUser) {
        Optional<Post> optionalPost = postRepository.findById(idPost);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Remove the userId from the followedBy list if it exists
            post.getFollowedBy().remove(idUser);

            postRepository.save(post);
        } else {
            // Handle the case where the Post with the given idPost doesn't exist
            throw new EntityNotFoundException("Post not found with id: " + idPost);
        }
    }
    @Override
    public void unfollowArticle(String idArticle, String idUser) {
        Optional<Article> optionalPost = articleRepository.findById(idArticle);

        if (optionalPost.isPresent()) {
            Article a = optionalPost.get();

            // Remove the userId from the followedBy list if it exists
            a.getFollowedBy().remove(idUser);

            articleRepository.save(a);
        } else {
            // Handle the case where the Post with the given idPost doesn't exist
            throw new EntityNotFoundException("Article not found with id: " + idArticle);
        }
    }

    @Override
    public List<Post> getFollowedArticlesByUserId(String userId) {
        return articleRepository.getFollowedArticlesByUserId(userId);
    }

    @Override
    public void voteUpPost(String idPost, String userId) {
        Post p = postRepository.findById(idPost).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + idPost));;
        if (p.getRating() == null) {
            p.setRating(new ArrayList<>());
        }
        // Check if the userId is not already in the followedBy list to avoid duplicates
        if (!p.getRating().contains(userId)) {
            p.getRating().add(userId);
            postRepository.save(p);
        }
    }

    @Override
    public void voteDownPost(String idPost, String userId) {
        Post p = postRepository.findById(idPost) .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + idPost));;
        // Remove the userId from the followedBy list if it exists
        p.getRating().remove(userId);

        postRepository.save(p);
    }

    @Override
    public void voteUpArticle(String idArticle, String userId) {
        Article a = articleRepository.findById(idArticle).orElseThrow(() -> new EntityNotFoundException("article not found with id: " + idArticle));
        ;
        if (a.getScore() == null) {
            a.setScore(new ArrayList<>());
        }
        // Check if the userId is not already in the followedBy list to avoid duplicates
        if (!a.getScore().contains(userId)) {
            a.getScore().add(userId);
            articleRepository.save(a);
        }
    }
    @Override
    public void voteDownArticle(String idArticle, String userId) {
        Article a = articleRepository.findById(idArticle) .orElseThrow(() -> new EntityNotFoundException("article not found with id: " + idArticle));;
        // Remove the userId from the followedBy list if it exists
        a.getScore().remove(userId);

        articleRepository.save(a);

    }

    @Override
    public boolean deleteReply(String id) {
        if (replyRepository.existsById(id)) {
            replyRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the reply with the given id doesn't exist
    }

    @Override
    public boolean deleteRe_reply(String id) {
        if (re_replyRepository.existsById(id)) {
            re_replyRepository.deleteById(id);
            return true;
        }
        return false;    }

    @Override
    public List<Post> getPostsByArticle(String id) {
        return postRepository.getPostsByArticleId(id);
    }


    public String chat(String prompt) {
        try {
            ChatGPTRequest request = new ChatGPTRequest("gpt-3.5-turbo", prompt); // Assuming model is defined elsewhere
            ChatGptResponse chatGptResponse = template.postForObject("https://api.openai.com/v1/chat/completions", request, ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {

            return null;
        }
    }



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






