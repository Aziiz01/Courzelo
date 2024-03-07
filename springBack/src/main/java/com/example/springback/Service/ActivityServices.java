package com.example.springback.Service;
import com.example.springback.Entity.Activity;
import com.example.springback.Entity.ActivityStats;
import com.example.springback.Repo.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; // For MultipartFile
import org.bson.types.Binary; // For Binary

import java.io.File;
import java.io.IOException; // For IOException
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServices {
    @Autowired
    private ActivityRepo repo;
    String imageUrl = "images/";
    /*public void saveOrUpdate(Activity activity, MultipartFile image) {
        try {
            if (!image.isEmpty()) {
                String imageUrl = "images/" + image.getOriginalFilename();
                File uploadDirectory = new File("C:\\Users\\hamza\\OneDrive\\Bureau\\KRAYA\\4SAE\\launchIt\\src\\main\\resources\\static\\images\\");
                image.transferTo(new File(uploadDirectory, image.getOriginalFilename()));
                activity.setImage(imageUrl);
            }
            // Save or update activity in the database
// Your code to save or update activity goes here
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }*/


    /*public void saveOrUpdate(Activity activity, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Binary imageBinary = new Binary(imageFile.getBytes());
                activity.setImage(imageBinary);
            } catch (IOException e) {
                // Handle the error scenario
                e.printStackTrace();
            }
        }
        // No changes needed for the date as it should already be set in the Activity object
        repo.save(activity);
    }*/
    public void saveorUpdate(Activity activities) {

        repo.save(activities);
    }
    public Activity save(Activity activities) {

       return repo.save(activities);
    }
    public Iterable<Activity> listAll() {
        return this.repo.findAll();
    }

    public void deleteActivity(String id) {
        this.repo.deleteById(id);
    }

    public Activity getActivityByID(String activityId) {

        return this.repo.findById(activityId).get();
    }

    public List<Activity> searchByName(String name) {
        return this.repo.findByActivityNameContainingIgnoreCase(name);
    }
    public Optional<Activity> getActivitytById2(String id) {
        return repo.findById(id);
    }

    public List<ActivityStats> countTotalActivitiesByClub() {
        return repo.countTotalActivitiesByClub();
    }
}