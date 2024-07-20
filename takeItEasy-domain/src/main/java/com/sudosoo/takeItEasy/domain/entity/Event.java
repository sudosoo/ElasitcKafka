package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private KafkaTopics targetName;
    @Enumerated(EnumType.STRING)
    private EventOperation operation;
    @CreatedDate
    private LocalDateTime createdAt;
    private String body;

    protected Event() {
    }

    public Event(KafkaTopics topicName, EventOperation operation, String body) {
        this.targetName = topicName;
        this.operation = operation;
        this.body = body;
    }

    public Long getId() {
        return id;
    }
}
