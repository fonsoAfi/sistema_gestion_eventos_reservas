package ifw.daw.sger.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class Ventas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVenta;
	
	@Column(name = "fecha_venta")
	private LocalDate fechaVenta;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private EstadoVenta estado;
	
	@Column(name = "monto_total")
	private BigDecimal montoTotal;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuarios usuario;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento", nullable = false)
	private Eventos evento;
	
	public Ventas() {}
	
	// getters
	public int getIdVenta() {
		return idVenta;
	}
	
	public LocalDate getFechaVenta() {
		return fechaVenta;
	}
	
	public EstadoVenta getEstado() {
		return estado;
	}
	
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}
	
	public Eventos getEvento() {
		return evento;
	}
	
	// setters
	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}
	
	public void setFechaVenta(LocalDate fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	public void setEstado(EstadoVenta estado) {
		this.estado = estado;
	}
	
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public void setEvento(Eventos evento) {
		this.evento = evento;
	}
	
}
