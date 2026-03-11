package ifw.daw.sger.models;

public enum EstadoCuenta {
	activo,
	inactivo,
	pendiente,
	suspendido,
	deshabilitado;
	
	public boolean isActivo() {
		return (this == activo);
	}
}
