package telran.java30.forum.controller;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.UserEditDto;
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

	@PutMapping("/user/edit")
	public UserProfileDto editUser(@RequestHeader("Authorization") String token, @RequestBody UserEditDto userEditDto) {
		return userAccountService.editUser(token, userEditDto);
	}

	@DeleteMapping("/user")
	public UserProfileDto removeUser(@RequestHeader("Authorization") String token) {
		return userAccountService.removeUser(token);
	}

	@PutMapping("/user/edit/password")
	public void changePassword(@RequestHeader("Authorization") String token, MessageDto password) {
		userAccountService.changePassword(token, password);
	}

	@PutMapping("/admin/edit/{login}/{role}")
	public Set<String> addRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token) {
		return userAccountService.addRole(login, role, token);
	}

	@PutMapping("/admin/edit/{login}/{role}")
	public Set<String> removeRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token) {
		return userAccountService.removeRole(login, role, token);
	}
}
