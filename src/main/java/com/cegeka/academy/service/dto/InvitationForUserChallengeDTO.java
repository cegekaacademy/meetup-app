package com.cegeka.academy.service.dto;

public class InvitationForUserChallengeDTO {

    private Long id;
    private String status;
    private UserForUserChallengeDTO user;

    public InvitationForUserChallengeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserForUserChallengeDTO getUser() {
        return user;
    }

    public void setUser(UserForUserChallengeDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvitationForUserChallengeDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
