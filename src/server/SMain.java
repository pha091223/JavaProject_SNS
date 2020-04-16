package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket serverS = null;
		ServerSocket serverO = null;
		
		Socket withClient = null;
		
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.104", 9999));
		
		serverO = new ServerSocket();
		serverO.bind(new InetSocketAddress("10.0.0.104", 8888));
		
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
