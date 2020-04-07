package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
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
