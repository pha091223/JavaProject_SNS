package db;

import java.sql.Connection;

public class PostDAO implements DAOInterface {
	private static Connection con = null;
	
	private static PostDAO PostDAO = null;
	
	private PostDAO(){
		
	}
	
	public static PostDAO getInstance(Connection c) {
		con = c;
		if(PostDAO==null) {
			PostDAO = new PostDAO();
		}
		return PostDAO;
	}

}
