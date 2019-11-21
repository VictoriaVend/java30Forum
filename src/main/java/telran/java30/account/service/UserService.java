package telran.java30.account.service;

import java.util.Set;

import telran.java30.account.dto.UserEditDto;
import telran.java30.account.dto.UserProfileDto;
import telran.java30.account.dto.UserRegisterDto;
import telran.java30.forum.dto.MessageDto;

public interface UserService {
	UserProfileDto register(UserRegisterDto userRegisterDto);

	UserProfileDto login(String token);

	UserProfileDto editUser(String token, UserEditDto userEditDto);

	UserProfileDto removeUser(String token);

	void changePassword(String token, MessageDto password);

	Set<String> addRole(String login, String role, String token);

	Set<String> removeRole(String login, String role, String token);

}
