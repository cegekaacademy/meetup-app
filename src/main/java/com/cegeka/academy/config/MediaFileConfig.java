package com.cegeka.academy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix="media")
public class MediaFileConfig {


    private String rootDirectory = System.getProperty("user.home") + "\\";
    private String uploadChallengeDirectory;
    private String jpgExtension;

    public String getRootDirectory() {
        return rootDirectory;
    }

    public String getUploadChallengeDirectory() {
        return uploadChallengeDirectory;
    }

    public void setUploadChallengeDirectory(String challengeDirectory) {
        this.uploadChallengeDirectory = challengeDirectory;
    }

    public String getJPGExtension() {
        return jpgExtension;
    }

    public void setJPGExtension(String jpgExtension) {
        this.jpgExtension = jpgExtension;
    }
}
