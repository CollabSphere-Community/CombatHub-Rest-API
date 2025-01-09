package br.com.combathub.combat_hub.domain.event;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.domain.user.UserRepository;
import br.com.combathub.combat_hub.infra.exception.DateNotValidException;
import br.com.combathub.combat_hub.infra.exception.EventNotFoundExpection;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	public EventEntity getUserById(Long id) {
		return eventRepository.findById(id).orElseThrow(()-> new EventNotFoundExpection("Event Not Found"));
	}
	
	public EventEntity createEvent(EventEntity event) {
		try {			
			return eventRepository.save(event);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} 
	}
	
	public UserEntity getOrganizerbyid(Long id) {
    	return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Organizer Id not found."));
    }
	
	public boolean isDatesValid(LocalDateTime startDate, LocalDateTime endDate) {
		if(!startDate.isAfter(LocalDateTime.now())) throw new DateNotValidException("start date and time can't be less then current date and time.");
		if(!startDate.isBefore(endDate)) throw new DateNotValidException("end date and time can't be less then start date and time.");;
		return true;
	}
	

}
