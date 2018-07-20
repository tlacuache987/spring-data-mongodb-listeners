package com.example.demo.crypto.v1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.crypto.v1.service.CipherService;
import com.example.demo.documents.UserDocument;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CipherUserService extends CipherService<UserDocument> {

	@Value("${encrypted.property}")
	private String encrypted;

	@Autowired
	public void hey(@Value("${encrypted.property}") String encrypted) {
		System.out.println("encrypted.property: " + encrypted);
	}

	@Override
	protected void performEncryption(UserDocument object) {
		log.info("trying to encrypt data...");

		object.setSensibleDataEncrypted(this.encrypt(object.getSensibleData()));

		object.setInitializedVector(this.getInitializedVector()); // needed to
																	// this poc
		object.setSecretKey(this.getSecretKey()); // needed to this poc

	}

	@Override
	protected void performDecryption(UserDocument object) {
		log.info("trying to decrypt data...");
		
		this.setInitializedVector(object.getInitializedVector()); // needed to
																	// this poc
		this.setSecretKey(object.getSecretKey()); // needed to this poc

		System.out.println(object);

		object.setSensibleDataEncrypted(this.decrypt(object.getSensibleDataEncrypted()));
	}

}
