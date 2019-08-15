package com.cegeka.academy.service.dto;


import java.util.Date;

public class ChallengeDTO {

    private Long id;

    private UserDTO creator;

    private Date startDate;

    private Date endDate;

    private String status;

    private double points;

    private String description;

    private ChallengeCategoryDTO challengeCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChallengeCategoryDTO getChallengeCategory() {
        return challengeCategory;
    }

    public void setChallengeCategory(ChallengeCategoryDTO challengeCategory) {
        this.challengeCategory = challengeCategory;
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "id=" + id +
                ", creator=" + creator +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", points=" + points +
                ", description='" + description + '\'' +
                ", challengeCategory=" + challengeCategory +
                '}';
    }
}
