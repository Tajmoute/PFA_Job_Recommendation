import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobRecommendationService {
  private apiUrl = 'http://localhost:8080/api'; // Replace with your Spring Boot endpoint

  constructor(private http: HttpClient) {}

  getRecommendedJobs(skills: string[]): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.apiUrl}/recommend`, { skills }, { headers });
  }
}
