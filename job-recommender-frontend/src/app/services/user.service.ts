import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createUser(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, user);
  }

  updateUser(id: number, user: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
