package com.itSolution.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itSolution.bindings.AuthRequest;
import com.itSolution.entity.UserEntity;
import com.itSolution.service.JwtService;
import com.itSolution.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private MyUserDetailsService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtService jwt;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserEntity user) {
		
		String encodedPwd = encoder.encode(user.getUpwd());
		user.setUpwd(encodedPwd);
		
		boolean saveUser = service.saveUser(user);
		
		if(saveUser) {
			return "User Registered";
		}
		else {
			return "Registration  Failed";
		}	
	}
	@PostMapping("/login")
	public String userAuthentication(@RequestBody AuthRequest request) {
		
	UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(request.getUname(),request.getPwd());	
	    try {
	    	Authentication auth = authManager.authenticate(token);
			if(auth.isAuthenticated()) {
				
				//generate jwt token and send to user
				
				String jwtToken = jwt.generateToken(request.getUname());
				return jwtToken;
			}   	
		} catch (Exception e) {
		 e.printStackTrace();	
		}
			return "Authentication Failed";	
	}	
	@GetMapping("/welcome")
	public String getWelcomeMsg() {
		
		return "Welcome Home Page , You Passed Authentication";
	}
	@GetMapping("/greet")
	public String getGreetMsg() {
		
		return "Good Morning ...!!!";
	}
}
