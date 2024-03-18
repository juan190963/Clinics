package com.cristianRuizBlog.aplicacion.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Venta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6935881474611389708L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="venta_citas"
		,joinColumns=@JoinColumn(name="venta_id")
		,inverseJoinColumns=@JoinColumn(name="cita_id"))
	private Set<Cita> cita;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="venta_costo"
		,joinColumns=@JoinColumn(name="venta_id")
		,inverseJoinColumns=@JoinColumn(name="costo_id"))
	private Set<Costo> costo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="venta_doctores"
		,joinColumns=@JoinColumn(name="venta_id")
		,inverseJoinColumns=@JoinColumn(name="doctores_id"))
	private Set<Doctores> doctores;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="venta_servicio"
		,joinColumns=@JoinColumn(name="venta_id")
		,inverseJoinColumns=@JoinColumn(name="servicio_id"))
	private Set<Servicio> serivicios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Cita> getCita() {
		return cita;
	}

	public void setCita(Set<Cita> cita) {
		this.cita = cita;
	}

	public Set<Costo> getCosto() {
		return costo;
	}

	public void setCosto(Set<Costo> costo) {
		this.costo = costo;
	}

	public Set<Doctores> getDoctores() {
		return doctores;
	}

	public void setDoctores(Set<Doctores> doctores) {
		this.doctores = doctores;
	}

	public Set<Servicio> getSerivicios() {
		return serivicios;
	}

	public void setSerivicios(Set<Servicio> serivicios) {
		this.serivicios = serivicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cita, costo, doctores, id, serivicios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return Objects.equals(cita, other.cita) && Objects.equals(costo, other.costo)
				&& Objects.equals(doctores, other.doctores) && Objects.equals(id, other.id)
				&& Objects.equals(serivicios, other.serivicios);
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", cita=" + cita + ", costo=" + costo + ", doctores=" + doctores + ", serivicios="
				+ serivicios + "]";
	}
	
	
}
