package ifw.daw.sger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ubicaciones")
public class Ubicaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUbicacion;
	
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "localidad")
	private String localidad;
	
	@Column(name = "codigo_postal")
	private String codigoPostal;
	
	@Column(name = "nombre_sala")
	private String nombreSala;
	
	@Column(name = "capacidad")
	private int capacidad;
	
	public Ubicaciones() {}
	
	// getters
	public int getIdUbicacion() {
		return idUbicacion;
	}
	
	public String getPais() {
		return pais;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	
	public String getCodigoPostal() {
		return codigoPostal;
	}
	
	public String getNombreSala() {
		return nombreSala;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	// setters
	public void setIdUbicacion(int idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
}
