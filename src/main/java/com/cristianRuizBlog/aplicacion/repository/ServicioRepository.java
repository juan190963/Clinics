package com.cristianRuizBlog.aplicacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Servicio;

@Repository
public interface ServicioRepository extends CrudRepository<Servicio, Long>{

	public Servicio findByName(String servicio);

}
