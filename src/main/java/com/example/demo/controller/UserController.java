package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.documents.UserDocument;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	public static enum UserControllerAction {
		INSERT
	}

	private static final String USER_INSERTED = "User has been inserted";

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/insert")
	public Map<String, String> insertUser() {
		UserDocument user = new UserDocument();
		user.setName("Ivan");
		user.setAge(31);
		user.setSensibleData("my sensible data");

		userRepository.save(user);

		return response(UserControllerAction.INSERT);
	}

	@GetMapping
	public List<UserDocument> getAllUser() {

		return userRepository.findAll();
	}

	private Map<String, String> response(UserControllerAction insert) {

		Map<String, String> map = new HashMap<>();

		switch (insert) {
		case INSERT:
			map.put("message", USER_INSERTED);
		}

		return map;
	}

}
