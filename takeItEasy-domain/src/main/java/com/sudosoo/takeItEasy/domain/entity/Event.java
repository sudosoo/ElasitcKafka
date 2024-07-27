package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
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
    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PENDING;

    protected Event() {
    }

    public Event(KafkaTopics topicName, EventOperation operation, String body) {
        this.targetName = topicName;
        this.operation = operation;
        this.body = body;
    }

    public void fail() {
        this.status = EventStatus.FAILED;
    }


}
