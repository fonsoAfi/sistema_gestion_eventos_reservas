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
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout")
public class Checkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCheckout;
	
	@Column(name = "monto_total")
	private BigDecimal montoTotal;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private EstadoCheckout estado;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_pago")
	private LocalDateTime fechaPago;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago")
	private MetodoPago metodoPago;
	
	@Column(name = "ip_origen")
	private String ipOrigen;
	
	@Column(name = "moneda")
	private String moneda;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "tiempo_expiracion")
	private LocalDateTime tiempoExpiracion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuarios usuario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reserva", nullable = false)
	private Reservas reserva;
	
	public Checkout() {}
	
	// getters
	public int getIdCheckout() {
		return idCheckout;
	}
	
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	
	public EstadoCheckout getEstado() {
		return estado;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public LocalDateTime getFechaPago() {
		return fechaPago;
	}
	
	public MetodoPago getMetodoPago() {
		return metodoPago;
	}
	
	public String getIpOrigen() {
		return ipOrigen;
	}
	
	public String getMoneda() {
		return moneda;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public LocalDateTime getTiempoExpiracion() {
		return tiempoExpiracion;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}
	
	public Reservas getReserva() {
		return reserva;
	}
	
	// setters
	public void setIdCheckout(int idCheckout) {
		this.idCheckout = idCheckout;
	}
	
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public void setEstado(EstadoCheckout estado) {
		this.estado = estado;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}
	
	public void setIpOrigen(String ipOrigen) {
		this.ipOrigen = ipOrigen;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public void setTiempoExpiracion(LocalDateTime tiempoExpiracion) {
		this.tiempoExpiracion = tiempoExpiracion;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public void setReserva(Reservas reserva) {
		this.reserva = reserva;
	}
}
