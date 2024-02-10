package com.ms.email.services.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.email.enums.StatusEmail;
import com.ms.email.model.EmailModel;
import com.ms.email.repositories.EmailRepository;
import com.ms.email.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	final EmailRepository emailRepository;
    final JavaMailSender emailSender;

    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value="${spring.mail.username}")
    private String emailFrom;

	@Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        try{
        	LOGGER.info("### Sending confirmation email to {}", emailModel.getEmailTo());
        	
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailModel.setStatusEmail(StatusEmail.SENT);
            
            emailSender.send(message);

        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
            e.printStackTrace();
        }
		return emailRepository.save(emailModel);
    }
    
}
