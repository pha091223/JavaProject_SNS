package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Random r = new Random();
		
		ServerSocket serverS = null;
		ServerSocket serverO = null;
		
		Socket withClient = null;
		
		// ObjectStream ServerSocket PortNum 생성 : Main ServerSocket PortNum 제외
		int portNum = r.nextInt(9998)+1;
		
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.104", 9999));
		
		serverO = new ServerSocket();
		serverO.bind(new InetSocketAddress("10.0.0.104", portNum));
		
		ServerCenter sc = new ServerCenter();
		
		while(true) {
			System.out.println("Server Waiting");
			withClient = serverS.accept();
			System.out.println("Client : " + withClient.getInetAddress());
			ServerChat ServerChat = new ServerChat(withClient, serverO, sc);
			sc.addSc(ServerChat);
			ServerChat.start();
		}

	}

}
