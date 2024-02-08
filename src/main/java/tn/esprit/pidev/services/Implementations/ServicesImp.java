package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.entities.Article;
import tn.esprit.pidev.repositories.ArticleRepository;
import tn.esprit.pidev.repositories.DiscussionRepository;
import tn.esprit.pidev.repositories.PostRepository;
import tn.esprit.pidev.repositories.ReplyRepository;
import tn.esprit.pidev.services.Interfaces.IService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ServicesImp implements IService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    // Other methods you have in the class...

    @Override
    public Reply addReply(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

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
        Optional<Article> existingArticle = articleRepository.findById(id);

        if (existingArticle.isPresent()) {
            Article originalArticle = existingArticle.get();

            // Update only the fields that are not null in the updatedArticle
            if (updatedArticle.getScore() != 0) {
                originalArticle.setScore(updatedArticle.getScore());
            }
            if (updatedArticle.getCategory() != null) {
                originalArticle.setCategory(updatedArticle.getCategory());
            }
            if (updatedArticle.getDiscussions() != null) {
                originalArticle.setDiscussions(updatedArticle.getDiscussions());
            }

            // Save the updated article
            return articleRepository.save(originalArticle);
        }

        return null; // Handle the case where the article with the given id doesn't exist
    }

    @Override
    public boolean deleteArticle(String id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the article with the given id doesn't exist
    }

    @Override
    public Discussion addDiscussion(Discussion discussion) {
        return discussionRepository.save(discussion);
    }

    @Override
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    @Override
    public Optional<Discussion> getDiscussionById(String id) {
        return discussionRepository.findById(id);
    }

    @Override
    public Discussion updateDiscussion(String id, Discussion updatedDiscussion) {
        Optional<Discussion> existingDiscussion = discussionRepository.findById(id);

        if (existingDiscussion.isPresent()) {
            Discussion originalDiscussion = existingDiscussion.get();

            // Update only the fields that are not null in the updatedDiscussion
            if (updatedDiscussion.getPosts() != null) {
                originalDiscussion.setPosts(updatedDiscussion.getPosts());
            }
            if (updatedDiscussion.getArticle() != null) {
                originalDiscussion.setArticle(updatedDiscussion.getArticle());
            }
            if (updatedDiscussion.getIdArticle() != null) {
                originalDiscussion.setIdArticle(updatedDiscussion.getIdArticle());
            }

            // Save the updated discussion
            return discussionRepository.save(originalDiscussion);
        }

        return null; // Handle the case where the discussion with the given id doesn't exist
    }

    @Override
    public boolean deleteDiscussion(String id) {
        if (discussionRepository.existsById(id)) {
            discussionRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the discussion with the given id doesn't exist
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
        Optional<Post> existingPost = postRepository.findById(id);

        if (existingPost.isPresent()) {
            Post originalPost = existingPost.get();

            // Update only the fields that are not null in the updatedPost
            if (updatedPost.getTitre() != null) {
                originalPost.setTitre(updatedPost.getTitre());
            }
            if (updatedPost.getImageUrl() != null) {
                originalPost.setImageUrl(updatedPost.getImageUrl());
            }
            if (updatedPost.getIdUser() != null) {
                originalPost.setIdUser(updatedPost.getIdUser());
            }
            if (updatedPost.getDiscussion() != null) {
                originalPost.setDiscussion(updatedPost.getDiscussion());
            }
            if (updatedPost.getReplies() != null) {
                originalPost.setReplies(updatedPost.getReplies());
            }

            // Save the updated post
            return postRepository.save(originalPost);
        }

        return null; // Handle the case where the post with the given id doesn't exist
    }

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
    public Optional<Reply> getReplyById(String id) {
        return replyRepository.findById(id);
    }

    @Override
    public Reply updateReply(String id, Reply updatedReply) {
        Optional<Reply> existingReply = replyRepository.findById(id);

        if (existingReply.isPresent()) {
            Reply originalReply = existingReply.get();

            // Update only the fields that are not null in the updatedReply
            if (updatedReply.getIdUser() != 0) {
                originalReply.setIdUser(updatedReply.getIdUser());
            }
            if (updatedReply.getRecommondations() != 0) {
                originalReply.setRecommondations(updatedReply.getRecommondations());
            }
            if (updatedReply.isVisibility() != originalReply.isVisibility()) {
                originalReply.setVisibility(updatedReply.isVisibility());
            }

            // Save the updated reply
            return replyRepository.save(originalReply);
        }

        return null; // Handle the case where the reply with the given id doesn't exist
    }

    @Override
    public boolean deleteReply(String id) {
        if (replyRepository.existsById(id)) {
            replyRepository.deleteById(id);
            return true;
        }
        return false; // Handle the case where the reply with the given id doesn't exist
    }

}
