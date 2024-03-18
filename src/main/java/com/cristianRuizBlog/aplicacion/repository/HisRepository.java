package com.cristianRuizBlog.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Historial;
import java.util.List;


@Repository
public interface HisRepository extends CrudRepository<Historial, Long>{

	public Optional<Historial>  findByDiagnostico(String diagnostico);
	public Optional<Historial>  findById(Long id);
}
