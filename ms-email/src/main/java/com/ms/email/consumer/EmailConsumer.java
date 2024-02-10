package com.ms.email.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.model.EmailModel;
import com.ms.email.services.impl.EmailServiceImpl;

@Component
public class EmailConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
	
	final EmailServiceImpl emailService;
	
	public EmailConsumer(EmailServiceImpl emailService) {
		this.emailService = emailService;
	}
	
	@RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
	public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
		
    	LOGGER.info("### New registration received of user: {}", emailRecordDto.emailTo());
    	
		EmailModel emailModel = new EmailModel();
		BeanUtils.copyProperties(emailRecordDto, emailModel);
		emailService.sendEmail(emailModel);
	}
	
}
