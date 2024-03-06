package com.example.springback.Service;

import com.example.springback.Repo.ActivityRepo;
import org.springframework.stereotype.Service;
import com.example.springback.Entity.Club;
import com.example.springback.Repo.ClubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; // For MultipartFile
import org.bson.types.Binary; // For Binary

import java.io.File;
import java.io.IOException; // For IOException
import java.util.List;

@Service
public class ClubService {


    @Autowired
    private ClubRepo repo;

    public Iterable<Club> listAll() {
        return this.repo.findAll();
    }

    public void saveorUpdate(Club clubs) {
        repo.save(clubs);
    }

    public void deleteClub(String id) {
        this.repo.deleteById(id);
    }

    public Club getActivityByID(String clubId) {
        return this.repo.findById(clubId).get();

    }
}
