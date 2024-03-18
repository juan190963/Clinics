package com.cristianRuizBlog.aplicacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Costo;
import com.cristianRuizBlog.aplicacion.entity.Role;

@Repository
public interface CostoRepository extends CrudRepository<Costo, Long>{

	public Costo findByName(String costo);
}
