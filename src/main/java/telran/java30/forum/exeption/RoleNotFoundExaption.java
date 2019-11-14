package telran.java30.forum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class RoleNotFoundExaption extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public RoleNotFoundExaption(String id) {
		super("In Post with login "+id+" role not found!");
	}

}
