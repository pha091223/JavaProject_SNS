package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerChat extends Thread {
	private Socket withClient = null;
	private ServerCenter sc = null;
	private ServerChat nowSc = null;
	
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	ServerChat(Socket c, ServerCenter sc){
		this.withClient = c;
		this.sc = sc;
		this.nowSc = this;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		receive();
	}
	
	private void receive() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						reMsg = withClient.getInputStream();
						byte[] buffer = new byte[256];
						reMsg.read(buffer);
						String reMsg = new String(buffer);
						reMsg = reMsg.trim();
						System.out.println(reMsg);
						sc.receiveClientMsg(reMsg, nowSc);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Client Logout");
				}
			}
		}).start();
	}
	
	public void send(String msg) {
		// TODO Auto-generated method stub
		try {
			String sMsg = msg;
			seMsg = withClient.getOutputStream();
			seMsg.write(sMsg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Client Logout");
		}
	}

}
