package db;

import java.io.Serializable;

public class DMRoomDTO implements Serializable {
	private String roomname = null;
	private String id = null;
	
	public String getRoomname() {
		return roomname;
	}
	
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
