package com.cegeka.academy.service.dto;


import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengeDTO)) return false;
        ChallengeDTO that = (ChallengeDTO) o;
        return Double.compare(that.getPoints(), getPoints()) == 0 &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getCreator(), that.getCreator()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getChallengeCategory(), that.getChallengeCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreator(), getStartDate(), getEndDate(), getStatus(), getPoints(), getDescription(), getChallengeCategory());
    }
}
