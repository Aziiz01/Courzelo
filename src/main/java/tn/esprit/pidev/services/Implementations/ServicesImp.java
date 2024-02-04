package tn.esprit.pidev.services.Implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Reply;
import tn.esprit.pidev.repositories.ReplyRepository;
import tn.esprit.pidev.services.Interfaces.IReplyService;

@Slf4j
@Service
@AllArgsConstructor
public class ServicesImp implements IReplyService {
    @Autowired
  private ReplyRepository replyRepository;
    public Reply addReply(Reply reply) {
return replyRepository.save(reply);
    }
}
