import java.util.ArrayList;
import java.util.List;


public class DB<T extends IDable> {
	List<T> db;
	public DB() {
		this.db = new ArrayList<T>();
	}
	
	/*
	 * Function adds a new element to the db
	 * @param element - the new element
	 * @throws IllegalArgumentException if element already exists
	 */
	void addElement(T element) {
		if(getElement(element.getID()) != null) {
			throw new IllegalArgumentException();
		}
		db.add(element);
	}
	
	/*
	 * Function returns the proper object which belongs to this ID
	 * @param ID - the id of the object
	 * @returns - the proper object or null if there is no such object
	 */
	T getElement(int ID) {
		for(T t: db) {
			if(ID == t.getID()) {
				return t;
			}
		}
		return null;
	}
	
	
	
}
