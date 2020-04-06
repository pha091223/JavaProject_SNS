package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private static MemberDAO MemberDAO = null;
	
	private MemberDAO(){
		
	}
	
	public static MemberDAO getInstance(Connection c) {
		con = c;
		if(MemberDAO==null) {
			MemberDAO = new MemberDAO();
		}
		return MemberDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		// TODO Auto-generated method stub
		try {
			MemberDTO m = (MemberDTO)DTO;
			String sql = "insert into member values(?, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, m.getId());
			psmt.setString(2, m.getPwd());
			psmt.setString(3, m.getPhone());
			
			int a = psmt.executeUpdate();
			
			if(a>0) {
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
		int cnt = 0;
		try {
			MemberDTO m = (MemberDTO)DTO;
			if(m.getPhone()==null) {
				String sqlId = "select * from member where id=?";
				PreparedStatement psmt = con.prepareStatement(sqlId);
				
				psmt.setString(1, m.getId());
				cnt = psmt.executeUpdate();
				
				if(cnt==1) {
					String sqlPwd = "select * from member where id=? and pwd=?";
					psmt = con.prepareStatement(sqlPwd);
					psmt.setString(1, m.getId());
					psmt.setString(2, m.getPwd());
					cnt = cnt + psmt.executeUpdate();
					if(cnt>1) {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
