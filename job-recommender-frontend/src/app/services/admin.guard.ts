import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const userRole = this.authService.getUserRole(); // Fetch user role from token or service
    console.log('[AdminGuard] userRole is:', userRole);
    if (userRole === 'ROLE_ADMIN') {
      return true; // Allow access for admins only
    }
    this.router.navigate(['/']); // Redirect to home if unauthorized
    return false;
  }
}
