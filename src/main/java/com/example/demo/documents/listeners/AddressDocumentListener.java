package com.example.demo.documents.listeners;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.example.demo.crypto.v2.service.CipherService;
import com.example.demo.documents.AddressDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AddressDocumentListener extends AbstractMongoEventListener<AddressDocument> {

	@Autowired
	private CipherService<AddressDocument> chiperAddressService;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<AddressDocument> event) {
		log.info("onBeforeConvert executed");

		AddressDocument address = event.getSource();

		chiperAddressService.encryptSensitiveData(address, "address", "neighborhood");
	}

	@Override
	public void onAfterConvert(AfterConvertEvent<AddressDocument> event) {
		log.info("onAfterConvert executed");

		AddressDocument address = event.getSource();

		chiperAddressService.decryptSensitiveData(address, "address", "neighborhood");
	}

}