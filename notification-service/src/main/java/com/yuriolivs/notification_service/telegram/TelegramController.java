package com.yuriolivs.notification_service.telegram;

import com.yuriolivs.notification_service.telegram.domain.dto.TelegramDeleteWebhookDTO;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramWebhookDTO;
import com.yuriolivs.notification_service.telegram.services.TelegramUserService;
import com.yuriolivs.notification_service.telegram.services.TelegramWebhookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webhooks")
@AllArgsConstructor
public class TelegramController {
    private final TelegramWebhookService webhookService;
    private final TelegramUserService userService;

    @PostMapping
    private ResponseEntity<Void> receiveUpdate(
            @RequestBody @Valid TelegramWebhookDTO dto
    ) throws BadRequestException {
        webhookService.receiveUpdate(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    private ResponseEntity<Void> deleteWebhook(
            @RequestBody TelegramDeleteWebhookDTO dto
    ) throws BadRequestException {
        userService.deleteTelegramUser(dto);
        return ResponseEntity.ok().build();
    }
}
