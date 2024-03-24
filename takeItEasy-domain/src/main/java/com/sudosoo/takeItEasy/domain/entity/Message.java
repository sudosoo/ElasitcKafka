package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content = "";

    @Enumerated(EnumType.STRING)
    private MessageType messageType = MessageType.NORMAL;

    @CreatedDate
    @Column(updatable = false)
    private final LocalDateTime sendTime = LocalDateTime.now();

    public Message( Long senderId, Long receiverId, String content, String messageType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageType = MessageType.getMessageType(messageType);
    }

    public Message() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }

    public String getContent() {
        return this.content;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public LocalDateTime getSendTime() {
        return this.sendTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$senderId = this.getSenderId();
        final Object other$senderId = other.getSenderId();
        if (this$senderId == null ? other$senderId != null : !this$senderId.equals(other$senderId)) return false;
        final Object this$receiverId = this.getReceiverId();
        final Object other$receiverId = other.getReceiverId();
        if (this$receiverId == null ? other$receiverId != null : !this$receiverId.equals(other$receiverId))
            return false;
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
        final Object this$messageType = this.getMessageType();
        final Object other$messageType = other.getMessageType();
        if (this$messageType == null ? other$messageType != null : !this$messageType.equals(other$messageType))
            return false;
        final Object this$sendTime = this.getSendTime();
        final Object other$sendTime = other.getSendTime();
        if (this$sendTime == null ? other$sendTime != null : !this$sendTime.equals(other$sendTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $senderId = this.getSenderId();
        result = result * PRIME + ($senderId == null ? 43 : $senderId.hashCode());
        final Object $receiverId = this.getReceiverId();
        result = result * PRIME + ($receiverId == null ? 43 : $receiverId.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        final Object $messageType = this.getMessageType();
        result = result * PRIME + ($messageType == null ? 43 : $messageType.hashCode());
        final Object $sendTime = this.getSendTime();
        result = result * PRIME + ($sendTime == null ? 43 : $sendTime.hashCode());
        return result;
    }
}
