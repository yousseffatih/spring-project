package com.exemple.security.playload.dto;

import java.util.Set;

import com.exemple.security.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private int id;

    private String username;

    private String password;

    private String email;

    private Set<Role> roles;
}
