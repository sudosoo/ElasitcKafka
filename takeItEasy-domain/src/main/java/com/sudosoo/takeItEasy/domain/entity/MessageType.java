package com.sudosoo.takeItEasy.domain.entity;

public enum MessageType {
    MESSAGE,MENTION;

    public static MessageType getMessageType(String messageType) {
        if (messageType == null || messageType.isEmpty()) {
            return null;
        }

        try {
            return MessageType.valueOf(messageType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

