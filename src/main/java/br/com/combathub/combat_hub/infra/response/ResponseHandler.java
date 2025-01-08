package br.com.combathub.combat_hub.infra.response;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.combathub.combat_hub.domain.event.EventDTO;
import br.com.combathub.combat_hub.domain.event.EventEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> eventResponseBuilder(String message, HttpStatus httpStatus, EventEntity responseObject){
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		response.put("message", message);
		response.put("httpStatus", httpStatus);
		response.put("data", new EventDTO(responseObject));
		
		return new ResponseEntity<>(response, httpStatus);
	}
	
	public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseObject){
		HashMap<String, Object> response = new HashMap<String, Object>();
		
		response.put("message", message);
		response.put("httpStatus", httpStatus);
		response.put("data", responseObject);
		
		return new ResponseEntity<>(response, httpStatus);
	}
}
