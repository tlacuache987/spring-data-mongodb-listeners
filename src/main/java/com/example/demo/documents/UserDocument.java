package com.example.demo.documents;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document
public class UserDocument implements Serializable {

	private static final long serialVersionUID = 8578179144661022634L;

	@Id
	private String id;

	@Indexed(direction = IndexDirection.ASCENDING)
	private String name;

	@Indexed(direction = IndexDirection.ASCENDING)
	private Integer age;

	private String sensibleData;

	private String sensibleDataEncrypted;

	@JsonIgnore
	private String secretKey;

	@JsonIgnore
	private String initializedVector;

	@Transient
	private Integer yearOfBirth;

}