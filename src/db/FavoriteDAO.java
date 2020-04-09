package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FavoriteDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private static FavoriteDAO FavoriteDAO = null;
	
	private FavoriteDAO(){
		
	}
	
	public static FavoriteDAO getInstance(Connection c) {
		con = c;
		if(FavoriteDAO==null) {
			FavoriteDAO = new FavoriteDAO();
		}
		return FavoriteDAO;
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
	public ArrayList<FavoriteDTO> getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<FavoriteDTO> fList = new ArrayList<>();
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
