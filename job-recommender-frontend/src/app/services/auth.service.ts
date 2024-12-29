import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {BehaviorSubject, Observable} from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authTokenKey = 'authToken'; // Local storage key for the token
  private usernameSubject = new BehaviorSubject<string | null>(null);
  private baseUrl = 'http://localhost:8080/api/auth';

  username$ = this.usernameSubject.asObservable(); // Observable to subscribe to changes

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    // Call the backend login endpoint
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password });
  }

  register(userData: any): Observable<any> {
    // Call the backend register endpoint
    return this.http.post(`${this.baseUrl}/register`, userData);
  }

  saveToken(token: string): void {
    localStorage.setItem(this.authTokenKey, token);
    const username = this.getUsernameFromToken(token);
    this.usernameSubject.next(username); // Notify subscribers
  }

  getToken(): string | null {
    // Retrieve the token from localStorage
    return localStorage.getItem(this.authTokenKey);
  }

  isAuthenticated(): boolean {
    // Check if a token exists to determine authentication
    return !!this.getToken();
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.role || null; // Ensure the backend includes 'role' in the token payload
      } catch (e) {
        console.error('Error decoding token', e);
        return null;
      }
    }
    return null;
  }


  logout() {
    // Clear the token and navigate to the login page
    localStorage.removeItem(this.authTokenKey);
    this.router.navigate(['/']);
  }

  getUsername(): string | null {
    return this.getUsernameFromToken(this.getToken());
  }

  private getUsernameFromToken(token: string | null): string | null {
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.sub || null;
      } catch (e) {
        console.error('Error decoding token', e);
        return null;
      }
    }
    return null;
  }
}
