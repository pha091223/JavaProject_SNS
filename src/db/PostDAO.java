package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		ConnectionCenter conC = new ConnectionCenter();
		con = conC.getConnection();
	}
	
	public static PostDAO getInstance() {
		if(PostDAO==null) {
			PostDAO = new PostDAO();
		}
		return PostDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
		try {
			PostDTO p = (PostDTO)DTO;
			String sql = "insert into post values(post_no.nextval, sysdate, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, p.getId());
			psmt.setString(2, p.getText());

			int a = psmt.executeUpdate();

			if(a==1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
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
		try {
			String sql = "delete from post where no=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			
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
	public boolean select(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object select(String s) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from post where no=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				PostDTO p = new PostDTO();
				
				p.setNo(rs.getString("no"));
				p.setDay(rs.getString("day"));
				p.setId(rs.getString("id"));
				p.setText(rs.getString("text"));
				
				return p;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<Object> pList = new ArrayList<>();
		try {
			String sql = "select * from post order by day desc";
			stmt = con.prepareStatement(sql);
			
			if(stmt!=null) {
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					PostDTO p = new PostDTO();
					
					p.setNo(rs.getString("no"));
					p.setDay(rs.getString("day"));
					p.setId(rs.getString("id"));
					p.setText(rs.getString("text"));
					
					pList.add(p);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return pList;
	}

	@Override
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> pList = new ArrayList<>();
		try {
			if(s.contains("/t")) {
				String id = s.substring(0, s.indexOf("/"));
				String sql = "select * from post where id=? or "
						+ "id=(select yourid from friend where myid=?) order by day desc";
				
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, id);
				psmt.setString(2, id);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					PostDTO p = new PostDTO();
					
					p.setNo(rs.getString("no"));
					p.setDay(rs.getString("day"));
					p.setId(rs.getString("id"));
					p.setText(rs.getString("text"));
					
					pList.add(p);
				}
			} else {				
				String sql = "select * from post where id=?";
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, s);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					PostDTO p = new PostDTO();
					
					p.setNo(rs.getString("no"));
					p.setDay(rs.getString("day"));
					p.setId(rs.getString("id"));
					p.setText(rs.getString("text"));
					
					pList.add(p);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return pList;
	}

}
