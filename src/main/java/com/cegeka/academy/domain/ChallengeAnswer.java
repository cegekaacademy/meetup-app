package com.cegeka.academy.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "challenge_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChallengeAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 150)
    @Column(name = "video_at", length = 150)
    private String videoAt;

    @Size(max = 150)
    @Column(name = "image_path", length = 150)
    private String imagePath;

    @Size(max = 150)
    @Column(name = "answer", length = 150)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChallengeAnswer)) return false;
        ChallengeAnswer that = (ChallengeAnswer) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getVideoAt(), that.getVideoAt()) &&
                Objects.equals(getImagePath(), that.getImagePath()) &&
                Objects.equals(getAnswer(), that.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideoAt(), getImagePath(), getAnswer());
    }

    @Override
    public String toString() {
        return "ChallengeAnswer{" +
                "id=" + id +
                ", videoAt='" + videoAt + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
