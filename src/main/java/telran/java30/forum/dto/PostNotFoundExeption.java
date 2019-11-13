package telran.java30.forum.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class PostNotFoundExeption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public PostNotFoundExeption(String id) {
		super("Student with id "+id+" not found!");
	}

}
