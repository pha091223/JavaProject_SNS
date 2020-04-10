package db;

import java.io.Serializable;

public class FriendDTO implements Serializable {
	private String myId = null;
	private String yourId = null;
	
	public String getMyId() {
		return myId;
	}
	
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	public String getYourId() {
		return yourId;
	}
	
	public void setYourId(String yourId) {
		this.yourId = yourId;
	}
	
	

}
