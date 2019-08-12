package com.cegeka.academy.service.dto;

import com.cegeka.academy.domain.ChallengeCategory;
import com.cegeka.academy.domain.User;

import java.util.Date;

public class ChallengeDTO {

    private Long id;

    private User creator;

    private Date startDate;

    private Date endDate;

    private String status;

    private double points;

    private String description;

    private ChallengeCategory challengeCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
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

    public ChallengeCategory getChallengeCategory() {
        return challengeCategory;
    }

    public void setChallengeCategory(ChallengeCategory challengeCategory) {
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
