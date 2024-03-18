package com.cristianRuizBlog.aplicacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.repository.CitaRepository;

@Service
public class CitaServiceImpl implements CitaService{

	@Autowired
	CitaRepository citaRepository;
	@Override
	public Iterable<Cita> getAllCitas() {
		return citaRepository.findAll();
	}
	
	private boolean checkCitaExist(Cita cita) throws Exception {
		Optional<Cita> citaFound = citaRepository.findByHora(cita.getHora());
		if(citaFound.isPresent()) {
			throw new Exception("Hora no disponible");
		}
		return true;
	}

	@Override
	public Cita createCita(Cita cita) throws Exception {
		if(checkCitaExist(cita)) {
			cita = citaRepository.save(cita);
		}
		return cita;
	}

	@Override
	public Cita getCitaById(Long id) throws Exception {
		return citaRepository.findById(id).orElseThrow(() ->  new Exception("La cita para actualizar no existe" ));
	}
	
	

	@Override
	public Cita updateCita(Cita fromCita) throws Exception {
		Cita toCita = getCitaById(fromCita.getId());
		mapCita(fromCita,toCita);
		return citaRepository.save(toCita);
		 
	}
	
	protected void mapCita(Cita from,Cita to) {
		to.setDocumento(from.getDocumento());
		to.setNombre(from.getNombre());
		to.setApellido(from.getApellido());
		to.setFecha(from.getFecha());
		to.setHora(from.getHora());
		to.setServicios(from.getServicios());
	}

	@Override
	public void deleteCita(Long id) throws Exception {
		Optional<Cita> optionalCita = citaRepository.findById(id);
		if(optionalCita.isPresent()) {
			Cita cita = optionalCita.get();
			cita.getServicios().clear();
			citaRepository.delete(cita);
		}else {
			throw new RuntimeException("La cita con el ID  no existe.");
		}
		
	}

	@Override
	public List<Cita> listar() {
		return (List<Cita>) citaRepository.findAll();
	}

	
	

}
