package telran.java30.forum.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.forum.dao.UserAccountRepository;
import telran.java30.forum.dto.UserRegisterDto;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@PostMapping("/register")
	public UserAccountRepository addAccount(UserRegisterDto userRegisterDto) {
		return null;
		
	}
	

}
