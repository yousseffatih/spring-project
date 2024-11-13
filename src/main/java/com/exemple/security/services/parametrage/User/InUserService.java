package com.exemple.security.services.parametrage.User;

import java.util.List;

import com.exemple.security.entity.User;
import com.exemple.security.playload.dto.PageableResponseDTO;
import com.exemple.security.playload.dto.UserDTO;

public interface InUserService {

	public List<User> getUsers();

	public UserDTO getUsers(Long id);

	public PageableResponseDTO getAllUsersPagebal(int pageNo, int pageSize);

	UserDTO addUsers(UserDTO userDTO);

	UserDTO deleteUsersStatut(Long id);

	//UserDTO updateUsers(Long id, UserDTO userDTO);

}
