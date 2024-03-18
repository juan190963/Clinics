package com.cristianRuizBlog.aplicacion.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.Venta;
import java.util.List;
import java.util.Set;

@Repository
public interface VentaRepository extends CrudRepository<Venta, Long>{

	public Optional<Venta> findByCita(Set<Cita> cita);

	public Optional<Venta> findById(Long id);
}
