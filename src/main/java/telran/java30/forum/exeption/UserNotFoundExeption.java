package telran.java30.forum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class UserNotFoundExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;
public UserNotFoundExeption(String id) {
	super("User with id "+id+" not found!");
}
}
