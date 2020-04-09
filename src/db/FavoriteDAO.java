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
	

}
