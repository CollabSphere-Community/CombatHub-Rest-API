package br.com.combathub.combat_hub.domain.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Integer maxParticipants;
    private Long organizerId;
    
    public EventDTO() {
    }
    
    @JsonCreator
    public EventDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("startDate") LocalDateTime startDate,
        @JsonProperty("endDate") LocalDateTime endDate,
        @JsonProperty("location") String location,
        @JsonProperty("maxParticipants") Integer maxParticipants,
        @JsonProperty("organizerId") Long organizerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.organizerId = organizerId;
    }

    public EventDTO(EventEntity event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.location = event.getLocation();
        this.maxParticipants = event.getMaxParticipants();
        this.organizerId = event.getOrganizerId().getId();
    }
    
}