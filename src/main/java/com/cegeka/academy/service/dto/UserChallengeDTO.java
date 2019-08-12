package com.cegeka.academy.service.dto;

import java.util.Date;

public class UserChallengeDTO {

    private Long id;
    private UserForUserChallengeDTO user;
    private InvitationForUserChallengeDTO invitation;
    private ChallengeForUserChallengeDTO challenge;
    private String status;
    private double points;
    private Date startTime;
    private Date endTime;

    public UserChallengeDTO() {
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

    public InvitationForUserChallengeDTO getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationForUserChallengeDTO invitation) {
        this.invitation = invitation;
    }

    public ChallengeForUserChallengeDTO getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeForUserChallengeDTO challenge) {
        this.challenge = challenge;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UserChallengeDTO{" +
                "id=" + id +
                ", user=" + user +
                ", invitation=" + invitation +
                ", challenge=" + challenge +
                ", status='" + status + '\'' +
                ", points=" + points +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
