package com.cegeka.academy.service.dto;

public class InvitationChallengeDTO {

    private Long id;
    private String status;
    private UserDTO user;

    public InvitationChallengeDTO() {
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "InvitationChallengeDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
