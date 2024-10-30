package com.exemple.security.services.parametrage.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.security.entity.User;
import com.exemple.security.repository.UserRepository;

@Service
public class UserServiceImp implements InUserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getUsers() {
		List<User> list = userRepository.findUsersByStatut();
		return list;

	}
}
