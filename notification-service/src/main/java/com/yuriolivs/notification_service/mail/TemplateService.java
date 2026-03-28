package com.yuriolivs.notification_service.mail;

import com.yuriolivs.notification.shared.domain.email.enums.EmailTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class TemplateService {
    public String loadTemplate(EmailTemplate template) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + template.getFilename());
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public String renderTemplate(String html, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            html = html.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return html;
    }
}
