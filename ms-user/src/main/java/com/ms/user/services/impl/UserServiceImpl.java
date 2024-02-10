package com.ms.user.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import com.ms.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	final UserRepository userRepository;
	final UserProducer userProducer;
	
	public UserServiceImpl(UserRepository userRepository, UserProducer userProducer) {
		this.userRepository = userRepository;
		this.userProducer = userProducer;
	}
	
	@Transactional
	public UserModel save(UserModel userModel) {
		
    	LOGGER.info("### Registering new user: {}", userModel.getName());
    	
		userModel = userRepository.save(userModel);
		userProducer.publishMessageEmail(userModel);
		return userModel;
	}
	
}
