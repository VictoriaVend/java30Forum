package telran.java30.forum.service;

import java.util.List;

import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;

public interface UserService {
	UserProfileDto addUser(UserRegisterDto userRegisterDto);

	UserProfileDto updateUser(UserRegisterDto userRegisterDto);

	UserProfileDto findUser(String login);

	UserProfileDto deleteUser(String login);

	List<String> addRole(String login, String role);

	List<String> removeRole(String login, String role);
	
	boolean editPassword(String login, UserRegisterDto userRegisterDto); 
}
