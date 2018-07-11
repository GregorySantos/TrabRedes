package br.com.ufba;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DateServer {
	

	public static void main(String[] args) {
		
		DatagramSocket serverSocket = null; //socket UDP
		final int PORT = 4000;
		
		try {
			serverSocket = new DatagramSocket(PORT);
			byte[] buffer = new byte[1000];
			
			while(true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				serverSocket.receive(request);

				long serverTime = System.currentTimeMillis();
				String svr = Long.toString(serverTime);
				byte[] sendTime = svr.getBytes();
				
				DatagramPacket reply = new DatagramPacket(sendTime, sendTime.length, request.getAddress(), request.getPort());
				serverSocket.send(reply);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
