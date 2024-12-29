import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthRedirectGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      // If the user is logged in, redirect to /recommendations
      this.router.navigate(['/recommendations']);
      return false; // Prevent access to the current route
    }
    return true; // Allow access if not logged in
  }
}
