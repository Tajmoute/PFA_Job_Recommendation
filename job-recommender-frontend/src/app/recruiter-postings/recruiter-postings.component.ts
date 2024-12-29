import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-recruiter-postings',
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './recruiter-postings.component.html',
  standalone: true,
  styleUrl: './recruiter-postings.component.css'
})
export class RecruiterPostingsComponent implements OnInit {
  myPostings: any[] = [];
  errorMessage = '';

  constructor(private http: HttpClient) {}
  // If you prefer to use JobPostingService instead:
  // constructor(private jobPostingService: JobPostingService) {}

  ngOnInit(): void {
    this.loadMyPostings();
  }

  loadMyPostings(): void {
    // Direct HttpClient call:
    this.http.get<any[]>('http://localhost:8080/api/job-postings/mine')
      .subscribe({
        next: (data) => {
          this.myPostings = data;
        },
        error: (err) => {
          this.errorMessage = 'Failed to load your postings.';
          console.error(err);
        }
      });

    /*
    // If using a custom jobPostingService method:
    this.jobPostingService.getMyPostings().subscribe({
      next: (data) => {
        this.myPostings = data;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load your postings.';
        console.error(err);
      }
    });
    */
  }

  // (Optional) If you want to let the recruiter delete or edit:
  deletePosting(id: number): void {
    // You’d call a DELETE endpoint, then reload:
    this.http.delete(`http://localhost:8080/api/job-postings/${id}`).subscribe({
      next: () => {
        this.loadMyPostings(); // refresh after deletion
      },
      error: (err) => {
        this.errorMessage = 'Failed to delete posting.';
        console.error(err);
      }
    });
  }
}
