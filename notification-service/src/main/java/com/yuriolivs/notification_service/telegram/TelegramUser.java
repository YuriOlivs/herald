package com.yuriolivs.notification_service.telegram;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private Long telegramUserId;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Setter
    @Column
    private boolean active;

    @Setter
    @Column
    private boolean firstMessage = true;

    public TelegramUser(Long telegramUserId, Long chatId, boolean active) {
        this.telegramUserId = telegramUserId;
        this.chatId = chatId;
        this.active = active;
    }

}
