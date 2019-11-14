package telran.java30.forum.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class PostNotFoundExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public PostNotFoundExeption(String id) {
		super("Post with id "+id+" not found!");
	}

}
