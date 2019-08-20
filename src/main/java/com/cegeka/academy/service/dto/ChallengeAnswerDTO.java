package com.cegeka.academy.service.dto;


import javax.validation.constraints.NotNull;

public class ChallengeAnswerDTO {

    private Long id;
    private String videoAt;
    private String imagePath;
    @NotNull(message = "Answer must not be null.")
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoAt() {
        return videoAt;
    }

    public void setVideoAt(String videoAt) {
        this.videoAt = videoAt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ChallengeAnswerDTO{" +
                "id=" + id +
                ", videoAt='" + videoAt + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
