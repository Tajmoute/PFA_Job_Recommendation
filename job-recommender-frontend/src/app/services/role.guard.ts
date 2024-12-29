import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const userRole = this.authService.getUserRole(); // Fetch user role from token or service
    if (userRole === 'ADMIN' || userRole === 'RECRUITER') {
      return true; // Allow access
    }
    this.router.navigate(['/']); // Redirect to home if unauthorized
    return false;
  }
}
