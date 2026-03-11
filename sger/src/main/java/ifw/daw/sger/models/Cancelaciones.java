package ifw.daw.sger.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cancelaciones")
public class Cancelaciones {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCancelacion;
	
	@Column(name = "motivo_cancelacion")
	private String motivo;
	
	@Column(name = "fecha_cancelacion")
	private LocalDateTime fechaCancelacion;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reserva")
	private Reservas reserva;
	
	
	public Cancelaciones() {}
	
	// getters
	public int getIdCancelacion() {
		return idCancelacion;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public LocalDateTime getFechaCancelacion() {
		return fechaCancelacion;
	}
	
	public Reservas getReserva() {
		return reserva;
	}
	
	//setters
	public void setIdCancelacion(int idCancelacion) {
		this.idCancelacion = idCancelacion;
	}
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	
	public void setReserva(Reservas reserva) {
		this.reserva = reserva;
	}
}
