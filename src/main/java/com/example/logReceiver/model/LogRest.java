package com.example.logReceiver.model;

/**
 * POJO used to parse data from Restfull request
 */
public class LogRest {
    private Long timestamp;
    private Long userId;
    private String event;

    public LogRest() {
    }

    public LogRest(Long timestamp, Long userId, String event) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.event = event;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
