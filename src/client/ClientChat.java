package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import db.FavoriteDTO;
import db.FriendDTO;
import db.MemberDTO;
import db.PostDTO;

public class ClientChat {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private LoginFrame loginF = null;
	private ChkFrame chkF = null;
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
	
	public void Home(String chk, ClientChat cc) {
		// TODO Auto-generated method stub
		if(chk.indexOf("login true")!=-1) {
			loginF.setVisible(false);
			homeF = HomeFrame.getInstance(nowId, cc);
			receiveObject();
			homeF.Frame();
		} else {
			nowId = null;
		}
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
//			System.out.println(o);
			
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
			
			System.out.println(chk);
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
