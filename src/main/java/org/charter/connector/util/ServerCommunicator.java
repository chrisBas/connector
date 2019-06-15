package org.charter.connector.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.charter.connector.error.CommunicationException;
import org.charter.connector.model.RequestMessage;
import org.charter.connector.model.ResponseMessage;

import com.google.gson.Gson;

public class ServerCommunicator {
	
	private final Socket socket;
	private final PrintWriter out;
	private final BufferedReader in;

	public ServerCommunicator() throws CommunicationException {
		try {
			this.socket = new Socket(Config.getHost(), Config.getPort());
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception ex) {
			throw new CommunicationException(ex);
		}
	}
	
	public ResponseMessage readMessageResponse() throws CommunicationException {
		try {
			String response = this.in.readLine();
			Gson gson = new Gson();
			return gson.fromJson(response, ResponseMessage.class);
		} catch (Exception ex) {
			throw new CommunicationException(ex);
		}
	}
	
	public void writeRequestMessage(RequestMessage requestMessage) {
		Gson gson = new Gson();
		this.out.println(gson.toJson(requestMessage));
	}
	
	public void writeMessage(String message) throws CommunicationException {
		this.out.println(message);
	}
	
	public String readMessage() throws CommunicationException {
		try {
			return this.in.readLine();
		} catch (Exception ex) {
			throw new CommunicationException(ex);
		}
	}
	
	public void cleanup() {
		try {
			this.in.close();
		} catch(Exception ex) {
			// do nothing
		}
		try {
			this.out.close();
		} catch(Exception ex) {
			// do nothing
		}
		try {
			this.socket.close();
		} catch(Exception ex) {
			// do nothing
		}
	}
	
}
