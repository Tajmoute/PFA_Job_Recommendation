package com.example.TestPFA.repository;

import com.example.TestPFA.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByJobType(String jobType);
    JobPosting findByUniqId(String uniqId);

}
