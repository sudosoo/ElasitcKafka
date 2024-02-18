package com.sudosoo.takeiteasy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long receiverId;

    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MessageType messageType;

    @CreatedDate
    @Column(updatable = false)
    private final LocalDateTime sendTime = LocalDateTime.now();

    @Builder
    private Message(Long senderId, Long receiverId, String content, MessageType messageType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageType = messageType;
    }
}
