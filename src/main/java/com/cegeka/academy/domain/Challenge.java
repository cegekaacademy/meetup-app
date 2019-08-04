package com.cegeka.academy.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "challenge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany
    Set<UserChallenge> userChallengeSet;

    @Column(name = "start_date")
    private Date startDate;

    @Column (name = "end_date")
    private Date endDate;

    @Size(min = 1, max = 50)
    private String status;

    private double points;

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", creator=" + creator +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", points=" + points +
                '}';
    }
}
