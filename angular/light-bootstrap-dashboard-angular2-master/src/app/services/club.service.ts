import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Club } from '../entities/club';

@Injectable({
  providedIn: 'root'
})
export class ClubService {
  private API_URL = 'http://localhost:8081/api/v1/club';

  constructor(private http: HttpClient) {}

  registerClub(bodyData: any): Observable<any> {
    return this.http.post(`${this.API_URL}/save`, bodyData, { responseType: 'text' });
  }

  getAllActivities(): Observable<any> {
    return this.http.get(`${this.API_URL}/getAll`);
  }

  updateClub(clubId: string, bodyData: any): Observable<any> {
    return this.http.put(`${this.API_URL}/edit/${clubId}`, bodyData, { responseType: 'text' });
  }

  deleteClub(clubId: string): Observable<any> {
    return this.http.delete(`${this.API_URL}/delete/${clubId}`, { responseType: 'text' });
  }
  searchActivities(name: string): Observable<Club[]> {
    return this.http.get<Club[]>(`${this.API_URL}/search`, { params: { name } });
  }
  

}
