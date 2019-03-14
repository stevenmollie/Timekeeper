package be.sbs.timekeeper.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SessionAlreadyRunningException extends RuntimeException {
	public SessionAlreadyRunningException(String message) {
		super(message);
	}
}
