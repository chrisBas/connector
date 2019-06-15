package org.charter.connector.util;

public class Config {
	
	private static String HOST;
	private static Integer PORT;
	
	private Config() {
		
	}
	
	public static void setHost(String hostName) {
		HOST = hostName;
	}
	
	public static String getHost() {
		return HOST;
	}
	
	public static void setPort(int portNum) {
		PORT = portNum;
	}
	
	public static Integer getPort() {
		return PORT;
	}

}
