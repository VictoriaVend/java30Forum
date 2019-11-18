package telran.java30.forum.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;
import telran.java30.forum.service.UserService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@Autowired
	UserService userAccountService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userAccountService.register(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserProfileDto login(@RequestHeader("Authorization") String token) {
		return userAccountService.login(token);
	}
	

}
