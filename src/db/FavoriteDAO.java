package db;

import java.sql.Connection;

public class FavoriteDAO implements DAOInterface {
	private static Connection con = null;
	
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
