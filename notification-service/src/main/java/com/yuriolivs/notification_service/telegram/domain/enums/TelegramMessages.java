package com.yuriolivs.notification_service.telegram.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TelegramMessages {
    WELCOME("You will now receive notifications from our system. To cancel, send /cancel."),
    START("Hello! How can we help you today?"),
    CANCELLED("You will no longer receive notifications. To reactivate them, send /activate"),
    REACTIVAED("Your notifications have been reactivated."),
    ALREADY_CANCELLED("Your notifications are already cancelled."),
    ALREADY_ACTIVATED("Your notifications are already activated."),
    UNKNOW_COMMAND("Sorry, I didn't understand. To see all possible commands, send /help.");

    private final String text;

    public String value() {
        return text;
    }
}