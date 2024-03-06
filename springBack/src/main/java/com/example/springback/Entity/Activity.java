package com.example.springback.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import org.bson.types.Binary;
import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "activities")
public class Activity {
    @Id
    private String _id;
    private String activityName;
    private String activityAddress;
    private String mobile;
    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

    private Date dateOfActivity;

    public Activity(String _id, String activityName, String activityAddress, String mobile, Date dateOfActivity) {
        this._id = _id;
        this.activityName = activityName;
        this.activityAddress = activityAddress;
        this.mobile = mobile;
        this.dateOfActivity = dateOfActivity;
    }

    public Activity(String _id, String activityName, String activityAddress, String mobile) {
        this._id = _id;
        this.activityName = activityName;
        this.activityAddress = activityAddress;
        this.mobile = mobile;
    }

    public Activity(String _id, String activityName, String activityAddress, String mobile, String image, Date dateOfActivity) {
        this._id = _id;
        this.activityName = activityName;
        this.activityAddress = activityAddress;
        this.mobile = mobile;
        this.image = image;
        this.dateOfActivity = dateOfActivity;
    }

    public Activity() {
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateOfActivity() {
        return dateOfActivity;
    }

    public void setDateOfActivity(Date dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "_id='" + _id + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityAddress='" + activityAddress + '\'' +
                ", mobile='" + mobile + '\'' +
                ", image=" + image +
                ", dateOfActivity=" + dateOfActivity +
                '}';
    }
}