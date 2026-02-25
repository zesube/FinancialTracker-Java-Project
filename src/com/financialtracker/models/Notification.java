package com.financialtracker.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final String notificationId;
    private final String message;
    private final LocalDateTime createdAt;
    private boolean read;

    public Notification(String message) {
        this.notificationId = UUID.randomUUID().toString();
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }
}
