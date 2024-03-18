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

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Historial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2065033475704380617L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="historial_venta"
		,joinColumns=@JoinColumn(name="historial_id")
		,inverseJoinColumns=@JoinColumn(name="venta_id"))
	private Set<Venta> venta;
	
	@Column
	@NotBlank
	private String documento;
	@Column
	@NotBlank
	private String nombre;
	@Column
	@NotBlank
	private String apellido;
	
	@Column
	@NotBlank
	private String edad;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="historial_genero"
		,joinColumns=@JoinColumn(name="historial_id")
		,inverseJoinColumns=@JoinColumn(name="genero_id"))
	private Set<Genero> genero;
	
	@Column
	@NotBlank
	private String peso;
	
	@Column
	@NotBlank
	private String altura;
	
	@Column
	@NotBlank
	private String antecedentes;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="historial_alergias"
		,joinColumns=@JoinColumn(name="historial_id")
		,inverseJoinColumns=@JoinColumn(name="alergias_id"))
	private Set<Alergias> alergias;
	
	@Column
	@NotBlank
	private String reaccion;
	
	@Column
	@NotBlank
	private String medicamentos;
	
	@Column
	@NotBlank
	private String examen;
	
	@Column
	@NotBlank
	private String diagnostico;
	
	@Column
	@NotBlank
	private String tratamiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Venta> getVenta() {
		return venta;
	}

	public void setVenta(Set<Venta> venta) {
		this.venta = venta;
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

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public Set<Genero> getGenero() {
		return genero;
	}

	public void setGenero(Set<Genero> genero) {
		this.genero = genero;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}

	public Set<Alergias> getAlergias() {
		return alergias;
	}

	public void setAlergias(Set<Alergias> alergias) {
		this.alergias = alergias;
	}

	public String getReaccion() {
		return reaccion;
	}

	public void setReaccion(String reaccion) {
		this.reaccion = reaccion;
	}

	public String getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(String medicamentos) {
		this.medicamentos = medicamentos;
	}

	public String getExamen() {
		return examen;
	}

	public void setExamen(String examen) {
		this.examen = examen;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alergias, altura, antecedentes, apellido, diagnostico, documento, edad, examen, genero, id,
				medicamentos, nombre, peso, reaccion, tratamiento, venta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historial other = (Historial) obj;
		return Objects.equals(alergias, other.alergias) && Objects.equals(altura, other.altura)
				&& Objects.equals(antecedentes, other.antecedentes) && Objects.equals(apellido, other.apellido)
				&& Objects.equals(diagnostico, other.diagnostico) && Objects.equals(documento, other.documento)
				&& Objects.equals(edad, other.edad) && Objects.equals(examen, other.examen)
				&& Objects.equals(genero, other.genero) && Objects.equals(id, other.id)
				&& Objects.equals(medicamentos, other.medicamentos) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(peso, other.peso) && Objects.equals(reaccion, other.reaccion)
				&& Objects.equals(tratamiento, other.tratamiento) && Objects.equals(venta, other.venta);
	}

	@Override
	public String toString() {
		return "Historial [id=" + id + ", venta=" + venta + ", documento=" + documento + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", edad=" + edad + ", genero=" + genero + ", peso=" + peso + ", altura="
				+ altura + ", antecedentes=" + antecedentes + ", alergias=" + alergias + ", reaccion=" + reaccion
				+ ", medicamentos=" + medicamentos + ", examen=" + examen + ", diagnostico=" + diagnostico
				+ ", tratamiento=" + tratamiento + "]";
	}

	
	
}
