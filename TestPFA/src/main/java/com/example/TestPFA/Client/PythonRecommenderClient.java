package com.example.TestPFA.Client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PythonRecommenderClient {

    private final RestTemplate restTemplate;

    public PythonRecommenderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sends the user skills to the Python microservice and retrieves recommendations.
     * @param skillsText The user’s combined skill text (e.g., "Java Spring Microservices").
     * @return A list of Recommendation objects, each containing uniq_id and score.
     */
    public List<Recommendation> getRecommendations(String skillsText) {
        // 1) Construct the request payload
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("skills", skillsText);

        // 2) Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3) Build the request entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 4) URL to the Python service (adjust if it's on a different host or port)
        String url = "http://localhost:5000/recommend";

        // 5) Make the POST request and parse the response
        // Expected response:
        // { "recommendations": [ {"uniq_id": "ABC123", "score": 0.9132}, ... ] }
        ResponseEntity<PythonResponse> responseEntity =
                restTemplate.postForEntity(url, requestEntity, PythonResponse.class);

        // 6) Check response status & body
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody().getRecommendations();
        } else {
            // handle error or return empty list
            return Collections.emptyList();
        }
    }

    // A small helper class to parse the Python service's JSON
    public static class PythonResponse {
        private List<Recommendation> recommendations;

        public List<Recommendation> getRecommendations() {
            return recommendations;
        }
        public void setRecommendations(List<Recommendation> recommendations) {
            this.recommendations = recommendations;
        }
    }

    // A class representing each item in the "recommendations" array
    public static class Recommendation {
        private String uniq_id;  // must match JSON key from Python
        private double score;

        public String getUniq_id() {
            return uniq_id;
        }
        public void setUniq_id(String uniq_id) {
            this.uniq_id = uniq_id;
        }

        public double getScore() {
            return score;
        }
        public void setScore(double score) {
            this.score = score;
        }
    }
}
