package be.sbs.timekeeper.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyActivatedException extends RuntimeException {
	public UserAlreadyActivatedException(String message) {
		super(message);
	}
}
