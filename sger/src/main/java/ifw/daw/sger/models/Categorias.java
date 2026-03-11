package ifw.daw.sger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categorias {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCategoria;
	
	@Column(name = "nombre_categoria")
	private String nombreCategoria;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	
	public Categorias() {}
	
	// getters 
	public int getIdCategoria() {
		return idCategoria;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	// setters
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
