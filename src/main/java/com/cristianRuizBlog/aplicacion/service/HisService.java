package com.cristianRuizBlog.aplicacion.service;


import java.util.List;

import com.cristianRuizBlog.aplicacion.entity.Historial;

public interface HisService {

	public Iterable<Historial> getAllHis();
	
	public List<Historial> listar();

	public Historial createHis(Historial historial) throws Exception;
	
	public Historial getHistorialById(Long id)throws Exception;
	
	public Historial updateHis(Historial historial)throws Exception;
	
	public void deleteHis(Long id) throws Exception;
	
}
