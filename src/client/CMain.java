package client;

import java.net.Socket;

public class CMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Socket withServer = null;
		
		withServer = new Socket("10.0.0.104", 9999);
		new ClientChat(withServer);

	}

}
