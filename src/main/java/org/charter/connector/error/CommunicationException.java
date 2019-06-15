package org.charter.connector.error;

import org.charter.connector.model.StatusResult;

public class CommunicationException extends Exception {

	private static final long serialVersionUID = -546220363420619313L;
	
	public CommunicationException(Exception ex) {
		super(ex);
	}
	
	public CommunicationException(StatusResult statusResult) {
		super(statusResult.name());
	}

}
