package br.com.combathub.combat_hub.event;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.combathub.combat_hub.domain.event.*;
import br.com.combathub.combat_hub.domain.user.UserEntity;
import br.com.combathub.combat_hub.domain.user.UserRepository;
import br.com.combathub.combat_hub.infra.exception.DateNotValidException;
import br.com.combathub.combat_hub.infra.exception.EventNotFoundExpection;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    private EventDTO eventDTO;
    private EventEntity eventEntity;
    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        eventDTO = new EventDTO();
        eventDTO.setName("Test Event");
        eventDTO.setDescription("Test Description");
        eventDTO.setStartDate(LocalDateTime.now().plusDays(1));
        eventDTO.setEndDate(LocalDateTime.now().plusDays(2));
        eventDTO.setLocation("Test Location");
        eventDTO.setMaxParticipants(100);
        eventDTO.setOrganizerId(1L);
        
        userEntity = new UserEntity();

        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        eventEntity = new EventEntity("Test Event", "Test Description", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "Test Location", 100, userEntity);
    }

    @Test
    public void testGetEventById_Success() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(eventEntity));
        EventEntity result = eventService.getEventById(1L);
        assertEquals(eventEntity, result);
    }

    @Test
    public void testGetEventById_NotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundExpection.class, () -> eventService.getEventById(1L));
    }

    @Test
    public void testCreateEvent_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);

        EventEntity result = eventService.createEvent(eventDTO);
        assertNotNull(result);
        assertEquals(eventDTO.getName(), result.getName());
    }

    @Test
    public void testCreateEvent_InvalidDates_StartBeforeNow() {
        eventDTO.setStartDate(LocalDateTime.now().minusDays(1));
        assertThrows(DateNotValidException.class, () -> eventService.createEvent(eventDTO));
    }

    @Test
    public void testCreateEvent_InvalidDates_EndBeforeStart() {
        eventDTO.setEndDate(eventDTO.getStartDate().minusDays(1));
        assertThrows(DateNotValidException.class, () -> eventService.createEvent(eventDTO));
    }

    @Test
    public void testCreateEvent_OrganizerNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> eventService.createEvent(eventDTO));
    }

    @Test
    public void testGetOrganizerById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        UserEntity result = eventService.getOrganizerById(1L);
        assertEquals(userEntity, result);
    }

    @Test
    public void testGetOrganizerById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> eventService.getOrganizerById(1L));
    }

    @Test
    public void testValidateDates_StartBeforeNow() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        assertThrows(DateNotValidException.class, () -> eventService.validateDates(startDate, endDate));
    }

    @Test
    public void testValidateDates_EndBeforeStart() {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).minusHours(1);
        assertThrows(DateNotValidException.class, () -> eventService.validateDates(startDate, endDate));
    }

    @Test
    public void testValidateDates_ValidDates() {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);
        assertDoesNotThrow(() -> eventService.validateDates(startDate, endDate));
    }
}