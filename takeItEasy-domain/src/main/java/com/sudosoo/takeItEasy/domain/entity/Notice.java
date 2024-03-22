package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId;

    @NotNull
    private String content;

    private boolean isRead = false;

    private boolean isDeleted = false;

    @CreatedDate
    @Column(updatable = false)
    private final LocalDateTime sendTime = LocalDateTime.now();

    private Notice(Long receiverId ,String content) {
        this.receiverId = receiverId;
        this.content = content;
    }

    public static Notice of(Long receiverId, String content){
        return new Notice(receiverId,content);
    }

}
