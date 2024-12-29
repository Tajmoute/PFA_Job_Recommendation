import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobPostingService {
  private baseUrl = 'http://localhost:8080/api/job-postings'; // Base URL for job postings API

  constructor(private http: HttpClient) {}


  getPagedJobPostings(page: number, size: number): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/paged?page=${page}&size=${size}`
    );
  }
  // Fetch all job postings
  getAllJobPostings(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  // Fetch a single job posting by ID
  getJobPostingById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // Create a new job posting
  createJobPosting(jobPosting: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, jobPosting);
  }

  // Update an existing job posting
  updateJobPosting(id: number, jobPosting: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, jobPosting);
  }

  // Delete a job posting by ID
  deleteJobPosting(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
