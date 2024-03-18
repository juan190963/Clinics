package com.cristianRuizBlog.aplicacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianRuizBlog.aplicacion.entity.Historial;
import com.cristianRuizBlog.aplicacion.repository.HisRepository;

@Service
public class HisServiceImpl implements HisService{

	@Autowired
	HisRepository hisRepository;
	
	@Override
	public Iterable<Historial> getAllHis() {
		return hisRepository.findAll();
	}
	
	private boolean checkHisExist(Historial historial) throws Exception {
		Optional<Historial> hisFound = hisRepository.findByDiagnostico(historial.getDiagnostico());
		if(hisFound.isPresent()) {
			throw new Exception("Paciente ya existe");
		}
		return true;
	}

	@Override
	public Historial createHis(Historial historial) throws Exception {
		if (checkHisExist(historial)) {
			historial = hisRepository.save(historial);
		}
		return historial;
	}

	@Override
	public Historial getHistorialById(Long id) throws Exception {
		return hisRepository.findById(id).orElseThrow(() -> new Exception("El Paciente no existe"));
	}

	@Override
	public Historial updateHis(Historial fromHistorial) throws Exception {
		Historial toHis = getHistorialById(fromHistorial.getId());
		mapHistorial (fromHistorial,toHis);
		return hisRepository.save(toHis);
	}
	
	protected void mapHistorial(Historial from,Historial to) {
		to.setVenta(from.getVenta());
		to.setDocumento(from.getDocumento());
		to.setNombre(from.getNombre());
		to.setApellido(from.getApellido());
		to.setEdad(from.getEdad());
		to.setGenero(from.getGenero());
		to.setAltura(from.getAltura());
		to.setAntecedentes(from.getAntecedentes());
		to.setAlergias(from.getAlergias());
		to.setReaccion(from.getReaccion());
		to.setMedicamentos(from.getMedicamentos());
		to.setExamen(from.getExamen());
		to.setDiagnostico(from.getDiagnostico());
		to.setTratamiento(from.getTratamiento());

		
	}

	@Override
	public void deleteHis(Long id) throws Exception {
		Optional<Historial> optionalHistorial = hisRepository.findById(id);
		if(optionalHistorial.isPresent()) {
			Historial historial = optionalHistorial.get();
			hisRepository.delete(historial);
		}else {
			throw new RuntimeException("La cita con el ID  no existe.");

		}
		
	}

	@Override
	public List<Historial> listar() {
		return (List<Historial>) hisRepository.findAll();
	}
	
	

}
