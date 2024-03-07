package com.example.springback.Controller;
import com.example.springback.Entity.Activity;
import com.example.springback.Entity.ActivityStats;
import com.example.springback.Service.ActivityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/activity")
public class ActivityController {
    @Autowired
    private ActivityServices  activityServices;

    @Autowired
    private ObjectMapper objectMapper; // Jackson's JSON mapper

/*    @PostMapping(value="/save")
    public String saveActivity(
            @RequestParam("activity") String activityString,
            @RequestParam("image") MultipartFile image) {

        try {
            // Convert activityString JSON to Activity object
            Activity activity = objectMapper.readValue(activityString, Activity.class);

            // Now pass both the Activity object and the MultipartFile to the service
            activityServices.saveOrUpdate(activity, image);

            return activity.get_id(); // Assuming you have a getter for the ID
        } catch (IOException e) {
            // Handle the error scenario properly
            e.printStackTrace();
            return "Error processing the request";
        }
    }*/


   /* @PostMapping("/save")
    public String saveActivity(
            @RequestParam("activityName") String activityName,
            @RequestParam("activityAddress") String activityAddress,
            @RequestParam("mobile") String mobile,
            @RequestParam("image") MultipartFile image,
            @RequestParam("dateOfActivity") @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateOfActivity) {
        Activity newActivity = new Activity();
        newActivity.setActivityName(activityName);
        newActivity.setActivityAddress(activityAddress);
        newActivity.setMobile(mobile);
        newActivity.setDateOfActivity(dateOfActivity);

        activityServices.saveOrUpdate(newActivity,image);
        return newActivity.get_id();
    }*/



    @PostMapping(value="/save")
    private String saveActivity(@RequestBody Activity activities)
    {
        activityServices.saveorUpdate(activities);
        return activities.get_id();
    }
    @PostMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> submitRating(@RequestBody Map<String, Object> requestData) {
        // Get the activity ID and rating from the request body
        String activityId = (String) requestData.get("_id");
        Integer rating = (Integer) requestData.get("rating");

        // Check if required parameters are present in the request body
        if (activityId == null || rating == null) {
            return ResponseEntity.badRequest().build(); // Return a 400 Bad Request response if parameters are missing
        }

        // Retrieve the activity associated with the activity ID from the database
        Optional<Activity> optionalActivity = activityServices.getActivitytById2(activityId);
        if (!optionalActivity.isPresent()) {
            return ResponseEntity.notFound().build(); // Return a 404 Not Found response if the activity is not found
        }

        // Update the rating of the activity with the new rating
        Activity activity = optionalActivity.get();
        activity.setRating(rating);

        // Save the updated activity in the database
        Activity updatedActivity = activityServices.save(activity);

        return ResponseEntity.ok(updatedActivity); // Return a 200 OK response with the updated activity
    }


    @GetMapping(value="/getAll")
    public Iterable <Activity> getActivities()
    {

        return activityServices.listAll();
    }

    @PutMapping(value = "/edit/{id}")
    private Activity updateActivity(@RequestBody Activity activity, @PathVariable(name = "id") String id) {
        activity.set_id(id);
        activityServices.saveorUpdate(activity);
        return activity;
    }
    /* @PutMapping(value = "/edit/{id}")
     private Activity updateActivity(@RequestBody Activity activity, @PathVariable(name = "id") String id,@RequestParam("image") MultipartFile image) {
         activity.set_id(id);
         activityServices.saveOrUpdate(activity,image);
         return activity;
     }*/
    @DeleteMapping("/delete/{id}")
    private void deleteActivity(@PathVariable("id") String _id) {
        activityServices.deleteActivity(_id);
    }
    @RequestMapping("/search/{id}")
    private Activity getActivities(@PathVariable(name = "id") String activityId) {
        return activityServices.getActivityByID(activityId);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Activity>> searchActivities(@RequestParam String name) {
        List<Activity> searchResults = activityServices.searchByName(name);
        return ResponseEntity.ok(searchResults);
    }
    @GetMapping("/stats")
    public ResponseEntity<?> getActivitiesStatsByClub() {
        List<ActivityStats> stats = activityServices.countTotalActivitiesByClub();
        return ResponseEntity.ok(stats);
    }



}