import { Component } from '@angular/core';
import { ActivityService } from 'app/services/activity.service';


@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss']
})
export class ActivityComponent {
  ActivityArray: any[] = [];
  activityName: string = "";
  activityAddress: string = "";
  mobile: number = 0;
  dateOfActivity: string = ""; // Add this line
  currentActivityID = "";

  constructor(private activityService : ActivityService) {
    this.getAllActivity;
  }

  ngOnInit(): void {
    this.getAllActivity(); 
  }
  
  register() {
    const bodyData = {
      "activityName": this.activityName,
      "activityAddress": this.activityAddress,
      "mobile": this.mobile,
      "dateOfActivity": this.dateOfActivity
    };

    this.activityService.registerActivity(bodyData).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Activity Registered Successfully");
      this.getAllActivity();
      this.clearForm();
    });
  }

  getAllActivity() {
    this.activityService.getAllActivities().subscribe((resultData: any) => {
      console.log(resultData);
      this.ActivityArray = resultData;
    });
  }

  setUpdate(data: any) {
    this.activityName = data.activityName;
    this.activityAddress = data.activityAddress;
    this.mobile = data.mobile;
    this.dateOfActivity = data.dateOfActivity; 
    this.currentActivityID = data._id;
  }

  UpdateRecords() {
    const bodyData = {
      "activityName": this.activityName,
      "activityAddress": this.activityAddress,
      "mobile": this.mobile,
      "dateOfActivity": this.dateOfActivity
    };

    this.activityService.updateActivity(this.currentActivityID, bodyData).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Activity Updated Successfully");
      this.getAllActivity();
      this.clearForm();
    });
  }

  save() {
    if (this.currentActivityID == '') {
      this.register();
    } else {
      this.UpdateRecords();
    }
  }

  setDelete(data: any) {
    this.activityService.deleteActivity(data._id).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Activity Deleted Successfully");
      this.getAllActivity();
      this.clearForm();
    });
  }
  
  clearForm() {
    this.activityName = '';
    this.activityAddress = '';
    this.mobile = 0;
    this.dateOfActivity = "";
    this.currentActivityID = '';
  }
}
