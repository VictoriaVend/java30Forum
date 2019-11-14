package telran.java30.forum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotAddedExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public UserNotAddedExeption(String id) {
		super("Post with login "+id+" already exists!");
	}

}
