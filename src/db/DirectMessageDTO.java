package db;

import java.io.Serializable;

public class DirectMessageDTO implements Serializable {
	private String roomname;
	private String day;
	private String id;
	private String message;
	
	public String getRoomname() {
		return roomname;
	}
	
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String msg) {
		message = msg;
	}
	
}
