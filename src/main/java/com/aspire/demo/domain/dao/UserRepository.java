package com.aspire.demo.domain.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.aspire.demo.domain.User;

@Component
public interface UserRepository extends MongoRepository<User, Long> {
	
	User findUserByUsername(String name);
}
