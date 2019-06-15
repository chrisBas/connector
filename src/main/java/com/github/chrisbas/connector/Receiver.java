package com.github.chrisbas.connector;

import com.github.chrisbas.connector.error.CommunicationException;
import com.github.chrisbas.connector.model.Command;
import com.github.chrisbas.connector.model.PasswordListner;
import com.github.chrisbas.connector.model.RequestMessage;
import com.github.chrisbas.connector.model.ResponseMessage;
import com.github.chrisbas.connector.model.Status;
import com.github.chrisbas.connector.model.StatusResult;
import com.github.chrisbas.connector.util.ServerCommunicator;

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
