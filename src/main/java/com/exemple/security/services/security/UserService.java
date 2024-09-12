package com.exemple.security.services.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exemple.security.entity.Role;
import com.exemple.security.entity.User;
import com.exemple.security.entity.UserRole;
import com.exemple.security.playload.ResourceNotFoundException;
import com.exemple.security.repository.UserRepository;
import com.exemple.security.repository.UserRoleRepository;
import com.exemple.security.security.UserPrincipal;



@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with username"+ username));
		
		if(user == null)
		{
			System.out.println("=========> User not found");
			throw new UsernameNotFoundException("User Not found");
		}
		
		List<UserRole> userRoles = userRoleRepository.getDroits(user.getId());
		
		user.setRoles(userRoles);
		
		return new UserPrincipal(user);
	}

}