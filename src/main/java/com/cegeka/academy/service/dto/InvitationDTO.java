package com.cegeka.academy.service.dto;

public class InvitationDTO {

    private String description;

    private String status;

    private String userName;

    private Long userId;

    private String eventName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "InvitationDTO{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", userName='" + userName + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
