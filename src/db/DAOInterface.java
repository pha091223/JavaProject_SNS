package db;

public interface DAOInterface {
	
	boolean insert(Object DTO);
	
	boolean select(Object DTO);
	
	boolean update(Object DTO);
	
	boolean delete(String s);
	
	Object select(String s);
	
	Object getDBList(String tName);

}
