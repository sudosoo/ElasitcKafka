package com.sudosoo.takeiteasy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notify extends Message{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String operator = "operator";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @NotNull
    private Member receiver;

    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    private final MessageType messageType = MessageType.NOTIFY;

    @CreatedDate
    @Column(updatable = false)
    private final LocalDateTime sendTime = LocalDateTime.now();

    @Builder
    private Notify(Member receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }
}
