package br.com.combathub.combat_hub.infra.exception;
import org.springframework.http.HttpStatus;


public class EventExpection {
	private final String message;
	private final Throwable throwable;
	private final HttpStatus HttpStatus;

	public EventExpection(String message, Throwable throwable,  HttpStatus httpStatus) {
		this.message = message;
		this.throwable = throwable;
		HttpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus;
	}
}
