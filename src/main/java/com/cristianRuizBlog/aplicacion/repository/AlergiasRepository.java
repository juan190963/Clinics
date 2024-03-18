package com.cristianRuizBlog.aplicacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Alergias;
import com.cristianRuizBlog.aplicacion.entity.Role;

@Repository
public interface AlergiasRepository extends CrudRepository<Alergias, Long>{

	public Alergias findByName(String alergias);

}
