package com.yuriolivs.herald_scheduler.controller;

import com.yuriolivs.herald_scheduler.domain.schedule.dto.ScheduleRequestDTO;
import com.yuriolivs.herald_scheduler.domain.schedule.dto.ScheduleResponseDTO;
import com.yuriolivs.herald_scheduler.domain.schedule.entities.ScheduledNotification;
import com.yuriolivs.herald_scheduler.service.SchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class SchedulerController {
    @Autowired
    private final SchedulerService service;

    @GetMapping("/status/{id}")
    public ResponseEntity<ScheduleResponseDTO> checkScheduleStatus(
            @PathVariable UUID id
    ) {
        ScheduledNotification response = service.findScheduledNotification(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> scheduleMessage(
            @RequestBody @Valid ScheduleRequestDTO dto,
            @RequestHeader("X-Tenant-Id") UUID tenantId
    ) {
        ScheduledNotification response = service.scheduleMessage(dto, tenantId);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> cancelSchedule(
            @PathVariable UUID id
    ) {
        ScheduledNotification response = service.cancelSchedule(id);
        return ResponseEntity.ok(ScheduleResponseDTO.from(response));
    }

    @GetMapping
    public ResponseEntity<ScheduleResponseDTO> returnAllScheduledMessages() {
        return ResponseEntity.ok().build();
    }
}
