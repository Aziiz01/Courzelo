import { Component, OnInit } from '@angular/core';
import { Activity } from 'src/app/entities/activity';
import { ActivityService } from 'src/app/services/activity.service';
import { Loader } from '@googlemaps/js-api-loader';
//import { styles } from './mapstyles';



@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.css']
})
export class ActivityComponent implements OnInit {
 
  searchName: string = "";
  activity?: Activity[] ;
  totalItems = 0;
  currentPage = 1; // Page actuelle
  pageSize = 2; // Nombre d'articles par page
  rating: number = 0; // Initialisation à 0
  activity2: Activity | undefined;
  title = 'google-maps';

  //private map: google.maps.Map


  constructor(private activityService: ActivityService){};

  // ngOnInit(): void {
  //   let loader = new Loader({
  //     apiKey: '--APIKEY--'
  //   })

  //   loader.load().then(()=> {

  //     new google.maps.Map(document.getElementById("map"),{
  //       center:{ lat: 51.233334, lng: 	6.783333},
  //       zoom: 6
  //     }
  //     )
  //   }


  //   )

      
  //   /// stuff
  //   console.log('ActivityComponent initialized');
  //   this.retrieveActivities();
  // }

  // ngOnInit(): void {
  //   let loader = new Loader({
  //     apiKey: 'AIzaSyAu_mO7cXmq6woA_NFn-idriInCb5wx4DU' // Make sure to replace with your actual API key
  //   });
  
  //   loader.load().then(() => {
  //     const mapElement = document.getElementById("map");
  //     if (mapElement) { // Check if the element is not null
  //       new google.maps.Map(mapElement, {
  //         center: { lat: 51.233334, lng: 6.783333 },
  //         zoom: 6
  //       });
  //     } else {
  //       console.error('Map element not found');
  //     }
  //   });
  
  //   // ... rest of your code
  //   console.log('ActivityComponent initialized');
  //   this.retrieveActivities();
  // }
  
  ngOnInit(): void {
    let loader = new Loader({
      apiKey: 'AIzaSyA_LLQNcidE1aVQ6Rg44aUqo7Wdvk3crIY', // Replace 'YOUR_API_KEY' with your actual API key
      libraries: ['places'] // This is required for the geocoding service
    });
  
    loader.load().then(() => {
      const mapElement = document.getElementById('map');
      if (mapElement) {
        const map = new google.maps.Map(mapElement, {
          center: { lat: 51.233334, lng: 6.783333 },
          zoom: 6
        });
  
        this.retrieveActivities2(map); // Pass the map as an argument
      } else {
        console.error('Map element not found');
      }
    });
      
    this.retrieveActivities();

    console.log('ActivityComponent initialized');
  }
  
  retrieveActivities2(map: google.maps.Map): void {
    this.activityService.getAllActivities().subscribe((data) => {
      this.activity = data;
      if (this.activity) {
        this.activity.forEach((activity) => {
          if (activity.activityAddress) {
            this.placeMarker(activity.activityAddress, map);
          }
        });
      }
    });
  }
  
  
  
  placeMarker(address: string, map: google.maps.Map): void {
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ address: address }, (results, status) => {
      if (status === 'OK' && results && results[0]) {
        const location = results[0].geometry.location;
        new google.maps.Marker({
          position: location,
          map: map
        });
      } else {
        console.error('Geocode was not successful for the following reason: ' + status);
        // Gérer les erreurs de géocodage ici, par exemple en affichant un message à l'utilisateur ou en consolant l'erreur
      }
    });
  }
  

  retrieveActivities(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.activityService.getAllActivities().subscribe({
      next: (data) => {
        if (this.activity2 && this.activity2.rating) {
          this.rating = this.activity2.rating; // Mettre à jour la valeur du rating lorsque le produit est récupéré
        }
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
  


  setRating(activityId: string, rating: number, activity: any): void {
    // Vérifiez si la note actuelle est la même que la note cliquée
    if (activity.rating === rating) {
        // Si c'est le cas, réinitialisez la note à zéro (déselectionnez-la)
        activity.rating = 0;
    } else {
        // Sinon, mettez à jour la note de l'activité avec la nouvelle valeur
        activity.rating = rating;
        console.log('Rating sélectionné :', rating);
        console.log(activityId);

        // Appel du service pour soumettre la note
        this.activityService.submitRating(activityId, rating)
            .subscribe(
                (response) => {
                    console.log('Activity rating updated in the database:', response);
                    // Ajoutez ici votre logique de traitement de la réponse
                },
                (error) => {
                    console.error('Error updating product rating:', error);
                    // Ajoutez ici votre logique de gestion des erreurs
                }
            );
    }
}

}
  



