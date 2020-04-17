package db;

public class DAOCenter {
	private DAOInterface Dif = null;
	
	private MemberDAO mDAO = null;
	private PostDAO pDAO = null;
	private FavoriteDAO fDAO = null;
	private FriendDAO frDAO = null;
	private DMRoomDAO dmRDAO = null;
	private DirectMessageDAO dmDAO = null;
	
	private static DAOCenter DAOCenter = null;
	
	private DAOCenter(){		
		mDAO = MemberDAO.getInstance();
		pDAO = PostDAO.getInstance();
		fDAO = FavoriteDAO.getInstance();
		frDAO = FriendDAO.getInstance();
		dmRDAO = DMRoomDAO.getInstance();
		dmDAO = DirectMessageDAO.getInstance();
	}
	
	public static DAOCenter getInstance() {
		if(DAOCenter==null) {
			DAOCenter = new DAOCenter();
		}
		return DAOCenter;
	}
	
	public boolean insert(String tName, Object DTO) {
		tableChk(tName);
		return Dif.insert(DTO);
	}
	
	public boolean select(String tName, Object DTO) {
		tableChk(tName);
		return Dif.select(DTO);
	}
	
	public Object select(String tName, String s) {
		tableChk(tName);
		return Dif.select(s);
	}
	
	public boolean update(String tName, Object DTO) {
		tableChk(tName);
		return Dif.update(DTO);
	}
	
	public boolean delete(String tName, Object DTO) {
		tableChk(tName);
		return Dif.delete(DTO);
	}
	
	public boolean delete(String tName, String s) {
		tableChk(tName);
		return Dif.delete(s);
	}
	
	public Object getDB(String tName){
		tableChk(tName);
		return Dif.getDBList(tName);
	}
	
	public Object getDB(String tName, String s){
		tableChk(tName);
		return Dif.getDBList(tName, s);
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
			case "dmroom" :
				Dif = dmRDAO;
				break;
			case "directmessage" :
				Dif = dmDAO;
		}
	}

}
