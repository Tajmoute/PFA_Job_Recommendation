package com.example.TestPFA.service;

import com.example.TestPFA.Client.PythonRecommenderClient;
import com.example.TestPFA.entity.JobPosting;
import com.example.TestPFA.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final PythonRecommenderClient pythonClient;
    private final JobPostingRepository jobPostingRepository; // to fetch job details by uniq_id

    public RecommendationService(PythonRecommenderClient pythonClient,
                                 JobPostingRepository jobPostingRepository) {
        this.pythonClient = pythonClient;
        this.jobPostingRepository = jobPostingRepository;
    }

    public List<JobPosting> getRecommendations(List<String> userSkills) {
        // 1) Combine skills into a single string
        String skillsText = String.join(" ", userSkills);
        // e.g. "Java Spring Microservices"

        // 2) Call Python microservice to get top recommended uniq_ids
        List<PythonRecommenderClient.Recommendation> recs = pythonClient.getRecommendations(skillsText);

        // 3) For each recommended uniq_id, fetch the JobPosting from your DB (if it exists)
        List<JobPosting> results = new ArrayList<>();
        for (PythonRecommenderClient.Recommendation rec : recs) {
            // e.g. rec.getUniq_id() -> "ABC123"
            // we assume you have something like findByUniqId(...) in your repository
            JobPosting jobPosting = jobPostingRepository.findByUniqId(rec.getUniq_id());
            if (jobPosting != null) {
                results.add(jobPosting);
            }
        }

        return results; // or you could wrap with the scores if you want
    }
}
