package com.example.TestPFA.controller;

import com.example.TestPFA.entity.JobPosting;
import com.example.TestPFA.service.JobPostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")  // Base URL segment
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    // 1) Get All Job Postings
    @GetMapping
    public List<JobPosting> getAllJobPostings() {
        return jobPostingService.getAllJobPostings();
    }

    // 2) Get Single Job Posting by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobPostingById(@PathVariable Long id) {
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting != null) {
            return ResponseEntity.ok(jobPosting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3) Create a New Job Posting
    @PostMapping
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPosting jobPosting) {
        JobPosting savedJobPosting = jobPostingService.saveJobPosting(jobPosting);
        return ResponseEntity.ok(savedJobPosting);
    }

    // 4) Delete a Job Posting by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.noContent().build();
    }
    // 5) Update
    // 4. Update an Existing Job Posting
    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJobPosting(@PathVariable Long id, @RequestBody JobPosting updatedJobPosting) {
        JobPosting jobPosting = jobPostingService.updateJobPosting(id, updatedJobPosting);
        return jobPosting != null ? ResponseEntity.ok(jobPosting) : ResponseEntity.notFound().build();
    }

    // 5) (Optional) Filter by jobType - if you created the method in the service
    //    Example usage: GET /api/job-postings/filter?jobType=FullTime
    @GetMapping("/filter")
    public List<JobPosting> getPostingsByJobType(@RequestParam String jobType) {
        // This requires a findByJobType(String jobType) method in your repository.
        return jobPostingService.getPostingsByJobType(jobType);
    }
}