package db;

public interface DAOInterface {
	
	boolean insert(Object DTO);
	
	boolean update(Object DTO);
	
	boolean delete(Object DTO);
	
	boolean delete(String s);
	
	boolean select(Object DTO);
	
	// table마다 조건에 맞는 tuple 한 개만 갖고오기
	Object select(String s);
	
	// table 전체 tuple 갖고오기
	Object getDBList(String tName);
	
	// table에서 조건에 맞는 일부 tuple 갖고오기
	Object getDBList(String tName, String s);

}
