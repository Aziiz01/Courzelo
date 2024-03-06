package com.example.springback.Controller;
import com.example.springback.Entity.Activity;
import com.example.springback.Service.ActivityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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

}