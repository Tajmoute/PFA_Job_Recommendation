import { Component, OnInit } from '@angular/core';
import { JobPostingService } from '../services/job-posting.service';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-job-management',
  templateUrl: './job-management.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  styleUrls: ['./job-management.component.css']
})
export class JobManagementComponent implements OnInit {
  jobPostings: any[] = []; // List of job postings
  currentJob = {
    id: null, // Add the id property to support editing
    jobTitle: '',
    jobDescription: '',
    jobType: '',
    location: '',
    organization: '',
    sector: '',
    city: '',
    stateCountry: ''
  }; // Model for adding/editing jobs
  isEditing = false; // Toggle between add/edit modes

  constructor(private jobPostingService: JobPostingService) {}

  ngOnInit(): void {
    this.fetchJobPostings(); // Fetch all job postings on component load
  }

  // Fetch all job postings
  fetchJobPostings(): void {
    this.jobPostingService.getAllJobPostings().subscribe((data) => {
      this.jobPostings = data;
    });
  }

  // Add a new job posting
  addJobPosting(): void {
    this.jobPostingService.createJobPosting(this.currentJob).subscribe(() => {
      this.fetchJobPostings(); // Refresh the list
      this.resetForm(); // Reset the form after adding
    });
  }

  // Edit an existing job posting
  editJobPosting(job: any): void {
    this.isEditing = true;
    this.currentJob = { ...job }; // Copy job, including id, to currentJob
  }


  // Save changes to an existing job posting
  saveJobPosting(): void {
    if (this.currentJob.id) { // Check if it's an existing job
      this.jobPostingService.updateJobPosting(this.currentJob.id, this.currentJob).subscribe(() => {
        this.fetchJobPostings(); // Refresh the job list
        this.resetForm(); // Reset the form after saving
      });
    }
  }


  // Delete a job posting
  deleteJobPosting(id: number): void {
    this.jobPostingService.deleteJobPosting(id).subscribe(() => {
      this.fetchJobPostings(); // Refresh the list after deletion
    });
  }

  // Reset the form and toggle back to add mode
  resetForm(): void {
    this.isEditing = false;
    this.currentJob = {
      id: null, // Ensure id is explicitly set to null
      jobTitle: '',
      jobDescription: '',
      jobType: '',
      location: '',
      organization: '',
      sector: '',
      city: '',
      stateCountry: ''
    };


}
}
