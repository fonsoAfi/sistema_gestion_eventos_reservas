package ifw.daw.sger.exceptions;

public class RoleMismatchException extends RuntimeException {

	private static final long serialVersionUID = 151146741490761250L;
	private static String message;
	
	
	public RoleMismatchException() {
		message = "El tipo de organizador no coincide con ninguno de los valores permitidos";
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
