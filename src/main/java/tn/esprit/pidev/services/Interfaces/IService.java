package tn.esprit.pidev.services.Interfaces;

import jakarta.mail.MessagingException;
import tn.esprit.pidev.entities.Article;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Re_reply;
import tn.esprit.pidev.entities.Reply;
import java.util.List;
import java.util.Optional;

public interface IService {
    Reply addReply(Reply reply) ;
    Re_reply addRe_reply(Re_reply re_reply);

    Post addPost(Post post) throws MessagingException;

    Article addArticle(Article article);

    List<Article> getAllArticles();

    Optional<Article> getArticleById(String id);

    Article updateArticle(String id, Article updatedArticle);

    boolean deleteArticle(String id);

    List<Post> getAllPosts();

    Optional<Post> getPostById(String id);

    Post updatePost(String id, Post updatedPost);

    boolean deletePost(String id);
    List<Reply> getAllReplies();
    List<Re_reply> getAllRe_replies();

    Optional<Reply> getReplyById(String id);
    Optional<Re_reply> getRe_replyById(String id);
    List<Post> getPostsByArticleId(String articleId);
    Reply updateReply(String id, Reply updatedReply);
    Re_reply updateRe_reply(String id, Re_reply updatedRe_reply);
    void followPost(String idPost,String idUser) throws MessagingException;
    public List<Post> getFollowedPostsByUserId(String userId) ;

    void followArticle(String idArticle,String idUser);
     void unfollowPost(String idPost, String idUser);
    void unfollowArticle(String idArticle, String idUser);
    List<Post> getFollowedArticlesByUserId(String userId);
    void voteUpPost(String idPost, String userId);
    void voteDownPost(String idPost, String userId);
    void voteUpArticle(String idArticle, String userId);
    void voteDownArticle(String idArticle, String userId);

    boolean deleteReply(String id);
    boolean deleteRe_reply(String id);
    List<Post> getPostsByArticle(String id);
}
