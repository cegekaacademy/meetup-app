package com.cegeka.academy.domain;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @OneToOne
    @JoinColumn(name = "invitation_id", referencedColumnName = "id")
    private Invitation invitation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "challenge_id", referencedColumnName = "id")
    private Challenge challenge;

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

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Invitation getInvitation() { return invitation; }

    public void setInvitation(Invitation invitation) { this.invitation = invitation; }

    public Challenge getChallenge() { return challenge; }

    public void setChallenge(Challenge challenge) { this.challenge = challenge; }

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
                ", user=" + user +
                ", invitation=" + invitation +
                ", status=" + status +
                ", points=" + points +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
