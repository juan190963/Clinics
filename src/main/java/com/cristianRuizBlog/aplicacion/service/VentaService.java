package com.cristianRuizBlog.aplicacion.service;

import java.util.List;

import javax.validation.Valid;

import com.cristianRuizBlog.aplicacion.entity.Venta;

public interface VentaService {

	public Iterable<Venta> getAllVentas();

	public List<Venta> listar();
	
	public Venta createVenta(Venta venta) throws Exception;
	
	public Venta getVentaById(Long id) throws Exception;
	
	public Venta updateVenta(Venta venta) throws Exception;
	
	public void deleteVenta(Long id) throws Exception;
}
