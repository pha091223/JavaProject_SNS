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
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private LoginFrame loginF = null;
	private HomeFrame homeF = null;
	
	private String nowId = null;
	private String chk = null;
	
	ClientChat(Socket s){
		this.withServer = s;
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
						System.out.println(reMsg);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("Server Out");
				}
			}
		}).start();
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
			loginF.dispose();
			homeF = HomeFrame.getInstance(nowId, cc);
			// TableName 꼭 바꾸기 > Home이 시작될 때 글 목록 받아와야 함
			// setList - post 모든 내용
			Object pList = getObject("setList:" + "member" + "/" + nowId);
			homeF.Frame(pList);
		} else if(chk.indexOf("Login false")!=-1){
			nowId = null;
		}
	}
	
	public String getNowScId() {
		return nowId;
	}
	
	public Object getObject(String msg) {
		send(msg);
		return receiveObject();
	}
	
	public Object receiveObject() {
		// TODO Auto-generated method stub
		try {
			reMsg = withServer.getInputStream();
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
	
	public void chkSet(String msg) {
		try {
			seMsg = withServer.getOutputStream();
			seMsg.write(msg.getBytes());
			
			reMsg = withServer.getInputStream();
			byte[] buffer = new byte[256];
			reMsg.read(buffer);
			chk = new String(buffer);
			chk = chk.trim();
			
			System.out.println("/CheckMessage:" + chk);

			if(chk.contains("MyPage Delete true")) {
				System.exit(0);
			}
			
			ChkFrame chkF = new ChkFrame(chk, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getChkMessage() {
		return chk;
	}
	
	public void loginSet(String id, String pwd) {
		String user = "login:" + id + "/" + pwd;
		
		nowId = id;
		
		System.out.println("id:" + id);
		System.out.println("pwd:" + pwd);
		
		chkSet(user);
	}

}
