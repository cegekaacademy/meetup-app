package com.cegeka.academy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_challenge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserChallenge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "challenge_id")
    private Long challengeId;

    @NotNull
    @Column(name = "invitation_id")
    private Long invitationId;

    @NotNull
    @Size(max = 45)
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "points")
    private double points;

    @NotNull
    @Column(name = "start_time")
    private Date startTime;

    @NotNull
    @Column(name = "end_time")
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getInvitationId() { return invitationId; }

    public void setInvitationId(Long invitationId) { this.invitationId = invitationId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public double getPoints() { return points; }

    public void setPoints(double points) { this.points = points; }

    public Date getStartTime() { return startTime; }

    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "UserChallenge{" +
                "id=" + id +
                ", userId=" + userId +
                ", challengeId=" + challengeId +
                ", invitationId=" + invitationId +
                ", status='" + status + '\'' +
                ", points=" + points +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
