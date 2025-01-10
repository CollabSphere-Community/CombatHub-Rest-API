package br.com.combathub.combat_hub.infra.exception;

public class EventExpection {
	private final String message;
	private final Throwable throwable;
	private final ApplicationErrorCode status;

	public EventExpection(String message, Throwable throwable,  ApplicationErrorCode status) {
		this.message = message;
		this.throwable = throwable;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public ApplicationErrorCode getStatus() {
		return status;
	}
}
