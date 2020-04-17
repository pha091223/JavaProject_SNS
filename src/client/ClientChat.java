package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import frame.ChkFrame;
import frame.HomeFrame;
import frame.JoinFrame;
import frame.LoginFrame;

public class ClientChat {
	private Socket withServer = null;
	private Socket withServerObject = null;
	
	private ClientChat nowCc = null;
	
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private LoginFrame loginF = null;
	private HomeFrame homeF = null;
	
	private String nowId = null;
	private String receiveMsg = null;
	
	ClientChat(Socket s){
		this.withServer = s;
		this.nowCc = this;
		receive();
		login(this);
	}
	
	public void receive() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(true) {
						reMsg = withServer.getInputStream();
						byte[] buffer = new byte[256];
						reMsg.read(buffer);
						String reMsg = new String(buffer);
						reMsg = reMsg.trim();
						receiveMsg = reMsg;
						
						System.out.println("/ReceiveMessage:" + receiveMsg);
						
						if(receiveMsg.contains("MyPage Delete true")) {
							System.exit(0);
						}
						
						ChkFrame chkF = new ChkFrame(receiveMsg, nowCc);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Server Out");
				}
			}
		}).start();
	}
	
	public void sleep() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getReceiveMessage() {
		return receiveMsg;
	}
	
	public void send(String msg) {
		try {
			seMsg = withServer.getOutputStream();
			seMsg.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login(ClientChat cc) {
		// TODO Auto-generated method stub
		loginF = new LoginFrame(cc);
	}
	
	public String getNowCcId() {
		return nowId;
	}
	
	public void Home(String chk, ClientChat cc) {
		// TODO Auto-generated method stub
		if(chk.indexOf("Login true")!=-1) {
			try {
				loginF.dispose();
				
				// Login True이니 ObjectStream을 위한 Socket Port Number 할당
				int portNum = Integer.valueOf(chk.substring(chk.indexOf(":")+1, chk.length()));
				
				if(portNum!=-1) {
					withServerObject = new Socket("10.0.0.104", portNum);
					System.out.println("/PortNumber:" + portNum);
				} else {
					System.out.println("Object Socket Port Wrong");
				}
				
				receive();
				
				homeF = HomeFrame.getInstance(nowId, cc);
				homeF.Frame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(chk.indexOf("Login false")!=-1){
			nowId = null;
		}
	}
	
	public Object getObject(String msg) {
		send(msg);
		return receiveObject();
	}
	
	public Object receiveObject() {
		// TODO Auto-generated method stub
		try {
			reMsg = withServerObject.getInputStream();
			byte[] reBuffer = new byte[1024];
			reMsg.read(reBuffer);
			
			ByteArrayInputStream bis = new ByteArrayInputStream(reBuffer);
			ObjectInputStream ois = new ObjectInputStream(bis);
			
			Object o = ois.readObject();
			
			return o;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void Join() {
		JoinFrame joinF = new JoinFrame(this);
	}

	public void loginSet(String id, String pwd) {
		try {
			String user = "login:" + id + "/" + pwd;
			
			nowId = id;
			
			System.out.println("id:" + id);
			System.out.println("pwd:" + pwd);
			
			seMsg = withServer.getOutputStream();
			seMsg.write(user.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
