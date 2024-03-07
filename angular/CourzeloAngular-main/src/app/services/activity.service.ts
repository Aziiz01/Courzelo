import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activity } from '../entities/activity';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {
  private API_URL = 'http://localhost:8081/api/v1/activity';

  constructor(private http: HttpClient) {}

  registerActivity(bodyData: any): Observable<any> {
    return this.http.post(`${this.API_URL}/save`, bodyData, { responseType: 'text' });
  }

  getAllActivities(): Observable<any> {
    return this.http.get(`${this.API_URL}/getAll`);
  }

  updateActivity(activityId: string, bodyData: any): Observable<any> {
    return this.http.put(`${this.API_URL}/edit/${activityId}`, bodyData, { responseType: 'text' });
  }

  deleteActivity(activityId: string): Observable<any> {
    return this.http.delete(`${this.API_URL}/delete/${activityId}`, { responseType: 'text' });
  }
  searchActivities(name: string): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${this.API_URL}/search`, { params: { name } });
  }
  submitRating(activityId: string, rating: number): Observable<any> {
    const body = {
      _id: activityId,
      rating: rating
    };
  
    return this.http.post<any>(`${this.API_URL}/rating`, body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

}
