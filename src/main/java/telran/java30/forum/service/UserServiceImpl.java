package telran.java30.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java30.forum.configuration.AccountConfiguration;
import telran.java30.forum.dao.UserAccountRepository;
import telran.java30.forum.dto.MessageDto;
import telran.java30.forum.dto.UserEditDto;
import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;
import telran.java30.forum.exeption.ForbiddenException;
import telran.java30.forum.exeption.UserAuthenticationException;
import telran.java30.forum.exeption.UserExistsException;
import telran.java30.forum.model.UserAccount;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserAccountRepository accountRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	UserAccount userAccountAdmin = accountRepository.findById("admin").orElseThrow(UserAuthenticationException::new);

	@Override
	public UserProfileDto register(UserRegisterDto userRegisterDto) {
		if (accountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException();
		}
		String hashPassword = BCrypt.hashpw(userRegisterDto.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder().login(userRegisterDto.getLogin()).password(hashPassword)
				.firstName(userRegisterDto.getFirstName()).lastName(userRegisterDto.getLastName()).role("User")
				.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();
		accountRepository.save(userAccount);
		return userAccountToUserProfileDto(userAccount);
	}

	private UserProfileDto userAccountToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder().login(userAccount.getLogin()).firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName()).roles(userAccount.getRoles()).build();
	}

	@Override
	public UserProfileDto login(String token) {
		UserAccount userAccount = authentication(token);
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto editUser(String token, UserEditDto userEditDto) {

		UserAccount userAccount = authentication(token);
		userAccount.setFirstName(userEditDto.getFirstName());
		userAccount.setLastName(userEditDto.getLastName());
		accountRepository.save(userAccount);

		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String token) {
		UserAccount userAccount = authentication(token);
		accountRepository.deleteById(userAccount.getLogin());
		return userAccountToUserProfileDto(userAccount);
	}

	@Override
	public void changePassword(String token, MessageDto password) {
		UserAccount userAccount = authentication(token);
		userAccount.setPassword(BCrypt.hashpw(password.getMessage(), BCrypt.gensalt()));
		userAccount.setExpDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
		accountRepository.save(userAccount);

	}

	@Override
	public Set<String> addRole(String login, String role, String token) {
		
		if(userAccountAdmin.equals(authentication(token))) {throw new ForbiddenException();}
		UserAccount userAccount = accountRepository.findById(login)
				.orElseThrow(UserAuthenticationException::new);
		userAccount.addRole(role);
		accountRepository.save(userAccount);
		return userAccount.getRoles();
			
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {
		if(userAccountAdmin.equals(authentication(token))) {throw new ForbiddenException();}
		UserAccount userAccount = accountRepository.findById(login)
				.orElseThrow(UserAuthenticationException::new);
		
		if(userAccount.removeRole(role)) {accountRepository.save(userAccount);}
		return userAccount.getRoles();
	}

	private UserAccount authentication(String token) {
		UserAccountCredentials userAccountCredentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = accountRepository.findById(userAccountCredentials.getLogin())
				.orElseThrow(UserAuthenticationException::new);
		if (!BCrypt.checkpw(userAccountCredentials.getPassword(), userAccount.getPassword())) {
			throw new ForbiddenException();
		}
		return userAccount;

	}
}
