package ifw.daw.sger.dto;

public class CambiarClaveDTO {
	
	private String claveActual;
	private String claveNueva;
	private String claveRepetida;
	
	public CambiarClaveDTO() {}

	// Getters
	public String getClaveActual() {
		return claveActual;
	}
	
	public String getClaveNueva() {
		return claveNueva;
	}
	
	public String getClaveRepetida() {
		return claveRepetida;
	}
	
	// Setters
	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}
	
	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}
	
	public void setClaveRepetida(String claveRepetida) {
		this.claveRepetida = claveRepetida;
	}
	
}
