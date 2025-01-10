 package br.com.combathub.combat_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.combathub.combat_hub.domain.event.EventDTO;
import br.com.combathub.combat_hub.domain.event.EventEntity;
import br.com.combathub.combat_hub.domain.event.EventService;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.infra.exception.DateNotValidException;
import br.com.combathub.combat_hub.infra.response.ResponseHandler;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@GetMapping
	public String getEvent() {
		return "This is the get method for {url}/api/event";
	}
	
	@GetMapping("/{eventId}")
	public ResponseEntity<Object> getEventById(@PathVariable("eventId") Long eventId){
		return ResponseHandler.eventResponseBuilder("Successfully Retrived the Event.", HttpStatus.OK, eventService.getEventById(eventId));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createEvent(@Valid @RequestBody EventDTO event){
		
		return ResponseHandler.eventResponseBuilder("Event Created Successfully.", HttpStatus.CREATED, eventService.createEvent(event));
	}
}
