package com.example.TestPFA.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uniq_id")
    private String uniqId;

    @Column(name = "job_description", length = 2000)
    private String jobDescription;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_type")
    private String jobType;

    private String location;

    private String organization;

    private String sector;

    private String city;

    @Column(name = "state_country")
    private String stateCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")  // foreign key column
    private User createdBy;


    // 1) No-args constructor
    public JobPosting() {
    }

    // 2) All-args constructor
    public JobPosting(Long id, String uniqId, String jobDescription, String jobTitle, String jobType,
                      String location, String organization, String sector, String city, String stateCountry) {
        this.id = id;
        this.uniqId = uniqId;
        this.jobDescription = jobDescription;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.location = location;
        this.organization = organization;
        this.sector = sector;
        this.city = city;
        this.stateCountry = stateCountry;
    }

    // Getters and Setters for every field

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCountry() {
        return stateCountry;
    }

    public void setStateCountry(String stateCountry) {
        this.stateCountry = stateCountry;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;}
}
