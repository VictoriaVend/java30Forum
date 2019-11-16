package telran.java30.forum.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.java30.forum.dao.UserAccountRepository;
import telran.java30.forum.dto.UserProfileDto;
import telran.java30.forum.dto.UserRegisterDto;
import telran.java30.forum.exeption.RoleNotFoundExaption;
import telran.java30.forum.exeption.UserNotAddedExeption;
import telran.java30.forum.exeption.UserNotFoundExeption;
import telran.java30.forum.model.UserAccount;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserAccountRepository userAccountRepository;

	@Override
	public UserProfileDto addUser(UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserNotAddedExeption(userRegisterDto.getLogin());
		}
		Set<String>setRose =new HashSet<String>();
		setRose.add("User");
		UserAccount user = UserAccount.builder().login(userRegisterDto.getLogin())
				.password(userRegisterDto.getPassword()).expDate(LocalDateTime.now().plusDays(90)).roles(setRose/*new HashSet<String>() ne rabotaet*/)
				.firstName(userRegisterDto.getFirstName()).lastName(userRegisterDto.getLastName()).build();
		/*user.addRole("User"); ne rabotaet*/
		userAccountRepository.save(user);
		return userAccountToUserProfile(user);
	}

	@Override
	public UserProfileDto updateUser(UserRegisterDto userRegisterDto) {
		UserAccount user = userAccountRepository.findById(userRegisterDto.getLogin())
				.orElseThrow(() -> new UserNotFoundExeption(userRegisterDto.getLogin()));

		if (userRegisterDto.getFirstName() != null)
			user.setFirstName(userRegisterDto.getFirstName());
		if (userRegisterDto.getLastName() != null)
			user.setLastName(userRegisterDto.getLastName());
		if (userRegisterDto.getPassword() != null) {
			user.setPassword(userRegisterDto.getPassword());
			user.setExpDate(LocalDateTime.now());
		}
		userAccountRepository.save(user);
		return userAccountToUserProfile(user);
	}

	@Override
	public UserProfileDto findUser(String login) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(() -> new UserNotFoundExeption(login));
		return userAccountToUserProfile(user);
	}

	@Override
	public UserProfileDto deleteUser(String login) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(() -> new UserNotFoundExeption(login));
		userAccountRepository.deleteById(login);
		return userAccountToUserProfile(user);
	}

	@Override
	public List<String> addRole(String login, String role) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(() -> new UserNotFoundExeption(login));
		user.addRole(role);
		userAccountRepository.save(user);
		List<String> list = new LinkedList<String>();
		user.getRoles().forEach(s->list.add(s));
		return list;
	}

	@Override
	public List<String> removeRole(String login, String role) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(() -> new UserNotFoundExeption(login));
		if (!user.removeRole(role)) {
			throw new RoleNotFoundExaption(login);
		}
		userAccountRepository.save(user);
		List<String> list = new LinkedList<String>();
		user.getRoles().forEach(list::add);
		return list;
	}

	@Override
	public boolean editPassword(String login, UserRegisterDto userRegisterDto) {
		if (!login.equals(userRegisterDto.getLogin()))
			return false;
		UserAccount user = userAccountRepository.findById(login).orElseThrow(() -> new UserNotFoundExeption(login));
		if (userRegisterDto.getPassword() == null)
			return false;
		user.setPassword(userRegisterDto.getPassword());
		user.setExpDate(LocalDateTime.now());
		userAccountRepository.save(user);
		return true;
	}

	private UserProfileDto userAccountToUserProfile(UserAccount user) {
		return UserProfileDto.builder().login(user.getLogin()).firstName(user.getFirstName())
				.lastName(user.getLastName()).roles(user.getRoles()).build();

	}

}
