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

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean select(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<PostDTO> getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<PostDTO> pList = new ArrayList<>();
		try {
			String sql = "select * from post";
			stmt = con.prepareStatement(sql);
			if (stmt!=null) {
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return pList;
	}

}
