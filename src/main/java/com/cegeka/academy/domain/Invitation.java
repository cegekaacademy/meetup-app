package com.cegeka.academy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    @Column(name = "description", length = 45)
    private String description;

    @Size(max = 45)
    @Column(name = "status", length = 45)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_invitation_user",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event",referencedColumnName = "id")
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public User getInvitedUser() {
        return user;
    }
    public void setInvitedUser(User invitedUser) {
        this.user = invitedUser;
    }

    public Event getInvitationEvent() {
        return event;
    }

    public void setInvitationEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return id.equals(that.id) &&
                description.equals(that.description) &&
                status.equals(that.status) &&
                user.equals(that.user) &&
                event.equals(that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status, user, event);
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();

        result.append("Invitation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status);

        if(user!=null){

            result.append(", idInvitedUser=" + user.getId());
        }

        if(event!=null){

            result.append(", idEvent=" + event.getId());
        }

        return result.toString();
    }
}
