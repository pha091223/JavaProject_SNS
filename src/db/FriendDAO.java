package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FriendDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
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
	public ArrayList<FriendDTO> getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<FriendDTO> frList = new ArrayList<>();
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
		return frList;
	}

}
