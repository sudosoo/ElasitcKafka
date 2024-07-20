package com.sudosoo.takeItEasy.domain.entity;

public enum KafkaTopics {
    ORDER("order");

    private final String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public static KafkaTopics fromString(String topicName) {
        for (KafkaTopics topic : KafkaTopics.values()) {
            if (topic.getTopicName().equalsIgnoreCase(topicName)) {
                return topic;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + topicName + " found");
    }
}