package telran.java30.forum.service;

import java.util.Set;

import telran.java30.forum.dto.UserEditDto;
import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;

public interface UserService {
	UserProfileDto register(UserRegisterDto userRegisterDto);

	UserProfileDto login(String token);

	UserProfileDto editUser(String token, UserEditDto userEditDto);

	UserProfileDto removeUser(String token);

	void changePassword(String token, String password);

	Set<String> addRole(String login, String role, String token);

	Set<String> removeRole(String login, String role, String token);

}
