package org.charter.connector;

import org.charter.connector.error.CommunicationException;
import org.charter.connector.model.Command;
import org.charter.connector.model.PasswordListner;
import org.charter.connector.model.RequestMessage;
import org.charter.connector.model.ResponseMessage;
import org.charter.connector.model.Status;
import org.charter.connector.model.StatusResult;
import org.charter.connector.util.ServerCommunicator;

public class Receiver {

	private final ServerCommunicator serverCommunicator;
	
	public Receiver(String id, PasswordListner passwordListner) throws CommunicationException {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setCommand(Command.RECEIVER);
		requestMessage.setId(id);
		
		this.serverCommunicator = new ServerCommunicator();
		this.serverCommunicator.writeRequestMessage(requestMessage);
		ResponseMessage response = this.serverCommunicator.readMessageResponse();
		if( !Status.SUCCESS.equals(response.getStatus()) ) {
			this.serverCommunicator.cleanup();
			throw new CommunicationException(StatusResult.valueOf(response.getMessage()));
		} else {
			passwordListner.passwordFound(response.getMessage());
		}
		response = this.serverCommunicator.readMessageResponse();
		if( !Status.SUCCESS.equals(response.getStatus()) ) { 
			this.serverCommunicator.cleanup();
			throw new CommunicationException(StatusResult.valueOf(response.getMessage()));
		}
	}
	
	public String read() throws CommunicationException {
		try {
			return this.serverCommunicator.readMessage();
		} catch(CommunicationException ex) {
			this.serverCommunicator.cleanup();
			throw ex;
		}
	}
	
}
