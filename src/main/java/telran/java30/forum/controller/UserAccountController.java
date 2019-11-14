package telran.java30.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;
import telran.java30.forum.service.UserService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	@Autowired
	UserService userService;

	@PostMapping("/register")
	public UserProfileDto addUser(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.addUser(userRegisterDto);

	}

	@PutMapping("/update")
	public UserProfileDto updateUser(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.updateUser(userRegisterDto);

	}

	@GetMapping("/user/{login}")
	public UserProfileDto findUser(@PathVariable String login) {
		return userService.findUser(login);

	}

	@DeleteMapping("/user/delete/{login}")
	public UserProfileDto deleteUser(@PathVariable String login) {
		return userService.deleteUser(login);

	}

	@PostMapping("/user/{login}/role/{role}")
	public List<String> addRole(@PathVariable String login, @PathVariable String role) {
		return userService.addRole(login, role);

	}

	@DeleteMapping("/user/{login}/role/{role}")
	public List<String> removeRole(@PathVariable String login, @PathVariable String role) {
		return userService.removeRole(login, role);

	}

	@PutMapping("/update/password/{login}")
	public boolean editPassword(@PathVariable String login, @RequestBody UserRegisterDto userRegisterDto) {
		return userService.editPassword(login, userRegisterDto);

	}

}
