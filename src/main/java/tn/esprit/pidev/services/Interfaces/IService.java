package tn.esprit.pidev.services.Interfaces;


import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.Reply;
public interface IService {
    public Reply addReply(Reply reply);
    public Post addPost (Post post);
}