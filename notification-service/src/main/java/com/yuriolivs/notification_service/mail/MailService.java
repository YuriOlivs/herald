package com.yuriolivs.notification_service.mail;

import com.yuriolivs.notification_service.mail.domain.dto.MailDTO;
import com.yuriolivs.notification_service.config.properties.MailFromProperties;
import com.yuriolivs.notification_service.mail.domain.dto.OrderTrackingMailDTO;
import com.yuriolivs.notification_service.mail.domain.dto.ProductEmailDTO;
import com.yuriolivs.notification.shared.domain.email.enums.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final MailFromProperties from;

    public void sendEmail(MailDTO dto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress(
                from.getAddress(),
                from.getName()
        ));

        helper.setTo(dto.to());
        helper.setSubject(dto.subject());
        helper.setText(dto.body(), true);

        mailSender.send(message);
    }

    public void sendOrderTrackingMail(OrderTrackingMailDTO dto) throws IOException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String html = templateService.loadTemplate(EmailTemplate.ORDER_TRACKING);
        String productsHtml = this.convertProductsToHtml(dto.products());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = mapper.convertValue(dto.data(), Map.class);

        data.put("orderProducts", productsHtml);

        String renderedHtml = templateService.renderTemplate(html, data);

        helper.setFrom(new InternetAddress(
                from.getAddress(),
                from.getName()
        ));

        helper.setTo(dto.to());
        helper.setSubject("Order Tracking");
        helper.setText(renderedHtml, true);

        mailSender.send(message);
    }

    private String convertProductsToHtml(List<ProductEmailDTO> products) throws IOException {
        String html = templateService.loadTemplate(EmailTemplate.ORDER_TRACKING_ITEM);
        StringBuilder resultHtml = new StringBuilder();

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> infoMapList = new ArrayList<>();

        for (ProductEmailDTO product : products) {
            Map<String, String> map = mapper.convertValue(product, Map.class);
            infoMapList.add(map);
        }

        for (Map<String, String> item : infoMapList) {
            String productHtml = html;

            for (Map.Entry<String, String> entry : item.entrySet()) {
                productHtml = productHtml.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            resultHtml.append(productHtml).append("\n");
        }

        return resultHtml.toString();
    }

}
