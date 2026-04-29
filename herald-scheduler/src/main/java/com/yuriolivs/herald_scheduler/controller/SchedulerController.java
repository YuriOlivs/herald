package com.yuriolivs.herald_scheduler.controller;

import com.yuriolivs.herald_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.herald_scheduler.domain.schedule.dto.ScheduleResponseDTO;
import com.yuriolivs.herald_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.herald_scheduler.service.SchedulerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Scheduler", description = "Endpoints for notification scheduling management")
public class SchedulerController {
    private final SchedulerService service;

    @GetMapping("/status/{id}")
    @Operation(
            summary = "Check schedule status",
            description = "Returns the current status of a scheduled notification by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule found"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    public ResponseEntity<ScheduleResponseDTO> checkScheduleStatus(
            @PathVariable UUID id
    ) {
        ScheduledNotification response = service.findScheduledNotification(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @PostMapping
    @Operation(
            summary = "Schedule notification",
            description = "Schedules a notification to be sent at a specific date and time for the authenticated tenant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification scheduled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized — invalid or missing API Key")
    })
    public ResponseEntity<ScheduleResponseDTO> scheduleMessage(
            @RequestBody @Valid ScheduleRequestDTO dto,
            @RequestHeader("X-Tenant-Id") UUID tenantId
    ) {
        ScheduledNotification response = service.scheduleMessage(dto, tenantId);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Cancel schedule",
            description = "Cancels a previously scheduled notification by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Schedule cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Schedule not found")
    })
    public ResponseEntity<ScheduleResponseDTO> cancelSchedule(
            @PathVariable UUID id
    ) {
        ScheduledNotification response = service.cancelSchedule(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @GetMapping
    @Operation(
            summary = "List scheduled messages",
            description = "Returns all scheduled notifications"
    )
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    public ResponseEntity<ScheduleResponseDTO> returnAllScheduledMessages() {
        return ResponseEntity.ok().build();
    }
}
