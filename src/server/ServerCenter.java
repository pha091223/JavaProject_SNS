package server;

import java.util.ArrayList;

import db.DAOCenter;
import db.FavoriteDTO;
import db.FriendDTO;
import db.MemberDTO;
import db.PostDTO;

public class ServerCenter {
	private DAOCenter Dc = null;
	private ServerChat nowSc = null;
	
	private ArrayList<ServerChat> sList = new ArrayList<>();
	
	private ArrayList<MemberDTO> mList = new ArrayList<>();
	private ArrayList<PostDTO> pList = new ArrayList<>();
	private ArrayList<FavoriteDTO> fList = new ArrayList<>();
	private ArrayList<FriendDTO> frList = new ArrayList<>();
	
	private boolean idChk = false;
	
	ServerCenter(){
		Dc = DAOCenter.getInstance();
		if(Dc!=null) {
			getDBList();
		}
	}
	
	private void getDBList() {
		// TODO Auto-generated method stub
		mList = (ArrayList<MemberDTO>)Dc.getDB("member");
		pList = (ArrayList<PostDTO>)Dc.getDB("post");
		fList = (ArrayList<FavoriteDTO>)Dc.getDB("favorite");
		frList = (ArrayList<FriendDTO>)Dc.getDB("friend");
	}

	public void addSc(ServerChat sc) {
		sList.add(sc);
	}
	
	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		if(msg.indexOf("login:")!=-1) {
			reLogin(msg);
		} else if(msg.indexOf("join:")!=-1) {
			reJoin(msg);
		} else if(msg.indexOf("idCheck:")!=-1) {
			idChk = idCheck(msg);
		} else if(msg.indexOf("setList:")!=-1) {
			
		}
	}

	private boolean idCheck(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":")+1, msg.length());
		
		if(id.length()>8) {
			nowSc.send("In eight");
		} else {
			boolean chk = true;
			if(mList!=null) {
				for(MemberDTO i : mList) {
					if(id.equals(i.getId())) {
						nowSc.send("Same Id");
						chk = false;
						return false;
					} else {
						chk = true;
					}
				}
			}
			if(chk==true) {
				nowSc.send("Possible Id");
				return true;
			}
		}
		return false;
	}

	private void reLogin(String msg) {
		// TODO Auto-generated method stub
		String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.length());
		
		MemberDTO m = new MemberDTO();
		m.setId(id);
		m.setPwd(pwd);
		
		if(Dc.select("member", m)) {
			nowSc.send("login true");
		} else {
			nowSc.send("login false");
		}
	}
	
	private void reJoin(String msg) {
		String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
		String phone = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
		
		if(idChk==true) {
			boolean chk = true;
			
			if(mList!=null) {
				for(MemberDTO i : mList) {
					if(id.equals(i.getId())) {
						nowSc.send("Same Id");
						chk = false;
						break;
					} else if(phone.equals(i.getPhone())) {
						nowSc.send("Same PhoneNumber");
						chk = false;
						break;
					} else {
						chk = true;
					}
				}
			}
			
			if(chk==true) {
				MemberDTO m = new MemberDTO();
				m.setId(id);
				m.setPwd(pwd);
				m.setPhone(phone);
				
				if(Dc.insert("member", m)) {
					nowSc.send("join true");
					getDBList();
				} else {
					nowSc.send("join false:DBSave");
				}
			}
		} else if(idChk==false) {
			nowSc.send("Wrong Id or Do not Id Check");
		}
		
	}

}
