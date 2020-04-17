package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCenter {
	private Connection con = null;
	
	ConnectionCenter(){
		connect();
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
	
	public Connection getConnection() {
		return con;
	}

}
