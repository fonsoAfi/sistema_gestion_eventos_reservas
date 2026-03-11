package ifw.daw.sger.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reservas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReserva;
	
	@Column(name = "titular")
	private String titular;
	
	@Column(name = "plazas_reservadas")
	private int plazasReservadas;
	
	@Column(name= "precio_base")
	private BigDecimal precioBase;
	
	@Column(name = "subtotal")
	private BigDecimal subtotal;
	
	@Column(name = "precio_total")
	private BigDecimal total;
	
	@Column(name = "gastos_gestion")
	private BigDecimal gastosGestion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuarios usuario;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento", nullable = false)
	private Eventos evento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoReserva estado;
	
	@Column(name = "fecha_reserva")
	private LocalDateTime fechaReserva;
	
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Checkout> checkouts = new ArrayList<>();
    
	@OneToOne(mappedBy = "reserva")
	private Cancelaciones cancelacion;
	
	@OneToOne(mappedBy = "reserva")
	private Reembolsos reembolso;
	    
	public Reservas() {}
	
	
	// getters
	public int getIdReserva() {
		return idReserva;
	}
	
	public String getTitular() {
		return titular;
	}
	
	public int getPlazasReservadas() {
		return plazasReservadas;
	}
	
	public BigDecimal getPrecioBase() {
		return precioBase;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public BigDecimal getGastosGestion() {
		return gastosGestion;
	}
	
	public Usuarios getUsuario() {
		return usuario;
	}
	
	public Eventos getEvento() {
		return evento;
	}
	
	public EstadoReserva getEstado() {
		return estado;
	}
	
	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}
	
	public List<Checkout> getCheckout() {
		return checkouts;
	}
	
	public Cancelaciones getCancelacion() {
		return cancelacion;
	}
	
	public Reembolsos getReembolso() {
		return reembolso;
	}
	
	// setters
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public void setPlazasReservadas(int plazasReservadas) {
		this.plazasReservadas = plazasReservadas;
	}
	
	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}
	
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public void setGastosGestion(BigDecimal gastosGestion) {
		this.gastosGestion = gastosGestion;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public void setEvento(Eventos evento) {
		this.evento = evento;
	}
	
	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}
	
	public void setFechaReserva(LocalDateTime fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	
	public void setCheckout(List<Checkout> checkouts) {
		this.checkouts = checkouts;
	}
	
	public void setCancelacion(Cancelaciones cancelacion) {
		this.cancelacion = cancelacion;
	}
	
	public void setReembolso(Reembolsos reembolso) {
		this.reembolso = reembolso;
	}
}
