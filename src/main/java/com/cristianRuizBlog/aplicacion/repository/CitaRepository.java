package com.cristianRuizBlog.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.Servicio;

import java.util.List;



@Repository
public interface CitaRepository extends CrudRepository<Cita, Long>{

	public Optional<Cita>   findByHora(String hora);
	
	public Optional<Cita>   findById(Long id);
	


}
