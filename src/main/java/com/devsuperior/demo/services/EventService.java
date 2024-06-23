package com.devsuperior.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private  EventRepository repository;
	
	public List<EventDTO> findAll(){
		List<Event> list = repository.findAll();
		return  list.stream().map(x -> new  EventDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		
			try {
				Event entity = repository.getReferenceById(id);
				copyDtoToEntity(dto,  entity);
				//entity.setName(dto.getName());
				entity = repository.save(entity);
				return new EventDTO(entity);
			} catch (EntityNotFoundException e) {
				throw new ResourceNotFoundException("Id not found" + id);
			}
		
	}

	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		entity.setCity(new City(dto.getCityId(), null));
				
	}

}
