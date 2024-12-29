package com.example.TestPFA.controller;

import com.example.TestPFA.entity.JobPosting;
import com.example.TestPFA.entity.User;
import com.example.TestPFA.repository.UserRepository;
import com.example.TestPFA.service.JobPostingService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")  // Base URL segment
public class JobPostingController {

    private final JobPostingService jobPostingService;
    private final UserRepository userRepository;

    public JobPostingController(JobPostingService jobPostingService,UserRepository userRepository) {
        this.jobPostingService = jobPostingService;
        this.userRepository = userRepository;
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
        // 1) Get username from SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2) Look up your actual User entity from DB
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in DB"));

        // 3) Set createdBy
        jobPosting.setCreatedBy(currentUser);

        JobPosting saved = jobPostingService.saveJobPosting(jobPosting);
        return ResponseEntity.ok(saved);
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
    @GetMapping("/paged")
    public Page<JobPosting> getAllJobPostingsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return jobPostingService.getAllJobPostingsPaged(page, size);
    }

    @GetMapping("/mine")
    public List<JobPosting> getMyJobPostings() {
        // 1) Get the current Authentication from the SecurityContext
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("No authentication found in the security context");
        }

        // 2) Extract the username (i.e., the principal's name)
        String username = authentication.getName();
        // Alternatively:
        // Object principal = authentication.getPrincipal();
        // If principal is a UserDetails, you can do:
        //   username = ((UserDetails) principal).getUsername();

        // 3) Use userRepository to find the actual User entity
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found in DB: " + username));

        // 4) Return the postings that belong to this user
        return jobPostingService.getPostingsByUser(currentUser);
    }


}