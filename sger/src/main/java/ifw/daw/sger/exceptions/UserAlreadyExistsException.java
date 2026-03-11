package ifw.daw.sger.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -896210638954994897L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
