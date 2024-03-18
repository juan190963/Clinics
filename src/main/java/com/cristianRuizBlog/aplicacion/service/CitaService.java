package com.cristianRuizBlog.aplicacion.service;

import java.util.List;

import javax.validation.Valid;

import com.cristianRuizBlog.aplicacion.entity.Cita;

public interface CitaService {

	public Iterable<Cita> getAllCitas();
	
	public List<Cita> listar();

	public Cita createCita(Cita cita) throws Exception;
	
	public Cita getCitaById(Long id) throws Exception;
	
	public Cita updateCita(Cita cita) throws Exception;

	public void deleteCita(Long id) throws Exception;
}
