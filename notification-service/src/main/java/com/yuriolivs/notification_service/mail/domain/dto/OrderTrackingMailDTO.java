package com.yuriolivs.notification_service.mail.domain.dto;

import java.util.List;

public record OrderTrackingMailDTO(
        String to,
        OrderTrackingDataEmailDTO data,
        List<ProductEmailDTO> products
) {}