package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private EventOperation operation;
    @CreatedDate
    private LocalDateTime createdAt;
    private String body;

    protected Event() {
    }

    public Event(EventOperation operation, String body) {
        this.operation = operation;
        this.body = body;
    }

}
