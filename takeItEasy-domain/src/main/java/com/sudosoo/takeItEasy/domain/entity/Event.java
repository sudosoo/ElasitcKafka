package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String eventName;
    private EventOperation operation;
    private LocalDateTime eventDeadline;
    private String body;

    private Event(String eventName, LocalDateTime eventDeadline) {
        this.eventName = eventName;
        this.eventDeadline = eventDeadline;
    }

    public Event(Long id, String eventName, EventOperation operation, LocalDateTime eventDeadline) {
        this.id = id;
        this.eventName = eventName;
        this.operation = operation;
        this.eventDeadline = eventDeadline;
    }

    protected Event() {
    }

    public static Event of(String eventName, String eventDeadline) {
        return new Event(eventName, LocalDateTime.parse(eventDeadline));
    }

    public boolean isDeadlineExpired() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return eventDeadline.isBefore(currentDateTime);
    }

    public Long getId() {
        return this.id;
    }

    public String getEventName() {
        return this.eventName;
    }

    public LocalDateTime getEventDeadline() {
        return this.eventDeadline;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Event)) return false;
        final Event other = (Event) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$eventName = this.getEventName();
        final Object other$eventName = other.getEventName();
        if (this$eventName == null ? other$eventName != null : !this$eventName.equals(other$eventName)) return false;
        final Object this$eventDeadline = this.getEventDeadline();
        final Object other$eventDeadline = other.getEventDeadline();
        if (this$eventDeadline == null ? other$eventDeadline != null : !this$eventDeadline.equals(other$eventDeadline))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Event;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $eventName = this.getEventName();
        result = result * PRIME + ($eventName == null ? 43 : $eventName.hashCode());
        final Object $eventDeadline = this.getEventDeadline();
        result = result * PRIME + ($eventDeadline == null ? 43 : $eventDeadline.hashCode());
        return result;
    }

}
