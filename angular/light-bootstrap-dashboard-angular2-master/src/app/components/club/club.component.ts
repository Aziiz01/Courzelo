import { Component } from '@angular/core';
import { ClubService } from 'app/services/club.service';


@Component({
  selector: 'app-club',
  templateUrl: './club.component.html',
  styleUrls: ['./club.component.scss']
})
export class ClubComponent {
  ClubArray: any[] = [];
  clubName: string = "";
  
  currentClubID = "";

  constructor(private clubService : ClubService) {
    this.getAllClub;
  }

  ngOnInit(): void {
    this.getAllClub(); 
  }
  
  register() {
    const bodyData = {
      "clubName": this.clubName,
     
    };

    this.clubService.registerClub(bodyData).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Club Registered Successfully");
      this.getAllClub();
      this.clearForm();
    });
  }

  getAllClub() {
    this.clubService.getAllActivities().subscribe((resultData: any) => {
      console.log(resultData);
      this.ClubArray = resultData;
    });
  }

  setUpdate(data: any) {
    this.clubName = data.clubName;
   
    this.currentClubID = data._id;
  }

  UpdateRecords() {
    const bodyData = {
      "clubName": this.clubName,
     
    };

    this.clubService.updateClub(this.currentClubID, bodyData).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Club Updated Successfully");
      this.getAllClub();
      this.clearForm();
    });
  }

  save() {
    if (this.currentClubID == '') {
      this.register();
    } else {
      this.UpdateRecords();
    }
  }

  setDelete(data: any) {
    this.clubService.deleteClub(data._id).subscribe((resultData: any) => {
      console.log(resultData);
      alert("Club Deleted Successfully");
      this.getAllClub();
      this.clearForm();
    });
  }

  clearForm() {
    this.clubName = '';
  
    this.currentClubID = '';
  }
}
