package com.example.springback.Entity;



import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "clubs")
public class Club {
    private String _id;
    private String clubName;
   /* @OneToMany(mappedBy = "club")
    private List<Activity> activities;*/

    public Club() {
    }

  /*  public Club(String _id, String clubName, List<Activity> activities) {
        this._id = _id;
        this.clubName = clubName;
        this.activities = activities;
    }*/

    public Club(String _id, String clubName) {
        this._id = _id;
        this.clubName = clubName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

   /* public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }*/

  /*  @Override
    public String toString() {
        return "Club{" +
                "_id='" + _id + '\'' +
                ", clubName='" + clubName + '\'' +
                ", activities=" + activities +
                '}';
    }
*/

    @Override
    public String toString() {
        return "Club{" +
                "_id='" + _id + '\'' +
                ", clubName='" + clubName + '\'' +
                '}';
    }
}
