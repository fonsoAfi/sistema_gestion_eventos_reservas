package ifw.daw.sger.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reembolsos")
public class Reembolsos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReembolso;
	
	@Column(name = "cantidad")
	private BigDecimal cantidad;
	
	@Column(name = "moneda", insertable = false)
	private String moneda;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoReembolso estado;
	
	@Column(name = "fecha_solicitud", insertable = false)
	private LocalDateTime fechaSolicitud;
	
	@Column(name = "fecha_reembolso")
	private LocalDateTime fechaReembolso;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reserva")
	private Reservas reserva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuarios usuario;
	
	
	public Reembolsos() {}
	
	
	// getters
	public int getIdReembolso() {
		return idReembolso;
	}
	
	public BigDecimal getCantidad() {
		return cantidad;
	}
	
	public String getMoneda() {
		return moneda;
	}
	
	public EstadoReembolso getEstado() {
		return estado;
	}
	
	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public LocalDateTime getFechaReembolso() {
		return fechaReembolso;
	}
	
	public Reservas getReserva() {
		return reserva;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}
	
	// setters
	public void setIdReembolso(int idReembolso) {
		this.idReembolso = idReembolso;
	}
	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public void setEstado(EstadoReembolso estado) {
		this.estado = estado;
	}
	
	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public void setFechaReembolso(LocalDateTime fechaReembolso) {
		this.fechaReembolso = fechaReembolso;
	}
	
	public void setReserva(Reservas reserva) {
		this.reserva = reserva;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
}
