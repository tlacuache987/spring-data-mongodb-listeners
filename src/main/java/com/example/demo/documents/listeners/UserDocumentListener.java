package com.example.demo.documents.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.demo.crypto.v1.service.impl.CipherUserService;
import com.example.demo.documents.UserDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserDocumentListener extends AbstractMongoEventListener<UserDocument> {

	@Autowired
	private CipherUserService chiperUserService;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<UserDocument> event) {
		log.info("onBeforeConvert executed");

		UserDocument user = event.getSource();

		user.setAge(user.getAge() + 1);

		chiperUserService.encryptSensitiveData(user);
	}

	@Override
	public void onAfterConvert(AfterConvertEvent<UserDocument> event) {
		log.info("onAfterConvert executed");

		UserDocument user = event.getSource();

		chiperUserService.decryptSensitiveData(user);
	}

}