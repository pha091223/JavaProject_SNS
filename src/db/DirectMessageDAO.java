package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirectMessageDAO implements DAOInterface {
	private Connection con = null;
	private ResultSet rs = null;
	
	private static DirectMessageDAO DMDAO = null;
	
	private DirectMessageDAO() {
		ConnectionCenter conC = new ConnectionCenter();
		con = conC.getConnection();
	}
	
	public static DirectMessageDAO getInstance() {
		if(DMDAO==null) {
			DMDAO = new DirectMessageDAO();
		}
		return DMDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
		try {
			DirectMessageDTO dm = (DirectMessageDTO)DTO;
			String sql = "insert into directmessage values(dmroom_no, sysdate, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dm.getId());
			psmt.setString(2, dm.getMessage());
			
			int cnt = psmt.executeUpdate();
			
			if(cnt==1) {
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
//		select * from (select * from directmessage where roomname=1 order by day) w
//		here rownum=1;
		
		return null;
	}

	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> dmList = new ArrayList<>();
		try {
			String sql = "select * from directmessage where roomname=? order by day desc";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				DirectMessageDTO dm = new DirectMessageDTO();
				
				dm.setRoomname(rs.getString("roomname"));
				dm.setDay(rs.getString("day"));
				dm.setId(rs.getString("id"));
				dm.setMessage(rs.getString("message"));
				
				dmList.add(dm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return dmList;
	}

}
