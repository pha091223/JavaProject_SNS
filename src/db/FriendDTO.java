package db;

import java.io.Serializable;

public class FriendDTO implements Serializable{
	private String myId = null;
	private String yourId = null;
	
	public String getMyId() {
		return myId;
	}
	
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	public String getyourId() {
		return yourId;
	}
	
	public void setyourId(String yourId) {
		this.yourId = yourId;
	}
	
	

}
