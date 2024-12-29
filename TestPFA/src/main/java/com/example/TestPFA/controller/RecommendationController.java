package com.example.TestPFA.controller;

import com.example.TestPFA.entity.JobPosting;
import com.example.TestPFA.service.RecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<JobPosting> recommend(@RequestBody SkillsRequest request) {
        // e.g. request.skills = ["Java", "Spring", "Microservices"]
        return recommendationService.getRecommendations(request.getSkills());
    }

    // Helper class to parse incoming JSON
    public static class SkillsRequest {
        private List<String> skills;

        public List<String> getSkills() {
            return skills;
        }
        public void setSkills(List<String> skills) {
            this.skills = skills;
        }
    }
}
