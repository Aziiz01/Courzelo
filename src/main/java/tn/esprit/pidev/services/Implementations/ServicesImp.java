package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.repositories.ArticleRepository;
import tn.esprit.pidev.repositories.DiscussionRepository;
import tn.esprit.pidev.repositories.PostRepository;
import tn.esprit.pidev.repositories.ReplyRepository;
import tn.esprit.pidev.services.Interfaces.IService;

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

    public Reply addReply(Reply reply) {
return replyRepository.save(reply);
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }


}
