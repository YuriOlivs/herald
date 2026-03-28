package com.yuriolivs.notification.shared.domain.email.enums;

public enum EmailTemplate {
    ORDER_TRACKING("order-tracking.html"),
    ORDER_TRACKING_ITEM("order-tracking-item.html"),
    NONE("");

    private final String filename;

    EmailTemplate(String s) {
        this.filename = s;
    }

    public String getFilename() {
        return this.filename;
    }
}