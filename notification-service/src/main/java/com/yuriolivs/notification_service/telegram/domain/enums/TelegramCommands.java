package com.yuriolivs.notification_service.telegram.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TelegramCommands {
    START("/start"),
    CANCEL("/cancel"),
    ACTIVATE("/activate"),
    HELP("/activate");

    private final String text;

    public String value() {
        return text;
    }

    public static TelegramCommands fromText(String text) {
        for (TelegramCommands cmd : values()) {
            if (cmd.text.equalsIgnoreCase(text)) {
                return cmd;
            }
        }
        return null;
    }
}
