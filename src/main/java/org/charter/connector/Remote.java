package org.charter.connector;

import org.charter.connector.error.CommunicationException;
import org.charter.connector.model.Command;
import org.charter.connector.model.RequestMessage;
import org.charter.connector.model.ResponseMessage;
import org.charter.connector.model.Status;
import org.charter.connector.model.StatusResult;
import org.charter.connector.util.ServerCommunicator;

public class Remote {
	
	private final ServerCommunicator serverCommunicator;

	public Remote(String id, String password) throws CommunicationException {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setCommand(Command.REMOTE);
		requestMessage.setId(id);
		requestMessage.setPassword(password);
		
		this.serverCommunicator = new ServerCommunicator();
		this.serverCommunicator.writeRequestMessage(requestMessage);
		ResponseMessage response = this.serverCommunicator.readMessageResponse();
		if( !Status.SUCCESS.equals(response.getStatus()) ) {
			this.serverCommunicator.cleanup();
			throw new CommunicationException(StatusResult.valueOf(response.getMessage()));
		}
	}
	
	public void write(String message) throws CommunicationException {
		try {
			this.serverCommunicator.writeMessage(message);
		} catch(CommunicationException ex) {
			this.serverCommunicator.cleanup();
			throw ex;
		}
	}

}
