package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DMRoomDAO implements DAOInterface {
	private static Connection con = null;
	private ResultSet rs = null;
	
	private static DMRoomDAO DMDAO = null;
	
	private DMRoomDAO() {
		ConnectionCenter conC = new ConnectionCenter();
		con = conC.getConnection();
	}
	
	public static DMRoomDAO getInstance() {
		if(DMDAO==null) {
			DMDAO = new DMRoomDAO();
		}
		return DMDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
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
		ArrayList<Object> dmRList = new ArrayList<>();
		try {
			String sql = "select * from dmroom where id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				DMRoomDTO dmR = new DMRoomDTO();
				
				dmR.setRoomname(rs.getString("roomname"));
				dmR.setId(rs.getString("id"));
				
				dmRList.add(dmR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return dmRList;
	}

}
