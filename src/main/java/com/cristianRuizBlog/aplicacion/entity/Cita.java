package com.cristianRuizBlog.aplicacion.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Cita implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4481261917869946570L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	@Column
	@NotBlank
	@Size(min=10, max = 10, message = "No se cumple las reglas del tamaño")
	private String documento;
	@Column
	@NotBlank
	private String nombre;
	@Column
	@NotBlank
	private String apellido;
	@Column
	@NotBlank
	private String fecha;
	@Column
	@NotBlank
	private String hora;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="cita_servicios"
		,joinColumns=@JoinColumn(name="cita_id")
		,inverseJoinColumns=@JoinColumn(name="servicio_id"))
	private Set<Servicio> servicios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Set<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(Set<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, documento, fecha, hora, id, nombre, servicios, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cita other = (Cita) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(documento, other.documento)
				&& Objects.equals(fecha, other.fecha) && Objects.equals(hora, other.hora)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(servicios, other.servicios) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", user=" + user + ", documento=" + documento + ", nombre=" + nombre + ", apellido="
				+ apellido + ", fecha=" + fecha + ", hora=" + hora + ", servicios=" + servicios + "]";
	}
	
	
}
