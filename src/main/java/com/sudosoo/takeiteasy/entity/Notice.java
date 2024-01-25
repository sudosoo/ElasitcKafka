package com.sudosoo.takeiteasy.entity;

import com.sudosoo.takeiteasy.dto.notice.NoticeRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.aspectj.weaver.ast.Not;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;

    @NotNull
    private String content;

    @Column(nullable = false)
    private boolean isRead = false;

    @CreatedDate
    @Column(updatable = false)
    private final LocalDateTime sendTime = LocalDateTime.now();

    private Notice(Member member ,String content) {
        this.receiver = member;
        this.content = content;
    }

    public static Notice of(Member member, String content){
        return new Notice(member,content);
    }

}
