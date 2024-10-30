package com.exemple.security.configuration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTAuthenticationFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
			 .cors(cors -> cors.configurationSource(request -> {
	             CorsConfiguration config = new CorsConfiguration();
	             config.setAllowCredentials(true);
	             config.addAllowedOrigin("http://localhost:5173");
	             config.addAllowedHeader("*");
	             config.addAllowedMethod("*");
	             return config;
	         }))
			.csrf(customizer -> customizer.disable())
			.authorizeHttpRequests(request -> request
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers("/api/admin").hasAnyAuthority("ROLE_ADMIN")
					.requestMatchers("/api/user").hasAnyAuthority("ROLE_AD_USER")
					.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			//.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		//http.formLogin(Customizer.withDefaults());

		 return http.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService( ) {
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("yassin").password("y@123").roles("USER").build();
//		UserDetails user2 = User.withDefaultPasswordEncoder().username("zakaria").password("y@123").roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(user1,user2);
//	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}

