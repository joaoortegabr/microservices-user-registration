package com.ms.user.resources;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	final UserServiceImpl userService;
	
	public UserResource(UserServiceImpl userService) {
		this.userService = userService;
	}
	
	@PostMapping(value = "/users")
	public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userRecordDto, userModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}
	
}
