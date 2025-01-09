package br.com.combathub.combat_hub.infra.exception;

public class DateNotValidException extends RuntimeException {
	
	public DateNotValidException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DateNotValidException(String message) {
		super(message);
	}
}
