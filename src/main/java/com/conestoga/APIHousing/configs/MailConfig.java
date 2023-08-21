package com.conestoga.APIHousing.configs;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
         

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("in-v3.mailjet.com"); // You can change this if you use a different email provider
        mailSender.setPort(587);
        mailSender.setUsername("c1d21f2db63170ad9cffcf57bd74ccd7");
        mailSender.setPassword("66864bfd273602dd0d7fbb8b5f3aa17e");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Set this to "true" for debugging (optional)

        return mailSender;
    }
}

