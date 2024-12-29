import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import { AuthService } from './services/auth.service';
import {NgIf} from '@angular/common';



@Component({

  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [
    RouterOutlet,
    NgIf,
    RouterLink
  ]
})


  export class AppComponent implements OnInit {

    title = 'Job Recommender';
    username: string | null = null;
  constructor(public authService: AuthService) {}

  ngOnInit(): void {
    // Subscribe to username changes
    this.authService.username$.subscribe((username) => {
      this.username = username;
    });

    // Set initial username if already authenticated
    if (this.authService.isAuthenticated()) {
      this.username = this.authService.getUsername();
    }
  }

  logout(): void {
    this.authService.logout();
    alert('You have been logged out successfully!');
    this.username = null; // Clear the username on logout

  }
}
