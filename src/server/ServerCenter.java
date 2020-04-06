package server;

import java.util.ArrayList;

import db.DAOCenter;
import db.MemberDTO;

public class ServerCenter {
	private DAOCenter Dc = null;
	private ServerChat nowSc = null;
	
	private ArrayList<ServerChat> sList = new ArrayList<>();
	
	ServerCenter(){
		Dc = DAOCenter.getInstance();
	}
	
	public void addSc(ServerChat sc) {
		sList.add(sc);
	}
	
	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		if(msg.indexOf("login:")!=-1) {
			String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
			String id = reMsg.substring(0, reMsg.indexOf("/"));
			String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.length());
			
			MemberDTO m = new MemberDTO();
			m.setId(id);
			m.setPwd(pwd);
			
			if(Dc.select("member", m)) {
				sc.send("login true");
			} else {
				sc.send("login false");
			}
		} else if(msg.indexOf("join:")!=-1) {
			String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
			String id = reMsg.substring(0, reMsg.indexOf("/"));
			String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
			String phone = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
			
			MemberDTO m = new MemberDTO();
			m.setId(id);
			m.setPwd(pwd);
			m.setPhone(phone);
			
			if(Dc.insert("member", m)) {
				sc.send("join true");
			} else {
				sc.send("join false");
			}
			
		}
	}

}
