package org.charter.connector.model;

public class ResponseMessage {

	private Status status;
	private String message;

	public ResponseMessage(String message, Status status) {
		this.setMessage(message);
		this.setStatus(status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
