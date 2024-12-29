import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Import FormsModule for [(ngModel)]
import {Router, RouterLink} from '@angular/router';
import { AuthService } from '../services/auth.service';
import {NgIf} from '@angular/common';
@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule, NgIf, RouterLink], // Add FormsModule here
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        // Save the token using AuthService
        this.authService.saveToken(response.token);

        // Navigate to recommendations page
        this.router.navigate(['/recommendations']);
      },
      (error) => {
        // Handle login errors
        this.errorMessage = 'Invalid username or password.';
        console.error('Login error:', error);
      }
    );
  }
}
