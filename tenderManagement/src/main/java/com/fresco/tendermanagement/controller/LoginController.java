package com.fresco.tendermanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.tendermanagement.dto.LoginDTO;
import com.fresco.tendermanagement.model.LoginResponse;
import com.fresco.tendermanagement.model.UserModel;
import com.fresco.tendermanagement.repository.UserRepository;
import com.fresco.tendermanagement.security.JWTUtil;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationProvider authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<Object> authenticateUser(@RequestBody LoginDTO authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	
		}
		
		Optional<UserModel> user = userRepository.findByEmail(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(user.get().getEmail());
		if(jwt !=null) {
			LoginResponse loginResponse =new LoginResponse();
			loginResponse.setJwt(jwt);
			loginResponse.setStatus("200");
			loginResponse.setUsername(user.get().getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}