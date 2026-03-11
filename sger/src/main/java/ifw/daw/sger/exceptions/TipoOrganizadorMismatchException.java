package ifw.daw.sger.exceptions;

public class TipoOrganizadorMismatchException extends RuntimeException {
	private static final long serialVersionUID = 151146741490761250L;
	private static String message;
	
	
	public TipoOrganizadorMismatchException() {
		message = "El tipo de organizador indicado no coincide con ninguno de los permitidos";
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
