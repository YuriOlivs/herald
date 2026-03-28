package com.yuriolivs.notification_service.telegram.services;

import com.yuriolivs.notification_service.telegram.domain.dto.TelegramWebhookDTO;
import com.yuriolivs.notification_service.telegram.domain.dto.message.MessageChatDTO;
import com.yuriolivs.notification_service.telegram.domain.dto.message.MessageFromDTO;
import com.yuriolivs.notification_service.telegram.TelegramUser;
import com.yuriolivs.notification_service.telegram.domain.enums.TelegramCommands;
import com.yuriolivs.notification_service.telegram.domain.enums.TelegramMessages;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelegramWebhookService {
    private final TelegramMessageService messageService;
    private final TelegramUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TelegramWebhookService.class);

    public void receiveUpdate(TelegramWebhookDTO update) {
        if (update.message() == null) return;

        MessageFromDTO from = update.message().from();
        MessageChatDTO chat = update.message().chat();
        String text = update.message().text();

        logger.info("""
                
                ================= TELEGRAM WEBHOOK =================
                User ID : {}
                Chat ID : {}
                Message : {}
                ====================================================
                
                """, from.id(), chat.id(), text);

        TelegramUser telegramUser = userService.registerIfNotExists(from.id(), chat.id());

        processCommand(telegramUser.getChatId(), text, from.id());
    }

    private void processCommand(Long chatId, String text, Long userId) {
        if (text == null || text.isBlank()) return;

        TelegramUser telegramUser = userService.findByUserId(userId);

        TelegramCommands command = TelegramCommands.fromText(text);
        if (command == null) return;

        switch(command) {
            case START -> {
                if (telegramUser.isFirstMessage()) {
                    userService.updateFirstMessage(userId);
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.WELCOME);
                } else {
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.START);
                }
            }
            case ACTIVATE -> {
                if (!telegramUser.isActive()) {
                    userService.toggleNotifications(userId);
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.REACTIVAED);
                } else {
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.ALREADY_ACTIVATED);
                }
            }
            case CANCEL -> {
                if (telegramUser.isActive()) {
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.CANCELLED);
                    userService.toggleNotifications(userId);
                } else {
                    messageService.sendStandardizedMessages(chatId, TelegramMessages.ALREADY_CANCELLED);
                }
            }
            default -> messageService.sendStandardizedMessages(chatId, TelegramMessages.UNKNOW_COMMAND);
        }
    }
}
