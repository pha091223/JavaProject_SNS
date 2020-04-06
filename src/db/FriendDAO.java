package db;

import java.sql.Connection;

public class FriendDAO implements DAOInterface {
	private static Connection con = null;
	
	private static FriendDAO FriendDAO = null;
	
	private FriendDAO(){
		
	}
	
	public static FriendDAO getInstance(Connection c) {
		con = c;
		if(FriendDAO==null) {
			FriendDAO = new FriendDAO();
		}
		return FriendDAO;
	}

}
