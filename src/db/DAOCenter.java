package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOCenter {
	private Connection con = null;
	
	private DAOInterface Dif = null;
	private MemberDAO mDAO = null;
	private PostDAO pDAO = null;
	private FavoriteDAO fDAO = null;
	private FriendDAO frDAO = null;
	
	private static DAOCenter DAOCenter = null;
	
	private DAOCenter(){
		connect();
		if(con!=null) {
			mDAO = MemberDAO.getInstance(con);
			pDAO = PostDAO.getInstance(con);
			fDAO = FavoriteDAO.getInstance(con);
			frDAO = FriendDAO.getInstance(con);
		} else if(con==null) {
			System.out.println("DB connect fail");
		}
	}
	
	public static DAOCenter getInstance() {
		if(DAOCenter==null) {
			DAOCenter = new DAOCenter();
		}
		return DAOCenter;
	}
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Class load fail :" + e.getMessage());
		}
	}
	
	private void connect() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", 
						"system", "11111111");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Class load fail : " + e.getMessage());
		}
	}
	
	public boolean insert(String tName, Object DTO) {
		tableChk(tName);
		return Dif.insert(DTO);
	}
	
	public boolean select(String tName, Object DTO) {
		tableChk(tName);
		return Dif.select(DTO);
	}
	
	private void tableChk(String tName) {
		switch(tName) {
		case "member" :
			Dif = mDAO;
			break;
		case "post" :
			Dif = pDAO;
			break;
		case "favorite" :
			Dif = fDAO;
			break;
		case "friend" :
			Dif = frDAO;
			break;
		}
	}

}
