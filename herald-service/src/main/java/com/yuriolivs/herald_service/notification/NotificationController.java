package com.yuriolivs.herald_service.notification;

import com.yuriolivs.notification.shared.domain.schedule.dto.SchedulePayloadRequestDTO;
import com.yuriolivs.notification.shared.domain.schedule.dto.ScheduledPayloadResponseDTO;
import com.yuriolivs.herald_service.notification.domain.dto.NotificationRequestDTO;
import com.yuriolivs.herald_service.notification.domain.dto.NotificationResponseDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints for notification management")
public class NotificationController {
    private NotificationService service;

    @PostMapping
    @Operation(summary = "Send notification", description = "Receives and processes a notification request for the authenticated tenant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid or missing API Key")
    })
    public ResponseEntity<NotificationResponseDTO> handleNotificationRequest(
            @RequestBody @Valid NotificationRequestDTO dto,
            @RequestHeader("X-Tenant-Id") UUID tenantId
    ) {
        NotificationResponseDTO response = NotificationResponseDTO
                .from(service.handleNotificationRequest(dto, tenantId));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification", description = "Returns a notification by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification found"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResponseDTO> getNotification(
            @PathVariable UUID id
    ) {
        NotificationResponseDTO response = NotificationResponseDTO
                .from(service.findById(id));

        return ResponseEntity.ok(response);
    }

    // <---- Internal Endpoints ---->
    @PostMapping("/internal")
    @Hidden
    public ResponseEntity<NotificationResponseDTO> postNotification(
            @RequestBody @Valid NotificationRequestDTO dto,
            @RequestHeader("X-Tenant-Id") UUID tenantId
    ) {
        NotificationResponseDTO response = NotificationResponseDTO
                .from(service.save(dto, tenantId));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/internal/payload")
    @Hidden
    public ResponseEntity<ScheduledPayloadResponseDTO> getNotificationsPayload(
            @RequestBody @Valid SchedulePayloadRequestDTO dto
            ) {
        ScheduledPayloadResponseDTO payload = service.getNotificationsPayload(dto);
        return ResponseEntity.ok(payload);
    }
}
