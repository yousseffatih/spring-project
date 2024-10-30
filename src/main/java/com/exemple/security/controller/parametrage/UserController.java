package com.exemple.security.controller.parametrage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.security.entity.User;
import com.exemple.security.playload.dto.ListApis;
import com.exemple.security.services.parametrage.User.InUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private InUserService userService;

	@GetMapping("/listUsers")
	public List<ListApis> getAllActivitesApis() {
		List<User> users  = userService.getUsers();
		List<ListApis> listApis = users.stream().map(e -> mapToApisList(e)).collect(Collectors.toList());
		return listApis;
	}

	private ListApis mapToApisList(User user)
	{
		ListApis listApis = new ListApis();
		listApis.setId(user.getId());
		listApis.setCode(user.getCode());
		listApis.setLibelle(user.getEmployes().getNom() + " " + user.getEmployes().getPrenom());
		return listApis;
	}

}
