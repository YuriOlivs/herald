package com.yuriolivs.notification_service.telegram.services;

import com.yuriolivs.notification.shared.exceptions.http.HttpNotFoundException;
import com.yuriolivs.notification_service.telegram.TelegramUser;
import com.yuriolivs.notification_service.telegram.TelegramUserRepository;
import com.yuriolivs.notification_service.telegram.domain.dto.TelegramDeleteWebhookDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TelegramUserService {
    private final TelegramUserRepository repo;

    public TelegramUser findByUserId(Long userId) {
        TelegramUser telegramUser = repo.findByTelegramUserId(userId);

        if(telegramUser == null) {
            throw new HttpNotFoundException("User was not found.");
        }

        return telegramUser;
    }

    public TelegramUser registerIfNotExists(
            Long telegramUserId,
            Long chatId
    ) {
        TelegramUser exists = repo.findByTelegramUserId(telegramUserId);

        if(exists == null) {
            TelegramUser telegramUser = new TelegramUser(telegramUserId, chatId, true);
            return repo.save(telegramUser);
        }

        return exists;
    }

    public void updateFirstMessage(Long userId) {
        TelegramUser telegramUser = findByUserId(userId);
        telegramUser.setFirstMessage(false);
        repo.save(telegramUser);
    }

    public void toggleNotifications(Long userId) {
        TelegramUser telegramUser = findByUserId(userId);
        telegramUser.setActive(!telegramUser.isActive());
    }

    public void deleteTelegramUser(TelegramDeleteWebhookDTO dto) {
        findByUserId(dto.id());
        repo.deleteByTelegramUserId(dto.id());
    }
}
