package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
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

    private Notice(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
    }

    public Notice() {
    }

    public static Notice of(Long receiverId, String content) {
        return new Notice(receiverId, content);
    }

    public Long getId() {
        return this.id;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }

    public @NotNull String getContent() {
        return this.content;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getSendTime() {
        return this.sendTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Notice)) return false;
        final Notice other = (Notice) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$receiverId = this.getReceiverId();
        final Object other$receiverId = other.getReceiverId();
        if (this$receiverId == null ? other$receiverId != null : !this$receiverId.equals(other$receiverId))
            return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        if (this.isRead() != other.isRead()) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$sendTime = this.getSendTime();
        final Object other$sendTime = other.getSendTime();
        if (this$sendTime == null ? other$sendTime != null : !this$sendTime.equals(other$sendTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Notice;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $receiverId = this.getReceiverId();
        result = result * PRIME + ($receiverId == null ? 43 : $receiverId.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        result = result * PRIME + (this.isRead() ? 79 : 97);
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $sendTime = this.getSendTime();
        result = result * PRIME + ($sendTime == null ? 43 : $sendTime.hashCode());
        return result;
    }
}
