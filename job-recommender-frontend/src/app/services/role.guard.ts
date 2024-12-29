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
    console.log('[RoleGuard] userRole is:', userRole);
    if (userRole === 'ROLE_ADMIN' || userRole === 'ROLE_RECRUITER') {
      return true; // Allow access
    }
    this.router.navigate(['/']); // Redirect to home if unauthorized
    return false;
  }
}
