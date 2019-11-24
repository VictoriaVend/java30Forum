package telran.java30.account.controller;

import java.security.Principal;
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

import telran.java30.account.dto.UserEditDto;
import telran.java30.account.dto.UserProfileDto;
import telran.java30.account.dto.UserRegisterDto;
import telran.java30.account.service.UserService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@Autowired
	UserService userService;
	
	@PostMapping("/user")
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.register(userRegisterDto);
	}

	@PostMapping("/login")
	public UserProfileDto login(Principal principal) {
		
		return userService.login(principal.getName());
	}

	@PutMapping("/user/edit")
	public UserProfileDto editUser( @RequestBody UserEditDto userEditDto, Principal principal) {
		return userService.editUser(principal.getName(), userEditDto);
	}

	@DeleteMapping("/user")
	public UserProfileDto removeUser(Principal principal) {
		return userService.removeUser(principal.getName());
	}

	@PutMapping("/user/password")
	public void changePassword( @RequestHeader("X-Password")String password,Principal principal) {
		userService.changePassword(principal.getName(), password);
	}

	@PutMapping("/admin/edit/{login}/{role}")
	public Set<String> addRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token, Principal principal) {
		return userService.addRole(login, role);
	}

	@DeleteMapping("/admin/edit/{login}/{role}")
	public Set<String> removeRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token, Principal principal) {
		return userService.removeRole(login, role);
	}
}
