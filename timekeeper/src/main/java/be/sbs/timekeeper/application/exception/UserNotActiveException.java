package be.sbs.timekeeper.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotActiveException extends RuntimeException {
	public UserNotActiveException(String message) {
		super(message);
	}
}
