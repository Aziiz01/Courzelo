package tn.esprit.pidev.services.Implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Service
@AllArgsConstructor
public class ServicesImp implements IService {
    private final RestTemplate template;
    private static final Logger logger = LoggerFactory.getLogger(CustomBotController.class); // Declare logger


    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private Re_replyRepository re_replyRepository;



    // Other methods you have in the class...

    @Override
    public Reply addReply(Reply reply) {
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

    @Override
    public Re_reply addRe_reply(Re_reply re_reply) {
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


    @Override
    public Post addPost(Post post) {
        //String reponse = chat(post.getContext());

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
            if (updatedArticle.getPosts() != null) {
                originalArticle.setPosts(updatedArticle.getPosts());
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
    public Reply updateReply(String id, Reply updatedReply) {
        Optional<Reply> existingReply = replyRepository.findById(id);

        if (existingReply.isPresent()) {
           // String reponse = chat(updatedReply.getContext());
          //  if ("valid".equals(reponse)) {
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


        return null; // Handle the case where the reply with the given id doesn't exist
    }

    @Override
    public Re_reply updateRe_reply(String id, Re_reply updatedRe_reply) {
        Optional<Re_reply> existingRe_reply = re_replyRepository.findById(id);

        if (existingRe_reply.isPresent()) {
            // String reponse = chat(updatedReply.getContext());
            //  if ("valid".equals(reponse)) {
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


        return null; // Handle the case where the reply with the given id doesn't exist    }
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


    public String chat(String prompt) {
        try {
            ChatGPTRequest request = new ChatGPTRequest("gpt-3.5-turbo", prompt); // Assuming model is defined elsewhere
            ChatGptResponse chatGptResponse = template.postForObject("https://api.openai.com/v1/chat/completions", request, ChatGptResponse.class);
            return chatGptResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return null;
        }
    }

}
