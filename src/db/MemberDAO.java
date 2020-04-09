package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		try {
			MemberDTO m = (MemberDTO)DTO;
			if(m.getPhone()==null) {
				String sqlId = "select * from member where id=?";
				PreparedStatement psmt = con.prepareStatement(sqlId);
				
				psmt.setString(1, m.getId());
				int cnt = psmt.executeUpdate();
				
				if(m.getPwd()!=null) {
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
				} else if(m.getPwd()==null) {
					if(cnt>0) {
						return true;						
					}
				}
			} else if(m.getPhone()!=null) {
				String sqlPhone = "select * from member where phone=?";
				PreparedStatement psmt = con.prepareStatement(sqlPhone);
				
				psmt.setString(1, m.getPhone());
				int cnt = psmt.executeUpdate();
				
				if(cnt==0) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return false;
	}

	@Override
	public Object select(String s) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from member where id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO m = new MemberDTO();
				
				m.setId(rs.getString("id"));
				m.setPwd(rs.getString("pwd"));
				m.setPhone(rs.getString("phone"));
				
				return m;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return null;
	}
	
	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<Object> mList = new ArrayList<>();
		try {
			String sql = "select * from member";
			stmt = con.prepareStatement(sql);
			if (stmt!=null) {
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					MemberDTO m = new MemberDTO();
					
					m.setId(rs.getString("id"));
					m.setPwd(rs.getString("pwd"));
					m.setPhone(rs.getString("phone"));
					
					mList.add(m);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return mList;
	}
	
	@Override
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object DTO) {
		// TODO Auto-generated method stub
		try {
			MemberDTO m = (MemberDTO)DTO;
			
			String sql = "update member set pwd=?, phone=? where id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, m.getPwd());
			psmt.setString(2, m.getPhone());
			psmt.setString(3, m.getId());
			
			int cnt = psmt.executeUpdate();
			
			if(cnt==1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		// TODO Auto-generated method stub
		try {
			String sql = "delete from member where id=?";
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


}
