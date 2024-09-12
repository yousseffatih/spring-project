package com.exemple.security.playload;

import java.util.Set;

import com.exemple.security.entity.Role;

import lombok.Data;

@Data
public class UserDTO {
	 private int id;
	    private String username;
	    private String password;
	    private String email;
	    private Set<Role> roles;
}
