package telran.java30.forum.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
	String login;
	String firstName;
	String lasteName;
	Set<String> roles;
}
