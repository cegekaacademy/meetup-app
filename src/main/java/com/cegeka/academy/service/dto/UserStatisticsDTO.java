package com.cegeka.academy.service.dto;

import com.cegeka.academy.domain.AgeIntervals;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.enums.Gender;

import java.util.Objects;

public class UserStatisticsDTO {
    private long ageIntervalId;
    private long eventId;
    private Gender gender;

    public long getAgeIntervalId() {
        return ageIntervalId;
    }

    public void setAgeIntervalId(long ageIntervalId) {
        this.ageIntervalId = ageIntervalId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatisticsDTO that = (UserStatisticsDTO) o;
        return ageIntervalId == that.ageIntervalId &&
                eventId == that.eventId &&
                gender == that.gender;
    }

    @Override
    public int hashCode() {

        return Objects.hash(ageIntervalId, eventId, gender);
    }
}
