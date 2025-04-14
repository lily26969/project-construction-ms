// EmailService.java
package com.example.Pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.Pidev.models.Order;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, Order order) {
        String subject = "Order Confirmation - Pidev Shop";
        String body = "✅ Your order has been confirmed!\n\n"
                + "Order ID: " + order.getOrderId() + "\n"
                + "Status: " + order.getStatus() + "\n"
                + "Total: $" + order.getTotalPrice() + "\n\n"
                + "Thank you for shopping with us!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
