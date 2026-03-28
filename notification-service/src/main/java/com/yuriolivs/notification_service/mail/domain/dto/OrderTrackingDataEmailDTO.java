package com.yuriolivs.notification_service.mail.domain.dto;

public record OrderTrackingDataEmailDTO(
        String customerName,
        String orderId,
        String orderDate,
        String shippmentMethod,
        String trackingCode,
        String trackingUrl,
        String status,
        String statusDescription
) {}