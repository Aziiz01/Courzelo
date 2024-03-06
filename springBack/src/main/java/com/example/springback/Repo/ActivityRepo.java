package com.example.springback.Repo;

import com.example.springback.Entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepo extends MongoRepository<Activity,String> {
    List<Activity> findByActivityNameContainingIgnoreCase(String name);
}