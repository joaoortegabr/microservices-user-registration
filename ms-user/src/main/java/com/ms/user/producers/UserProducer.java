package com.ms.user.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;

@Component
public class UserProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);
	
	final RabbitTemplate rabbitTemplate;
	
	public UserProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Value(value = "${spring.rabbitmq.template.default-receive-queue}")
	private String routingKey;
	
	public void publishMessageEmail(UserModel userModel) {
		
    	LOGGER.info("### Sending register confirmation to: {}", userModel.getEmail());
    	
		EmailDto emailDto = new EmailDto();
		emailDto.setUserId(userModel.getUserId());
		emailDto.setEmailTo(userModel.getEmail());
		emailDto.setSubject("Cadastro realizado com sucesso!");
		emailDto.setText(userModel.getName() + ", seja bem-vindo(a)! \n"
				+ "Agradecemos o seu cadastro.");

		rabbitTemplate.convertAndSend("", routingKey, emailDto);
	}
	
}
