package com.devsuperior.bds04.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.bds04.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
	
	public Optional<List<City>> findAllByOrderByNameAsc();
	

}
