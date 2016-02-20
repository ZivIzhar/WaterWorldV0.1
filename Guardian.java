import java.util.ArrayList;
import java.util.List;


public class Guardian implements IDable{
	List<Integer> allMySons;
	int password;
	String name;
	DB<Child> childDB;
	int id;
	
	public Guardian(String name,int password,DB<Child> childdb,int id) {
		allMySons = new ArrayList<Integer>();
		this.name = name;
		this.password = password;	
		this.childDB=childdb;
		this.id = id;
	}
	
	/*
	 * Function checks if a child is my child
	 * @param childID - the id of the child to check
	 * @return true iff it's my child
	 */
	boolean isMyChild(int childID) {
		for(Integer child: allMySons) {
			if(child == childID)
				return true;
		}
		return false;
	}
	
	/*
	 * Function adds a new child to this guardian.
	 * @param childID the id of the new child
	 * @return true if child added successfuly, false otherwise
	 */
	boolean addChild(int childID) {
		if(isMyChild(childID) == true) {
			return false;
		}
		allMySons.add(childID);
		return true;
	}
	
	/*
	 * WaterWorld does it!
	 * Function adds a delta to a child's tickets number
	 * @param childID - the id of the child to change num of tickets
	 * @param rideID - obvious
	 * @param delta - the amount of ticket to add (may be negative)
	 * @excpetion IllegalArgumentException - bad parameters
	 */
	void changeNumTicketsOfChild(int childID,int rideID,int delta) {
		//WaterWorld does it.
		/*if(childID<0 || rideID<0) {
			throw new IllegalArgumentException();
		}
		Child myChild = childDB.getElement(childID);
		Ride ride = rideDB.getElement(rideID);
		if(ride==null) {
			//ERROR no such ride
			System.out.println("error");
			return;
		}
		boolean regularRide;
		if(ride instanceof RegularRide) {
			regularRide=true;
		}
		else {
			regularRide = false;
		}
		try {
			myChild.changeNumOfTickets(regularRide, rideID, delta);
		}
		catch(BadTicketNumException e) {
			//ERROR in delta
			System.out.println("error");
		}*/
	}
	
	List<Integer> getMySons() {
		return allMySons;
	}
	
	public int getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public int getPassword() {
		return this.password;
	}
}
