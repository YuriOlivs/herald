package com.yuriolivs.notification_service.telegram;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    TelegramUser findByTelegramUserId(Long telegramUserId);
    void deleteByTelegramUserId(Long telegramUserId);
}
