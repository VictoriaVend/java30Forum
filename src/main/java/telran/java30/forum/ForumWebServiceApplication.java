package telran.java30.forum;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.java30.forum.dao.UserAccountRepository;
import telran.java30.forum.model.UserAccount;

@SpringBootApplication
public class ForumWebServiceApplication implements CommandLineRunner {
	@Autowired
	UserAccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(ForumWebServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (accountRepository.existsById("admin")) {
			String pas = BCrypt.hashpw("admin", BCrypt.gensalt());
			UserAccount admin = UserAccount.builder().login("admin").password(pas).firstName("admin").lastName("admin")
					.role("Administrator").role("Maderator").role("User").expDate(LocalDateTime.MAX).build();

			accountRepository.save(admin);
		}
	}

}
