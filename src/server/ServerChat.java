package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends Thread {
	private ServerSocket serverO = null;

	private Socket withClient = null;
	private Socket withClientObject = null;
	
	private ServerCenter sc = null;
	private ServerChat nowSc = null;
	
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private String nowId = null;
	
	ServerChat(Socket c, ServerSocket o, ServerCenter sc){
		this.withClient = c;
		this.serverO = o;
		this.sc = sc;
		this.nowSc = this;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			receive();
			withClientObject = serverO.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getObjectPortNum() {
		return String.valueOf(serverO.getLocalPort());
	}
	
	public String getNowScId() {
		return nowId;
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
						
						if(nowId==null && reMsg.indexOf("getList:")!=-1) {
							nowId = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
						}
						
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
	
	public void sendDB(byte[] resultByte) {
		try {
			seMsg = withClientObject.getOutputStream();
			seMsg.write(resultByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
