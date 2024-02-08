package tn.esprit.pidev.services.Interfaces;

import tn.esprit.pidev.entities.Article;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;
import java.util.List;
import java.util.Optional;

public interface IService {
    Reply addReply(Reply reply);

    Post addPost(Post post);

    Article addArticle(Article article);

    List<Article> getAllArticles();

    Optional<Article> getArticleById(String id);

    Article updateArticle(String id, Article updatedArticle);

    boolean deleteArticle(String id);

    Discussion addDiscussion(Discussion discussion);

    List<Discussion> getAllDiscussions();

    Optional<Discussion> getDiscussionById(String id);

    Discussion updateDiscussion(String id, Discussion updatedDiscussion);

    boolean deleteDiscussion(String id);

    List<Post> getAllPosts();

    Optional<Post> getPostById(String id);

    Post updatePost(String id, Post updatedPost);

    boolean deletePost(String id);
    List<Reply> getAllReplies();

    Optional<Reply> getReplyById(String id);

    Reply updateReply(String id, Reply updatedReply);

    boolean deleteReply(String id);
}
