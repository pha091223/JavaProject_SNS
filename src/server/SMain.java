package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ServerSocket serverS = null;
		Socket withClient = null;
		
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("192.168.219.104", 9999));
		
		ServerCenter sc = new ServerCenter();
		
		while(true) {
			System.out.println("Server Waiting");
			withClient = serverS.accept();
			System.out.println("Client : " + withClient.getInetAddress());
			ServerChat ServerChat = new ServerChat(withClient, sc);
			sc.addSc(ServerChat);
			ServerChat.start();
		}

	}

}
