package db;

public interface DAOInterface {
	
	boolean insert(Object DTO);
	
	boolean select(Object DTO);
	
	Object select(String s);
	
	Object getDBList(String tName);

}
