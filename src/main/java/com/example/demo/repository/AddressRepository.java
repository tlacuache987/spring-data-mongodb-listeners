package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.documents.AddressDocument;

public interface AddressRepository extends MongoRepository<AddressDocument, String> {
}