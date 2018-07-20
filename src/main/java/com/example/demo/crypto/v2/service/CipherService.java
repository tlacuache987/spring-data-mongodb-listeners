package com.example.demo.crypto.v2.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.crypto.SymmetricEncryptionUtils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CipherService<T extends Serializable> {

	@Value("${mongodb.cipher.secretKey}")
	private String secretKeyHexString;

	@Value("${mongodb.cipher.initVector}")
	private String initVectorHexString;

	private SecretKey key;

	private byte[] initializationVector;

	@PostConstruct
	public void initCipher() {
		this.key = generateSecretKey();
		this.initializationVector = initializeVector();
	}

	@SneakyThrows
	public void encryptSensitiveData(T object, String... properties) {
		if (object != null && properties != null) {
			PropertyAccessor accessor = PropertyAccessorFactory.forBeanPropertyAccess(object);

			for (String property : properties) {
				accessor.setPropertyValue(property, accessor.getPropertyValue(property) != null
						? encrypt(accessor.getPropertyValue(property).toString()) : null);
			}
		}
	}

	@SneakyThrows
	public void decryptSensitiveData(T object, String... properties) {
		if (object != null && properties != null) {
			PropertyAccessor accessor = PropertyAccessorFactory.forBeanPropertyAccess(object);

			for (String property : properties) {
				accessor.setPropertyValue(property, accessor.getPropertyValue(property) != null
						? decrypt(accessor.getPropertyValue(property).toString()) : null);
			}
		}
	}

	@SneakyThrows
	protected String encrypt(String data) {
		return DatatypeConverter
				.printHexBinary(SymmetricEncryptionUtils.performAESEncryption(data, key, initializationVector));
	}

	@SneakyThrows
	protected String decrypt(String data) {
		System.out.println("*initializedVector: " + DatatypeConverter.printHexBinary(initializationVector));
		System.out.println("*secretKey: " + DatatypeConverter.printHexBinary(key.getEncoded()));

		return SymmetricEncryptionUtils.performAESDecryption(DatatypeConverter.parseHexBinary(data), key,
				initializationVector);
	}

	private byte[] initializeVector() {
		return DatatypeConverter.parseHexBinary(this.initVectorHexString);
	}

	@SneakyThrows
	private SecretKey generateSecretKey() {
		byte[] secretKeyArray = DatatypeConverter.parseHexBinary(this.secretKeyHexString);

		return new SecretKeySpec(secretKeyArray, SymmetricEncryptionUtils.AES);
	}
}
