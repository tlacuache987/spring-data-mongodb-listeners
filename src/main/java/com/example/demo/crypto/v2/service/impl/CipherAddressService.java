package com.example.demo.crypto.v2.service.impl;

import com.example.demo.crypto.v2.service.CipherService;
import com.example.demo.documents.AddressDocument;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class CipherAddressService extends CipherService<AddressDocument> {

	/*@Override
	protected void performEncryption(AddressDocument object) {
		log.info("trying to encrypt data...");
		object.setAddressEncrypted(this.encrypt(object.getAddress()));
	}

	@Override
	protected void performDecryption(AddressDocument object) {
		log.info("trying to decrypt data...");
		object.setAddressEncrypted(this.decrypt(object.getAddressEncrypted()));
	}*/

}
