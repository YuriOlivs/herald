package com.yuriolivs.notification_service.mail.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.ToString;

public record MailDTO(
    @NotBlank(message = "Recipient (to) is obligatory.")
    @Email(message = "Recipient mail is invalid.")
    String to,

    @NotBlank(message = "Subject (subject) is obligatory.")
    @Size(max = 100, message = "Subject (subject) must have at max 100 characters.")
    String subject,

    @NotBlank(message = "Mail content (body) is obligatory.")
    String body
) {}