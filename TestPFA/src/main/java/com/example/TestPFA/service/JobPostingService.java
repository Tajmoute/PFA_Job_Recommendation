package com.example.TestPFA.service;

import com.example.TestPFA.entity.JobPosting;
import com.example.TestPFA.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    // Constructor injection of the repository
    public JobPostingService(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }

    // Example: Retrieve all job postings
    public List<JobPosting> getAllJobPostings() {
        return jobPostingRepository.findAll();
    }

    // Example: Save a new job posting
    public JobPosting saveJobPosting(JobPosting jobPosting) {
        return jobPostingRepository.save(jobPosting);
    }

    // Example: Retrieve a single job posting by ID
    public JobPosting getJobPostingById(Long id) {
        return jobPostingRepository.findById(id)
                .orElse(null); // or throw an exception if not found
    }

    // Example: Delete a job posting by ID
    public void deleteJobPosting(Long id) {
        jobPostingRepository.deleteById(id);
    }

    // Update an existing job posting
    public JobPosting updateJobPosting(Long id, JobPosting updatedJobPosting) {
        return jobPostingRepository.findById(id)
                .map(existing -> {
                    existing.setJobTitle(updatedJobPosting.getJobTitle());
                    existing.setJobDescription(updatedJobPosting.getJobDescription());
                    existing.setJobType(updatedJobPosting.getJobType());
                    existing.setLocation(updatedJobPosting.getLocation());
                    existing.setOrganization(updatedJobPosting.getOrganization());
                    existing.setSector(updatedJobPosting.getSector());
                    existing.setCity(updatedJobPosting.getCity());
                    existing.setStateCountry(updatedJobPosting.getStateCountry());
                    return jobPostingRepository.save(existing);
                })
                .orElse(null);
    }
    public List<JobPosting> getPostingsByJobType(String jobType) {
        // Assuming you create a custom query method in the repository:
        // List<JobPosting> findByJobType(String jobType);

        return jobPostingRepository.findByJobType(jobType);
    }
    /*
     * In the future, we’ll add methods for "recommendations"
     * (e.g., filtering logic based on job type, skills, etc.)
     * For now, let's keep the basic CRUD.
     */
}