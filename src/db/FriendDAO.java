package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		try {
			FriendDTO f = (FriendDTO)DTO;
			String sql = "insert into friend values(?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getMyId());
			psmt.setString(2, f.getYourId());
			
			int a = psmt.executeUpdate();
			
			if(a>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean delete(Object DTO) {
		// TODO Auto-generated method stub
		try {
			FriendDTO f = (FriendDTO)DTO;
			String sql = "delete from friend where myid=? and yourid=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getMyId());
			psmt.setString(2, f.getYourId());
			
			int cnt = psmt.executeUpdate();
			
			if(cnt>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean select(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Object select(String s) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getDBList(String tName, String s) {
		ArrayList<Object> fList = new ArrayList<>();
		try {
			String sql = "select * from friend where myId=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				FriendDTO f = new FriendDTO();
					
				f.setMyId(rs.getString("myId"));
				f.setYourId(rs.getString("yourId"));
					
				fList.add(f);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return fList;
	}

}
