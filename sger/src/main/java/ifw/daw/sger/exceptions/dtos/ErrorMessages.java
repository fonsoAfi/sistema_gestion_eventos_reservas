package ifw.daw.sger.exceptions.dtos;

import org.springframework.http.HttpStatus;

public class ErrorMessages {
	
	private HttpStatus status;
	private String message;
	
	public ErrorMessages() {}
	
	public ErrorMessages(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format("Error %s\n%s", status.toString(), message);
	}

}
