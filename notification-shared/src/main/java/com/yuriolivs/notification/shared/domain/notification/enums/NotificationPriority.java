package com.yuriolivs.notification.shared.domain.notification.enums;

public enum NotificationPriority {
    URGENT("10"),
    SCHEDULED("10"),
    WARNING("7"),
    INFO("4"),
    MARKETING("1");

    private final String text;

    NotificationPriority(String text) {
        this.text = text;
    }

    public Integer value() {
        return Integer.parseInt(text);
    }

    public static NotificationPriority fromText(String text) {
        for (NotificationPriority priority : values()) {
            if (priority.text.equalsIgnoreCase(text)) {
                return priority;
            }
        }
        return null;
    }
}

