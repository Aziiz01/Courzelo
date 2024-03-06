import { Component, OnInit } from '@angular/core';
import { Activity } from 'src/app/entities/activity';
import { ActivityService } from 'src/app/services/activity.service';

@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {
 
  searchName: string = "";
  activity?: Activity[];
  totalItems = 0;

  constructor(private activityService: ActivityService){};

  ngOnInit(): void {
    console.log('ActivityComponent initialized');
    this.retrieveActivities();
  }

  retrieveActivities(): void {
    this.activityService.getAllActivities().subscribe({
      next: (data) => {
        this.activity = data;
        this.totalItems = data.length;
        console.log('Activities fetched:', data); // Confirming data fetched
      },
      error: (e) => console.error(e)
    });
  }




  searchActivities(): void {
    this.activityService.searchActivities(this.searchName).subscribe({
      next: (data) => {
        this.activity = data;
      },
      error: (e) => console.error(e)
    });
  }
  





}
