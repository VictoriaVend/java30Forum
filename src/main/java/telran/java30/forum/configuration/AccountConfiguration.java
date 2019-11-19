package telran.java30.forum.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import telran.java30.forum.exeption.UserAuthenticationException;
import telran.java30.forum.service.UserAccountCredentials;

@Configuration
@ManagedResource
public class AccountConfiguration {
	@Value("${exp.value}")
	long expPeriod;

	@ManagedAttribute
	public long getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(long expPeriod) {
		this.expPeriod = expPeriod;
	}

	public UserAccountCredentials tokenDecode(String token) {
		try {
			int pos = token.indexOf(" ");
			token = token.substring(pos + 1);
			String str = new String(Base64.getDecoder().decode(token));
			String[] credention = str.split(":");

			return new UserAccountCredentials(credention[0], credention[1]);
		} catch (Exception e) {
			throw new UserAuthenticationException();
		}

	}
}
