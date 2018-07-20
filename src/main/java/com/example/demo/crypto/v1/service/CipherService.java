package com.example.demo.crypto.v1.service;

import java.io.Serializable;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.example.demo.crypto.SymmetricEncryptionUtils;

import lombok.SneakyThrows;

public abstract class CipherService<T extends Serializable> {

	ThreadLocal<SecretKey> threadLocalSecretKey = new ThreadLocal<>();
	ThreadLocal<byte[]> threadLocalInitializationVector = new ThreadLocal<>();

	@SneakyThrows
	public void encryptSensitiveData(T object) {
		SecretKey key = generateSecretKey();
		threadLocalSecretKey.set(key);

		byte[] initializationVector = initializeVector();
		threadLocalInitializationVector.set(initializationVector);

		performEncryption(object);
	}

	@SneakyThrows
	public void decryptSensitiveData(T object) {
		performDecryption(object);
	}

	protected abstract void performEncryption(T object);

	protected abstract void performDecryption(T object);

	@SneakyThrows
	protected String encrypt(String data) {
		return DatatypeConverter.printHexBinary(SymmetricEncryptionUtils.performAESEncryption(data,
				threadLocalSecretKey.get(), threadLocalInitializationVector.get()));
	}

	@SneakyThrows
	protected String decrypt(String data) {
		System.out.println("*initializedVector: " + DatatypeConverter.printHexBinary(threadLocalInitializationVector.get()));
		System.out.println("*secretKey: " + DatatypeConverter.printHexBinary(threadLocalSecretKey.get().getEncoded()));
		
		return SymmetricEncryptionUtils.performAESDecryption(DatatypeConverter.parseHexBinary(data), threadLocalSecretKey.get(),
				threadLocalInitializationVector.get());
	}

	private String encodeInitialicedVector(byte[] initializationVector) {
		return DatatypeConverter.printHexBinary(initializationVector);
	}

	private String encodeSecretKey(SecretKey key) {
		return DatatypeConverter.printHexBinary(key.getEncoded());
	}

	private byte[] initializeVector() {
		return SymmetricEncryptionUtils.createInitializationVector();
	}

	@SneakyThrows
	private SecretKey generateSecretKey() {
		return SymmetricEncryptionUtils.createAESKey();
	}

	protected String getInitializedVector() {
		return encodeInitialicedVector(threadLocalInitializationVector.get());
	}

	protected String getSecretKey() {
		return encodeSecretKey(threadLocalSecretKey.get());
	}

	protected void setInitializedVector(String initializedVector) {
		System.out.println("initializedVector: " + initializedVector);
		threadLocalInitializationVector.set(DatatypeConverter.parseHexBinary(initializedVector));
	}

	protected void setSecretKey(String secretKey) {
		System.out.println("secretKey: " + secretKey);

		byte[] secretKeyArray = DatatypeConverter.parseHexBinary(secretKey);

		threadLocalSecretKey.set(new SecretKeySpec(secretKeyArray, SymmetricEncryptionUtils.AES));
	}
}
