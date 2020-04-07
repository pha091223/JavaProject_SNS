package db;

public interface DAOInterface {
	
	boolean insert(Object DTO);
	
	boolean select(Object DTO);
	
	Object getDBList(String tName);

}
