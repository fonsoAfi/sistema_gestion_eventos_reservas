package ifw.daw.sger.dto;

public class ReservasPorEventoDTO {

	private int idEvento;
	private String tituloEvento;
	private Long totalReservas;
	private int plazasDisponibles;
	
	public ReservasPorEventoDTO(int idEvento, String tituloEvento, Long totalReservas, int plazasDisponibles) {
		this.idEvento = idEvento;
		this.tituloEvento = tituloEvento;
		this.totalReservas = totalReservas;
		this.plazasDisponibles = plazasDisponibles;
	}
	
	// getters
	public int getIdEvento() {
		return idEvento;
	}
	
	public String getTituloEvento() {
		return tituloEvento;
	}
	
	public Long getTotalReservas() {
		return totalReservas;
	}
	
	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	
	// setters
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	
	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}
	
	public void setTotalReservas(Long totalReservas) {
		this.totalReservas = totalReservas;
	}
	
	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}	
}
