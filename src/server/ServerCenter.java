package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	
	private boolean idChk = false;
	
	ServerCenter(){
//		Dc = DAOCenter.getInstance();
	}

	public void addSc(ServerChat sc) {
		sList.add(sc);
	}
	
	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		if(msg.indexOf("login:")!=-1) {
			Login(msg);
		} else if(msg.indexOf("join:")!=-1) {
			Join(msg);
		} else if(msg.indexOf("idCheck:")!=-1) {
			idChk = idCheck(msg);
		} else if(msg.indexOf("setList:")!=-1) {
			setList(msg);
		} else if(msg.indexOf("myPage:")!=-1) {
			viewMyPage(msg);
		}
	}

	private void viewMyPage(String msg) {
		// TODO Auto-generated method stub
		if(nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf(":")+1, msg.length()))){
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				
				os.writeObject(Dc.select("member", nowSc.getNowScId()));
				
				byte[] resultByte = bos.toByteArray();
				nowSc.sendList(resultByte);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setList(String msg) {
		// TODO Auto-generated method stub
		String tName = msg.substring(msg.indexOf(":")+1, msg.lastIndexOf("/"));
		if(nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf("/")+1, msg.length()))){
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				
<<<<<<< HEAD
				switch(tName) {
					case "member" :
						os.writeObject(Dc.getDB("member"));
						break;
					case "post" :
						os.writeObject(Dc.getDB("post"));
						break;
					case "favorite" :
						os.writeObject(Dc.getDB("favorite"));
						break;
					case "friend" :
						os.writeObject(Dc.getDB("friend"));
						break;
				}
=======
				ArrayList<MemberDTO> mList = new ArrayList<>();
				os.writeObject(mList);
				
//				switch(tName) {
//					case "member" :
//						os.writeObject(Dc.getDB("member"));
//						break;
//					case "post" :
//						os.writeObject(Dc.getDB("post"));
//						break;
//					case "favorite" :
//						os.writeObject(Dc.getDB("favorite"));
//						break;
//					case "friend" :
//						os.writeObject(Dc.getDB("friend"));
//						break;
//				}
>>>>>>> refs/remotes/origin/master
				
				byte[] resultByte = bos.toByteArray();
				nowSc.sendList(resultByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean idCheck(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":")+1, msg.length());
		
		if(id.length()>8) {
			nowSc.send("In eight");
		} else {
			MemberDTO m = new MemberDTO();
			m.setId(id);
			
			if(Dc.select("member", m)) {
				nowSc.send("Same Id");
			} else {
				nowSc.send("Possible Id");
				return true;
			}
<<<<<<< HEAD
=======
			
>>>>>>> refs/remotes/origin/master
		}
		return false;
	}

	private void Login(String msg) {
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
		
		nowSc.send("login true");
	}
	
	private void Join(String msg) {
		String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
		String phone = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
		
		if(idChk==true) {
			MemberDTO m = new MemberDTO();
			m.setId(id);
			m.setPwd(pwd);
			m.setPhone(phone);
			
			if(Dc.select("member", m)) {
				if(Dc.insert("member", m)) {
					nowSc.send("join true");
				} else {
					nowSc.send("join false:DBSave");
				}
			} else {
				nowSc.send("Same PhoneNumber");
			}
		} else if(idChk==false) {
			nowSc.send("Wrong Id or Do not Id Check");
		}
	}

}
