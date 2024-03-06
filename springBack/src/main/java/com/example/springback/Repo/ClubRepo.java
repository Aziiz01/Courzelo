package com.example.springback.Repo;

import com.example.springback.Entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepo extends MongoRepository<Club,String> {
}
