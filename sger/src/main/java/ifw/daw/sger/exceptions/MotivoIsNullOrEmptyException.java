package ifw.daw.sger.exceptions;

public class MotivoIsNullOrEmptyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public MotivoIsNullOrEmptyException() {
		message = "No se ha aportado motivo de cancelacion";
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
