package com.cegeka.academy.service.dto;

import java.util.Date;

public class ChallengeForUserChallengeDTO {

    private Long id;
    private UserForUserChallengeDTO user;
    private Date startDate;
    private Date endDate;
    private double points;

    public ChallengeForUserChallengeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserForUserChallengeDTO getUser() {
        return user;
    }

    public void setUser(UserForUserChallengeDTO user) {
        this.user = user;
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

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "ChallengeForUserChallengeDTO{" +
                "id=" + id +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", points=" + points +
                '}';
    }
}
