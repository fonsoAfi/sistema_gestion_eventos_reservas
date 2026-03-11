package ifw.daw.sger.models;

public enum Rol {
	ASISTENTES,
	ORGANIZADORES,
    ADMINISTRADORES;
    
	public boolean isAdmin() {
		return (this == ADMINISTRADORES);
	}
	
    public String toString() {
		return name();
	}
}
