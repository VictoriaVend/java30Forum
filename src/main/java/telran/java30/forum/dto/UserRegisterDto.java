package telran.java30.forum.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserRegisterDto {
	String login;
	String password;
	String firstName;
	String lastName;
}