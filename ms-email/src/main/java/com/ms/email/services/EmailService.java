package com.ms.email.services;

import com.ms.email.model.EmailModel;

public interface EmailService {

	EmailModel sendEmail(EmailModel emailModel);
	
}
