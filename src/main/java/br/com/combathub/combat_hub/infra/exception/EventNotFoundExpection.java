package br.com.combathub.combat_hub.infra.exception;

public class EventNotFoundExpection extends RuntimeException {
	
	public EventNotFoundExpection(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EventNotFoundExpection(String message) {
		super(message);
	}
}
