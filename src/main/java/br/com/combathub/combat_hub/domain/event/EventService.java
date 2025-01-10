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
    private EventRepository eventRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    public EventEntity getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundExpection("Event Not Found"));
    }
    
    public EventEntity createEvent(EventDTO event) {
        validateDates(event.getStartDate(), event.getEndDate());
        UserEntity organizer = getOrganizerById(event.getOrganizerId());
        EventEntity newEvent = new EventEntity(
                event.getName(),
                event.getDescription(),
                event.getStartDate(), 
                event.getEndDate(),
                event.getLocation(),
                event.getMaxParticipants(),
                organizer
        );
        return eventRepository.save(newEvent);
    }
    
    public UserEntity getOrganizerById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organizer Id not found."));
    }
    
    public void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isAfter(LocalDateTime.now())) {
            throw new DateNotValidException("Start date and time cannot be less than the current date and time.");
        }
        if (!startDate.isBefore(endDate)) {
            throw new DateNotValidException("End date and time cannot be less than the start date and time.");
        }
    }
}
