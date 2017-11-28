package com.aspire.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.demo.domain.User;
import com.aspire.demo.domain.dao.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/add")
	public void add() {
		userRepository.save(new User(1L, "didi", 30));
		userRepository.save(new User(2L, "mama", 40));
		userRepository.save(new User(3L, "kaka", 50));
		userRepository.save(new User(4L, "hehe", 70));
	}
	
	@GetMapping("/deleteAll")
	public void deleteAll() {
		userRepository.deleteAll();
	}
	
	@GetMapping("/find")
	public User find(@RequestParam("id") String id) {
		return userRepository.findOne(Long.valueOf(id));
	}
	
	@GetMapping("/delete")
	public void delete(@RequestParam("id") String id) {
		userRepository.delete(Long.valueOf(id));
	}
	
	@GetMapping("/findAll")
	public List<User> findAll(){
		return userRepository.findAll();
	}
}
