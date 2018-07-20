package com.example.demo.documents;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class AddressDocument implements Serializable {

	private static final long serialVersionUID = -8778161391228394467L;

	@Id
	private String id;

	private String address;

	@Transient
	private String addressEncrypted;

	private String neighborhood;

}