package com.exemple.security.security;



import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exemple.security.entity.User;
import com.exemple.security.exception.CustomException;
import com.exemple.security.playload.ChangePassword;
import com.exemple.security.playload.JwtAythentication;
import com.exemple.security.playload.RefreshTokenRequest;
import com.exemple.security.playload.ResourceNotFoundExceptionUsername;
import com.exemple.security.playload.SingInRequest;
import com.exemple.security.playload.SingUpRequest;
import com.exemple.security.repository.UserRepository;

@Service
public class AuthentificationServices {


	private  UserRepository userRepository;
	private  PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;




	public AuthentificationServices (UserRepository userRepository, PasswordEncoder passwordEncoder,JWTService jwtService, AuthenticationManager authenticationManager) {
		this.userRepository= userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public UserPrincipal singup(SingUpRequest singUpRequest)
	{
		User user = new User();
		user.setUsername(singUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
		user.setDateCreation(new Date());

		User user1 = userRepository.save(user);
		UserPrincipal userPrincipal = new UserPrincipal(user1);
		return userPrincipal;

	}

	public JwtAythentication singIn(SingInRequest singInRequest)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(singInRequest.getEmail(), singInRequest.getPassword()));

		User user = userRepository.findByUsernameStatut(singInRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		if(user.getFirst().equals("1"))
		{
			// throw new CustomException("Mot de passe réinitialisé !!");
			return null;
		}

		String jwt = jwtService.generateToken(user.getUsername());
		String refreshJwt = jwtService.generateRefreshToken(new HashMap<>(),user.getUsername());

		JwtAythentication jwtAythentication =new JwtAythentication();

		jwtAythentication.setIdUser(user.getId());
		jwtAythentication.setIdEmploye(user.getEmployes().getId());
		jwtAythentication.setEmploye(user.getEmployes().getNom());
		jwtAythentication.setEmailEmploye(user.getEmployes().getEmail());
		jwtAythentication.setIdAgence(user.getEmployes().getAffectations().getId());
		jwtAythentication.setAgence(user.getEmployes().getAffectations().getLibelle());
		jwtAythentication.setRoles(user.getRoles().stream().map(role -> role.getRole().getName()).collect(Collectors.toList()));
		jwtAythentication.setFirst(user.getFirst());
		jwtAythentication.setToken(jwt);

		jwtAythentication.setRefrechToken(refreshJwt);

		return jwtAythentication;
	}

	public void changePassword(ChangePassword changePassword) {
		User user = userRepository.findByUsernameStatut(changePassword.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		if(!passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword()))
		{
			 throw new CustomException("Votre mot de passe est incorrect !");
		}
		else
		{
			user.setDateModification(new Date());
			user.setFirst("0");
			user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));

			userRepository.save(user);
		}
	}


	public JwtAythentication refreshToken(RefreshTokenRequest refreshTokenRequest)
	{
		String token = refreshTokenRequest.getToken();
		String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
		User user = getUser(userEmail);

		if(user == null )
		{
			throw new IllegalArgumentException("Invalid user");
		}

		UserPrincipal userPrincipal = new UserPrincipal(user);

		if(jwtService.validateToken(token,userPrincipal))
		{
			String jwt = jwtService.generateToken(userPrincipal.getUsername());

			JwtAythentication jwtAythentication =new JwtAythentication();

			//jwtAythentication.setUser(user);
			jwtAythentication.setToken(jwt);
			jwtAythentication.setRefrechToken(refreshTokenRequest.getToken());

			return jwtAythentication;
		}
		return null;
	}

	public User getUser(String username)
	{
		User user = userRepository.findByUsernameStatut(username).orElseThrow(()-> new ResourceNotFoundExceptionUsername("user", "username", username));
		return user;
	}
}
