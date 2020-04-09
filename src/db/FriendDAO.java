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
			psmt.setString(2, f.getyourId());
			
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
	public boolean select(Object DTO) {
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
			String sql = "select * from friend where myId = ?";
			stmt = con.prepareStatement(sql);
			if (stmt!=null) {
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					MemberDTO m = new MemberDTO();
					
					m.setId(rs.getString("id"));
					m.setPwd(rs.getString("pwd"));
					m.setPhone(rs.getString("phone"));
					
					fList.add(m);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return fList;
	}

	@Override
	public Object select(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String s) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
