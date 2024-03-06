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
  currentPage = 1; // Page actuelle
  pageSize = 2; // Nombre d'articles par page

  constructor(private activityService: ActivityService){};

  ngOnInit(): void {
    console.log('ActivityComponent initialized');
    this.retrieveActivities();
  }

  retrieveActivities(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.activityService.getAllActivities().subscribe({
      next: (data) => {

        this.totalItems = data.length;
        //this.activity = data;

        this.activity = data.slice(startIndex, endIndex);
      
        //this.totalItems = data.length;
        console.log('Activities fetched:', data); // Confirming data fetched
      },
      error: (e) => console.error(e)
    });
  }

get totalPages(): number {
    return Math.ceil(this.totalItems / this.pageSize);
  }
  totalPagesArray(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  setCurrentPage(page: number): void {
    this.currentPage = page;
    this.retrieveActivities();
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
