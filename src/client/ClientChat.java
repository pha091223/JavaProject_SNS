package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientChat {
	private Socket withServer = null;
	private InputStream reMsg = null;
	private OutputStream seMsg = null;
	
	private LoginFrame loginF = null;
	private ChkFrame chkF = null;
	private HomeFrame homeF = null;
	
	private String nowId = null;
	
	ClientChat(Socket s){
		this.withServer = s;
		login(this);
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
		if(chk.indexOf("true")!=-1) {
			homeF = new HomeFrame(nowId);
		} else {
			nowId = null;
		}
	}
	
	public void Join() {
		JoinFrame joinF = new JoinFrame(this);
	}
	
	public void chkFrame(String msg) {
		try {
			seMsg = withServer.getOutputStream();
			seMsg.write(msg.getBytes());
			
			reMsg = withServer.getInputStream();
			byte[] buffer = new byte[256];
			reMsg.read(buffer);
			String chk = new String(buffer);
			chk = chk.trim();
			
			System.out.println(chk);
			ChkFrame chkF = new ChkFrame(chk, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loginSet(String id, String pwd) {
		String user = "login:" + id + "/" + pwd;
		
		nowId = id;
		
		System.out.println("id:" + id);
		System.out.println("pwd:" + pwd);
		
		chkFrame(user);
	}

}
