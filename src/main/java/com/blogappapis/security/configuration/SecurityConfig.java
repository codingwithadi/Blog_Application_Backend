package com.blogappapis.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration

public class SecurityConfig {
	

	
	//Encode password using PasswordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//User Details -> Normal User and Admin who can access the blog functionality
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user = User
				.withUsername("user@as.blog.in")
				.password(passwordEncoder().encode("user@123"))
				.roles("USER")
				.build();
		
		UserDetails admin = User
				.withUsername("admin@as.blog.in")
				.password(passwordEncoder().encode("admin@123"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}

	//Security FilterChain for securing REST API
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity
				.csrf()
				.disable()
				.authorizeHttpRequests()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
		
		return httpSecurity.build(); 
	}
}
