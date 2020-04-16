package test;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import javax.swing.JFrame;

import db.DAOCenter;

public class Test extends JFrame {	
	private DAOCenter Dc = DAOCenter.getInstance();
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new Test();
		
	}
	
	Test() throws Exception {
		ServerSocket serverO = new ServerSocket();
		serverO.bind(new InetSocketAddress("10.0.0.104", 8888));
		
		String a = "Login true/Object port:" + serverO.getLocalPort();
	}

}
