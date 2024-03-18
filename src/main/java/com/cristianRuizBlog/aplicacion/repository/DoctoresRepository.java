package com.cristianRuizBlog.aplicacion.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Doctores;
import com.cristianRuizBlog.aplicacion.entity.Role;

@Repository
public interface DoctoresRepository extends CrudRepository<Doctores, Long>{

	public Doctores findByName(String doctores);
}
