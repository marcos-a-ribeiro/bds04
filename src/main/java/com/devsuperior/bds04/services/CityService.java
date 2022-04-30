package com.devsuperior.bds04.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DatabaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City(dto.getId(), dto.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);
	}
	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
			City entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new CityDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAllByOrderByNameAsc() {
		Optional<List<City>> obj = repository.findAllByOrderByNameAsc();
		
		List<City> cities = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		List<CityDTO> list = cities.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
		return list;
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(CityDTO dto, City entity) {
		entity.setName(entity.getName());
	}
	
}
