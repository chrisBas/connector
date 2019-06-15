package com.github.chrisbas.connector;

import com.github.chrisbas.connector.error.CommunicationException;
import com.github.chrisbas.connector.model.Command;
import com.github.chrisbas.connector.model.RequestMessage;
import com.github.chrisbas.connector.model.ResponseMessage;
import com.github.chrisbas.connector.model.Status;
import com.github.chrisbas.connector.model.StatusResult;
import com.github.chrisbas.connector.util.ServerCommunicator;

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
