package ifw.daw.sger.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos") 
public class Eventos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvento;
	
	@Column(name = "titulo_evento")
	private String tituloEvento;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "limite_plazas")
	private int limitePlazas;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "fecha_inicio")
	private LocalDateTime fechaInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "fecha_fin")
	private LocalDateTime fechaFin;
	
	@Column(name = "plazas_disponibles")
	private int plazasDisponibles;
	
	@Column(name = "precio_base")
	private BigDecimal precioBase;
	
	@Column(name = "url_imagen", length = 500)
	private String urlImagen;
	
	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categorias categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ubicacion", nullable = false)
	private Ubicaciones ubicacion;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Organizadores organizador;
	
	
	public Eventos() {}
	
	public Eventos(int idEvento, String tituloEvento, String descripcion, int limitePlazas,
				   LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazasDisponibles, BigDecimal precioBase) {
		
		this.idEvento = idEvento;
		this.tituloEvento = tituloEvento;
		this.descripcion = descripcion;
		this.limitePlazas = limitePlazas;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.plazasDisponibles = plazasDisponibles;
		this.precioBase = precioBase;
	}
	
	
	// getters
	public int getIdEvento() {
		return idEvento;
	}
	
	public String getTituloEvento() {
		return tituloEvento;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public int getLimitePlazas() {
		return limitePlazas;
	}
	
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	
	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
	
	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	
	public BigDecimal getPrecioBase() {
		return precioBase;
	}
	
	public String getUrlImagen() {
		return urlImagen;
	}
	
	public Categorias getCategoria() {
		return categoria;
	}
	
	public Ubicaciones getUbicacion() {
		return ubicacion;
	}
	
	public Organizadores getOrganizador() {
		return organizador;
	}
	
	//setters
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	
	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setLimitePlazas(int limitePlazas) {
		this.limitePlazas = limitePlazas;
	}
	
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}
	
	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}
	
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}
	
	public void setUbicacion(Ubicaciones ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public void setOrganizador(Organizadores organizador) {
		this.organizador = organizador;
	}

}
