package db;

import java.io.Serializable;

public class FavoriteDTO implements Serializable {
	private String postNum = null;
	private String id = null;
	
	public String getPostNum() {
		return postNum;
	}
	
	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

}
