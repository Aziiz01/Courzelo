package com.example.springback.Entity;

public class ActivityStats {
     // DTO data transfer object
    private String club;
    private int numberOfActivities;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public void setNumberOfActivities(int numberOfActivities) {
        this.numberOfActivities = numberOfActivities;
    }
}
