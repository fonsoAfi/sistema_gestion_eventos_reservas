package ifw.daw.sger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ifw.daw.sger.exceptions.dtos.ErrorMessages;

@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RoleMismatchException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessages> getRoleMismatchException(RoleMismatchException roleMisExcp) {
		ErrorMessages errorMsg = new ErrorMessages(HttpStatus.NOT_FOUND, roleMisExcp.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.IM_USED)
	public ResponseEntity<ErrorMessages> getUserAlreadyExistsException(UserAlreadyExistsException userExistsExcp) {
		ErrorMessages errorMsg = new ErrorMessages(HttpStatus.IM_USED, userExistsExcp.getMessage());
		return ResponseEntity.status(HttpStatus.IM_USED).body(errorMsg);
	}
	
}
