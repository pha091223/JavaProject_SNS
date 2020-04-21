package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		try {
			DMRoomDTO dmR = (DMRoomDTO)DTO;
			
			String sql = null;
			PreparedStatement psmt = null;
			
			if(dmR.getRoomname()==null) {
//				sql = "insert into dmroom values(dmroom_no.nextval, ?)";
				
				// Test
				sql = "insert into dmroom values(1, ?)";
				//
				
				psmt = con.prepareStatement(sql);
				psmt.setString(1, dmR.getId());
			} else {
				sql = "insert into dmroom values(?, ?)";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, dmR.getRoomname());
				psmt.setString(2, dmR.getId());
			}
			
			int count = psmt.executeUpdate();
			
			if(count==1) {
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
		// (상황은 1:1을 가정)
		// DM 사용자간에 DM 방의 존재 여부를 판별
		// 1) 존재하지 않으면 null값 할당 > insert문을 통해 sequence 방 번호를 할당하여 DM 방 생성
		// 		> 생성된 방 번호를 알기 위해 바로 select문을 돌려 가장 최근에 만들어진 DM 방의 번호를 받아옴(내림차순 정렬, rownum=1)
		// 		> 받은 방 번호로 상대방을 방 안에 참가시키기 위한 insert문 수행
		// 2) 존재한다면 이미 만들어져있는 DM방의 번호 할당 > return 받은 방 번호로 바로 Server에서 send 수행
		
		// 처음 방 여부를 체크하는 select문
		if(s.contains("/")) {
			String myId = s.substring(0, s.indexOf("/"));
			String yourId = s.substring(s.indexOf("/")+1, s.length());
			
			String sql = null;
			PreparedStatement psmt = null;
			
			try {			
				sql = "create view dmroom_1 as select * from dmroom where id='" + myId + "'";
				Statement st = con.createStatement();
				st.executeUpdate(sql);
				
				sql = "create view dmroom_2 as select * from dmroom where id='" + yourId + "'";
				st = con.createStatement();
				st.executeUpdate(sql);
				
				sql = "select dmroom_1.roomname from dmroom_1, dmroom_2 "
						+ "where dmroom_1.roomname=dmroom_2.roomname";
				psmt = con.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				String roomName = null;
				
				while(rs.next()) {
					roomName = rs.getString("roomname");
				}
				
				sql = "drop view dmroom_1";
				st = con.createStatement();
				st.executeUpdate(sql);
				
				sql = "drop view dmroom_2";
				st = con.createStatement();
				st.executeUpdate(sql);
				
				return roomName;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// 방 여부 체크 후, 생성하였다면 바로 그 생성된 방 번호를 받기 위한 select문
			try {
				String sql = "select * from (select * from dmroom where id=? order by roomname desc)"
						+ "where rownum=1";
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, s);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					DMRoomDTO dmR = new DMRoomDTO();
					dmR.setRoomname(rs.getString("roomname"));
					dmR.setId(rs.getString("id"));
					
					return dmR;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
			String sql = null;
			PreparedStatement psmt = null;
			
			if(s.contains("/r")) {
				sql = "select * from dmroom where roomname=?";
				String roomN = s.substring(0, s.indexOf("/"));
				psmt = con.prepareStatement(sql);
				psmt.setString(1, roomN);
			} else {
				sql = "select * from dmroom where id=?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, s);
			}
			
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
