package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.documents.AddressDocument;
import com.example.demo.repository.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	public static enum AddressControllerAction {
		INSERT
	}

	private static final String ADDRESS_INSERTED = "Address has been inserted";

	@Autowired
	private AddressRepository addresRepository;

	@GetMapping("/insert")
	public Map<String, String> insertAddress() {
		AddressDocument address = new AddressDocument();
		address.setAddress("Uxmal 123");
		address.setNeighborhood("Narvarte");

		addresRepository.save(address);

		return response(AddressControllerAction.INSERT);
	}

	@GetMapping
	public List<AddressDocument> getAllAddresses() {

		return addresRepository.findAll();
	}

	private Map<String, String> response(AddressControllerAction insert) {

		Map<String, String> map = new HashMap<>();

		switch (insert) {
		case INSERT:
			map.put("message", ADDRESS_INSERTED);
		}

		return map;
	}

}
