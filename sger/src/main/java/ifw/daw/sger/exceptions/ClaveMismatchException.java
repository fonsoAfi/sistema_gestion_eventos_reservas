package ifw.daw.sger.exceptions;

public class ClaveMismatchException extends RuntimeException {
	
	private static final long serialVersionUID = -8295038075097713804L;
	private String message;
	
	public ClaveMismatchException() {
		message = "El rol indicado no coincide con ninguno de los roles permitidos";
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
