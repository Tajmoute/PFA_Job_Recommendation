import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css'],
  imports: [
    NgIf,
    FormsModule,
    NgForOf
  ],
  standalone: true
})
export class UserManagementComponent implements OnInit {
  users: any[] = [];
  userModel: any = {
    username: '',
    password: '',
    email: '',
    phoneNumber: '',
    role: { name: 'USER' }, // Adjusted role format
  };
  showForm: boolean = false;
  isEditing: boolean = false;
  roles = ['ADMIN', 'USER', 'RECRUITER'];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe((data) => {
      this.users = data.map((user: any) => ({
        ...user,
        roleName: user.role.name.replace('ROLE_', ''), // Extract role name for display
      }));
    });
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    this.resetForm();
  }

  addUser(): void {
    this.userService.createUser(this.userModel).subscribe(() => {
      this.loadUsers();
      this.toggleForm(); // Hide form after adding user
    });
  }

  editUser(user: any): void {
    this.isEditing = true;
    this.showForm = true; // Show the form when editing
    this.userModel = {
      ...user,
      role: { name: user.roleName }, // Preload role data correctly
    };
  }

  saveUser(): void {
    if (this.userModel.id) {
      this.userService.updateUser(this.userModel.id, this.userModel).subscribe(() => {
        this.loadUsers();
        this.toggleForm(); // Hide form after saving user
      });
    }
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(() => {
      this.loadUsers();
    });
  }

  resetForm(): void {
    this.isEditing = false;
    this.userModel = {
      username: '',
      password: '',
      email: '',
      phoneNumber: '',
      role: { name: 'USER' }, // Default role
    };
  }
}
