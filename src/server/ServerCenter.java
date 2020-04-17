package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import db.DAOCenter;
import db.DirectMessageDTO;
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
		Dc = DAOCenter.getInstance();
	}

	public void addSc(ServerChat sc) {
		sList.add(sc);
	}
	
	public void delSc(ServerChat sc) {
		sList.remove(sc);
	}
	
	private void sendObject(Object o) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			
			os.writeObject(o);
			
			byte[] resultByte = bos.toByteArray();
			nowSc.sendDB(resultByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveClientMsg(String msg, ServerChat sc) {
		nowSc = sc;
		
		if(msg.indexOf("login:")!=-1) {
			login(msg);
		} else if(msg.indexOf("logout:")!=-1) {
			logout(msg);
		} else if(msg.indexOf("join:")!=-1) {
			join(msg);
		} else if(msg.indexOf("idCheck:")!=-1) {
			idChk = idCheck(msg);
		} else if(msg.indexOf("setList:")!=-1) {
			setList(msg);
		} else if(msg.indexOf("getList:")!=-1) {
			getList(msg);
		} else if(msg.indexOf("profile:")!=-1) {
			profile(msg);
		} else if(msg.indexOf("myPage:")!=-1) {
			myPage(msg);
		} else if(msg.indexOf("Follow:")!=-1) {
			followFriend(msg);
		} else if(msg.indexOf("Post:")!=-1) {
			post(msg);
		} else if(msg.indexOf("Favorite:")!=-1) {
			favorite(msg);
		} else if(msg.indexOf("DM:")!=-1) {
			DirectMessage(msg);
		}
	}
	
	private void DirectMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	private void favorite(String msg) {
		String id = msg.substring(msg.indexOf(":")+1, msg.indexOf("/"));
		String postNum = msg.substring(msg.indexOf("/")+1, msg.length());
		
		FavoriteDTO f = new FavoriteDTO();
		f.setId(id);
		f.setPostNum(postNum);
		
		if(msg.contains("addFavorite:")) {
			if(Dc.insert("favorite", f)) {
				nowSc.send("chkAddFavorite true");
			} else {
				nowSc.send("chkAddFavorite false");
			}
		} else if(msg.contains("delFavorite:")) {
			if(Dc.delete("favorite", f)) {
				nowSc.send("chkDelUnfavorite true");
			} else {
				nowSc.send("chkDelUnfavorite false");
			}
		} else if(msg.contains("chkFavorite:")) {
			if(Dc.select("favorite", f)) {
				nowSc.send("chkFavorite true");
			} else {
				nowSc.send("chkFavorite false");
			}
		} else if(msg.contains("countFavorite:")) {
			sendObject(Dc.select("favorite", postNum));
		}
	}
	
	private void post(String msg) {
		if(msg.contains("sharePost:")) {
			String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
			String id = reMsg.substring(0, reMsg.indexOf("/"));
			String post = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
			
			PostDTO p = new PostDTO();
			p.setId(id);
			p.setText(post);
			
			if(Dc.insert("post", p)) {
				nowSc.send("Write true");
			} else {
				if(post.length()>200) {
					nowSc.send("Write false : text length over 200");
				} else {
					nowSc.send("Write false : Please input text");					
				}
			}
		} else if(msg.contains("deletePost:")) {
			if(msg.indexOf("sure")!=-1) {
				String postNum = msg.substring(msg.indexOf("/")+1, msg.length());
				if(Dc.delete("post", postNum)){
					nowSc.send("Post Delete true");
				}
			} else {
				String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
				String id = reMsg.substring(0, reMsg.indexOf("/"));
				String postNum = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
				
				nowSc.send("Post Delete hope" + ":" + postNum);
			}
		} else if(msg.contains("selectPost:")) {
			String postNum = msg.substring(msg.lastIndexOf(":")+1, msg.length());
			sendObject(Dc.select("post", postNum));
		}
	}

	private void followFriend(String msg) {
		// TODO Auto-generated method stub
		String myId = msg.substring(msg.indexOf(":")+1, msg.indexOf("/"));
		String yourId = msg.substring(msg.indexOf("/")+1, msg.length());
		
		FriendDTO fr = new FriendDTO();
		fr.setMyId(myId);
		fr.setYourId(yourId);
		
		if(msg.contains("addFollow:")) {
			if(Dc.insert("friend", fr)) {
				nowSc.send("Follow true");
			} else {
				nowSc.send("Follow false");
			}			
		} else if(msg.contains("delFollow:")) {
			if(Dc.delete("friend", fr)) {
				nowSc.send("Unfollow true");
			} else {
				nowSc.send("Unfollow false");
			}
		} else if(msg.contains("chkFollow:")) {
			if(Dc.select("friend", fr)) {
				nowSc.send("chkFollow true");
			} else {
				nowSc.send("chkFollow false");
			}
		}
	}

	private void myPage(String msg) {
		// TODO Auto-generated method stub
		if(msg.indexOf("updatemyPage:")!=-1) {
			String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
			String id = reMsg.substring(0, reMsg.indexOf("/"));
			String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
			String phone = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
			
			if(phone.length()==0) {
				nowSc.send("Wrong PhoneNumber");
			} else {
				if(phone.length()>11 || phone.substring(0, 3).indexOf("01")==-1) {
					nowSc.send("Wrong PhoneNumber");
				} else {
					MemberDTO a = (MemberDTO)Dc.select("member", phone);
					
					if(a==null) {
						updateProfile_my(id, pwd, phone);
					} else {
						if(a.getId().equals(id)) {
							updateProfile_my(id, pwd, phone);
						} else {
							nowSc.send("Same PhoneNumber");
						}
					}
				}
			}
		} else if(msg.indexOf("deletemyPage:")!=-1){
			if(msg.indexOf("sure")!=-1) {
				if(Dc.delete("member", nowSc.getNowScId())) {
					for(ServerChat i : sList) {
						if(i.getNowScId().equals(nowSc.getNowScId())){
							delSc(i);
							break;
						}
					}
					nowSc.send("MyPage Delete true");
				}
			} else {
				nowSc.send("MyPage Delete hope");
			}
		} else {
			try {
				String id = msg.substring(msg.indexOf(":")+1, msg.length());
				sendObject(Dc.select("member", id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void updateProfile_my(String id, String pwd, String phone) {
		MemberDTO m = new MemberDTO();
		m.setId(id);
		m.setPwd(pwd);
		m.setPhone(phone);
		
		if(Dc.update("member", m)) {
			nowSc.send("MyPage Update true");
		} else {
			nowSc.send("MyPage Update false");
		}
	}

	private void profile(String msg) {
		// TODO Auto-generated method stub
		try {
			String id = msg.substring(msg.indexOf(":")+1, msg.length());
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			
			os.writeObject(Dc.select("member", id));
			
			byte[] resultByte = bos.toByteArray();
			nowSc.sendDB(resultByte);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 조건 없이 table의 tuple 모두 가져오기
	private void setList(String msg) {
		// TODO Auto-generated method stub
		String tName = null;
		String id = null;
		
		tName = msg.substring(msg.indexOf(":")+1, msg.lastIndexOf("/"));
		id = msg.substring(msg.indexOf("/")+1, msg.length());
		
		if(nowSc.getNowScId().equals(msg.substring(msg.lastIndexOf("/")+1, msg.length()))){
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos);
				
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
				
				byte[] resultByte = bos.toByteArray();
				nowSc.sendDB(resultByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 조건 있이 table에서 맞는 tuple만 가져오기
	private void getList(String msg) {
		// TODO Auto-generated method stub
		String tName = null;
		String keyword = null;
		
		tName = msg.substring(msg.indexOf(":")+1, msg.indexOf("/"));
		keyword = msg.substring(msg.indexOf("/")+1, msg.length());
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			
			switch(tName) {
				case "member" :
					os.writeObject(Dc.getDB("member", keyword));
					break;
				case "post" :
					os.writeObject(Dc.getDB("post", keyword));
					break;
				case "favorite" :
					os.writeObject(Dc.getDB("favorite", keyword));
					break;
				case "friend" :
					os.writeObject(Dc.getDB("friend", keyword));
					break;
				case "dmroom" :
					os.writeObject(Dc.getDB("dmroom", keyword));
					break;
				case "directmessage" :	
					os.writeObject(Dc.getDB("directmessage", keyword));
			}
			
			byte[] resultByte = bos.toByteArray();
			nowSc.sendDB(resultByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean idCheck(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":")+1, msg.length());
		
		if(id.length()>8) {
			nowSc.send("In eight");
		} else if(id.length()==0) {
			nowSc.send("Please input ID");
		} else {
			MemberDTO m = new MemberDTO();
			m.setId(id);
			
			if(Dc.select("member", m)) {
				nowSc.send("Same Id");
			} else {
				nowSc.send("Possible Id");
				return true;
			}
		}
		return false;
	}

	private void logout(String msg) {
		// TODO Auto-generated method stub
		String id = msg.substring(msg.indexOf(":")+1, msg.length());
		
		if(msg.indexOf("sure")!=-1) {
			nowSc.send("Logout true");
			delSc(nowSc);
		} else {
			nowSc.send("Logout hope");
		}
	}
	
	private void login(String msg) {
		// TODO Auto-generated method stub
		String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.length());
		
		// 다중 접속 방어
		boolean chk = true;
		
		if(sList.size()>1) {
			for(ServerChat i : sList) {
				if(i.getNowScId()!=null && i.getNowScId().equals(id)) {
					nowSc.send("Already access member");
					chk = false;
					break;
				}
			}
		}
		
		if(chk==true) {
			MemberDTO m = new MemberDTO();
			m.setId(id);
			m.setPwd(pwd);
			
			if(Dc.select("member", m)) {
				nowSc.send("Login true/Object port:" + nowSc.getObjectPortNum());
			} else {
				nowSc.send("Login false");
			}
		}
	}
	
	private void join(String msg) {
		String reMsg = msg.substring(msg.indexOf(":")+1, msg.length());
		String id = reMsg.substring(0, reMsg.indexOf("/"));
		String pwd = reMsg.substring(reMsg.indexOf("/")+1, reMsg.lastIndexOf("/"));
		String phone = reMsg.substring(reMsg.lastIndexOf("/")+1, reMsg.length());
		
		if(idChk==true) {
			if(phone.length()>11) {
				nowSc.send("Wrong PhoneNumber");
			} else {
				if(phone!=null && phone.substring(0, 3).indexOf("01")==-1) {
					nowSc.send("Wrong PhoneNumber");
				} else {
					MemberDTO m = new MemberDTO();
					m.setId(id);
					m.setPwd(pwd);
					m.setPhone(phone);
					
					if(Dc.select("member", m)) {
						if(Dc.insert("member", m)) {
							nowSc.send("Join true");
						} else {
							nowSc.send("Join false");
						}
					} else {
						nowSc.send("Same PhoneNumber");
					}
				}
			}
		} else if(idChk==false) {
			nowSc.send("Wrong Id or Do not Id Check");
		}
	}

}
