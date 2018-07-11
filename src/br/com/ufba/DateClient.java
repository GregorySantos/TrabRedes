package br.com.ufba;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class DateClient {

	public static void main(String[] args) {
		
		DatagramSocket clientSocket;
		
		try {
			clientSocket = new DatagramSocket();
			
			String msg = "GETTIME";
			byte[] req = msg.getBytes();
			
			InetAddress host = InetAddress.getByName("localhost");
			int port = 4000;
			DatagramPacket request = new DatagramPacket(req, req.length, host, port);
			clientSocket.send(request);
			
			byte[] buffer = new byte[10000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			clientSocket.receive(reply);
			
			long horalocal = System.currentTimeMillis();
			
			Date clientTime = new Date();
			clientTime.setTime(horalocal);
			System.out.println("local: " + clientTime.toString());
			
			String serverAnswer = new String(reply.getData());
			long serverTime = Long.parseLong(serverAnswer.trim());
			long systemNano = System.nanoTime();
			long currentServerTime = serverTime + ((System.nanoTime() - systemNano) / 1000000);
			Date svr = new Date();
			svr.setTime(currentServerTime);
			System.out.println("server: " + svr.toString());
			
			long delta = currentServerTime - System.currentTimeMillis();	
			clientTime.setTime(System.currentTimeMillis() + delta);
			System.out.println("local ajustado: " + clientTime.toString());
			
			clientSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
