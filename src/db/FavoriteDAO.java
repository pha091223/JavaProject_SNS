package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteDAO implements DAOInterface {
	private static Connection con = null;
	private ResultSet rs = null;
	
	private static FavoriteDAO FavoriteDAO = null;
	
	private FavoriteDAO(){
		ConnectionCenter conC = new ConnectionCenter();
		con = conC.getConnection();
	}
	
	public static FavoriteDAO getInstance() {
		if(FavoriteDAO==null) {
			FavoriteDAO = new FavoriteDAO();
		}
		return FavoriteDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
		try {
			FavoriteDTO f = (FavoriteDTO)DTO;
			String sql = "insert into favorite values(?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getPostNum());
			psmt.setString(2, f.getId());
			
			int a = psmt.executeUpdate();
			
			if(a>0) {
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
		try {
			FavoriteDTO f = (FavoriteDTO)DTO;
			String sql = "delete from favorite where no=? and id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getPostNum());
			psmt.setString(2, f.getId());
			
			int cnt = psmt.executeUpdate();
			
			if(cnt>0) {
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
	public boolean delete(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean select(Object DTO) {
		// TODO Auto-generated method stub
		try {
			FavoriteDTO f = (FavoriteDTO)DTO;
			String sql = "select * from favorite where no=? and id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getPostNum());
			psmt.setString(2, f.getId());
			int cnt = psmt.executeUpdate();
			
			if(cnt==1) {
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
	public Object select(String s) {
		// TODO Auto-generated method stub
		String count = null;
		try {
			String sql = "select count(*) from favorite where no=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getString("count(*)");
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return count;
	}

	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> fList = new ArrayList<>();
		try {
			String sql = null;
			String keyword = null;
			
			if(s.contains("/u")) {
				sql = "select * from favorite where no=?";
				keyword = s.substring(0, s.indexOf("/"));
			} else {
				sql = "select * from favorite where id=?";
				keyword = s;
			}
			
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, keyword);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				FavoriteDTO f = new FavoriteDTO();
				
				f.setId(rs.getString("id"));
				f.setPostNum(rs.getString("no"));
				
				fList.add(f);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return fList;
	}
}
