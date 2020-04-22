package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import frame.ChkFrame;
import frame.DirectMessageFrame;
import frame.HomeFrame;
import frame.JoinFrame;
import frame.LoginFrame;
import frame.OneDMFrame;

public class ClientChat {
	private Socket withServer = null;
	private Socket withServerObject = null;
	
	private ClientChat nowCc = null;
	
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private LoginFrame loginF = null;
	private HomeFrame homeF = null;
	
	private DirectMessageFrame OpendmF = null;
	private OneDMFrame OneDmF = null;
	
	private String nowId = null;
	private String receiveMsg = null;
	
	private int portNum = -1;
	
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
						
						// Login True이니 ObjectStream을 위한 Socket Port Number 할당
						if(receiveMsg.indexOf("/Object port")!=-1) {
							portNum = Integer.valueOf(receiveMsg.substring(receiveMsg.indexOf(":")+1, receiveMsg.length()));
							
							if(portNum!=-1) {
								withServerObject = new Socket("10.0.0.104", portNum);
								System.out.println("/PortNumber:" + portNum);
							} else {
								System.out.println("Object Socket Port Wrong");
							}
						} 
						
						if(receiveMsg.contains("MyPage Delete true")) {
							System.exit(0);
						}
						
						ChkFrame chkF = new ChkFrame(receiveMsg, nowCc);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					System.out.println("Server Out");
				}
			}
		}).start();
	}
	
	// 만들어진 DM Frame을 받기 위한 메소드(실시간으로 주고받는 Msg Frame에 띄우기)
	public void setOpenDmFrame(DirectMessageFrame dmF) {
		this.OpendmF = dmF;
	}
	
	public DirectMessageFrame getOpenDmFrame() {
		return this.OpendmF;
	}
	
//	public void setOneDMFrame(OneDMFrame OneDmF) {
//		this.OneDmF = OneDmF;
//	}
//	
//	public OneDMFrame getOneDMFrame() {
//		return this.OneDmF;
//	}
//	
	public HomeFrame getHomeF() {
		return homeF;
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
			System.out.println("/Object:" + o);
			
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
