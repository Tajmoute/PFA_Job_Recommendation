import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {AuthService} from '../services/auth.service';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common'; // Import FormsModule for [(ngModel)]

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [
    FormsModule,
    NgIf,
    RouterLink
  ]
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  email: string = '';
  phoneNumber: string = '';
  role: string = 'USER'; // Default to USER
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }


  onRegister(): void {
    const userData = {
      username: this.username,
      password: this.password,
      email: this.email,
      phoneNumber: this.phoneNumber,
      role: this.role // Include the role in the registration request

    };

    this.authService.register(userData).subscribe(
      (response) => {
        if (response?.message) {
          this.successMessage = response.message; // Display the success message from the backend
          this.errorMessage = '';
          console.log('Registration successful:', response);

          // Redirect to /login after 3 seconds
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
        } else {
          this.errorMessage = 'Unexpected response format';
          console.error('Unexpected response:', response);
        }
      },
      (error) => {
        this.errorMessage = `Registration failed: ${error.error?.message || 'Unknown error'}`;
        this.successMessage = '';
        console.error('Registration failed:', error);
      }
    );
  }
}
