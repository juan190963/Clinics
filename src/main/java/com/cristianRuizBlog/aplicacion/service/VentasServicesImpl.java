package com.cristianRuizBlog.aplicacion.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.Venta;
import com.cristianRuizBlog.aplicacion.repository.VentaRepository;

@Service
public class VentasServicesImpl implements VentaService{

	@Autowired
	VentaRepository ventaRepository;
	@Override
	public Iterable<Venta> getAllVentas() {
		return ventaRepository.findAll();
	}
	
	private boolean checkVentaExist(Venta venta) throws Exception {
		Optional<Venta> ventaFound = ventaRepository.findByCita(venta.getCita());
		if(ventaFound.isPresent()) {
			throw new Exception("Cita existente");
		}
		return true;
	}

	@Override
	public Venta createVenta(Venta venta) throws Exception {
		if(checkVentaExist(venta)) {
			venta =  ventaRepository.save(venta);
		}
		return venta;
	}

	@Override
	public Venta getVentaById(Long id) throws Exception {
		return ventaRepository.findById(id).orElseThrow(() -> new Exception("La venta para actualizar no existe"));
	}

	@Override
	public Venta updateVenta(Venta fromVenta) throws Exception {
		Venta toVenta = getVentaById(fromVenta.getId());
		mapVenta(fromVenta,toVenta);
		return ventaRepository.save(toVenta);
	}

	protected void mapVenta(Venta from,Venta to) {
		to.setCita(from.getCita());
		to.setCosto(from.getCosto());
		to.setDoctores(from.getDoctores());
	}

	@Override
	public void deleteVenta(Long id) throws Exception {
		Optional<Venta> optionalVenta = ventaRepository.findById(id);
		if(optionalVenta.isPresent()) {
			Venta venta = optionalVenta.get();
			ventaRepository.delete(venta);
		}else {
			throw new RuntimeException("La cita con el ID  no existe.");
		}
	}

	@Override
	public List<Venta> listar() {
		// TODO Auto-generated method stub
		return (List<Venta>) ventaRepository.findAll();
	}

}
