import { Component } from '@angular/core';
import { JobRecommendationService } from '../services/job-recommendation.service';
import {FormsModule} from '@angular/forms';
import {CommonModule, NgForOf, NgIf, SlicePipe} from '@angular/common';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-job-recommendations',
  standalone: true,
  templateUrl: './job-recommendations.component.html',
  styleUrls: ['./job-recommendations.component.css'],
  imports: [FormsModule, CommonModule, RouterOutlet],
})
export class JobRecommendationsComponent {
  title = 'Job Recommender';
  userSkillsInput = '';
  jobRecommendations: any[] = [];
  errorMessage = '';
  // Set to track expanded job descriptions by their unique ID
  expandedJobs: Set<string> = new Set();

  constructor(private jobService: JobRecommendationService) {}

  getRecommendations(): void {
    this.errorMessage = '';
    const skillsArray = this.userSkillsInput
      .split(',')
      .map((skill) => skill.trim())
      .filter((skill) => skill);

    if (skillsArray.length === 0) {
      this.errorMessage = 'Please enter at least one skill.';
      return;
    }

    this.jobService.getRecommendedJobs(skillsArray).subscribe(
      (data) => {
        this.jobRecommendations = data;
      },
      (error) => {
        this.errorMessage = 'Failed to fetch recommendations. Please try again.';
        console.error('API Error:', error);
      }
    );
  }

  // Toggle the expanded state of a job description
  toggleFullDescription(job: any): void {
    if (this.expandedJobs.has(job.uniqId)) {
      this.expandedJobs.delete(job.uniqId);
    } else {
      this.expandedJobs.add(job.uniqId);
    }
  }

  // Check if a job description is expanded
  isExpanded(job: any): boolean {
    return this.expandedJobs.has(job.uniqId);
  }
}
