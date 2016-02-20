import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public abstract class Ride implements IDable {

	int rideID;
	boolean status; //true - standby, false - working
	int capacity;
	double minHeight;
	int minAge;
	int childrenCount;
	Timer myTimer;
	private final int delay = 5*60*1000; //5 minutes
	List<Child> myChildren;
	int numOfPurchasedTickets;
	
	public Ride(int rideID,int capacity,double minHeight,int minAge) {
		this.rideID = rideID;
		this.capacity = capacity;
		this.minHeight = minHeight;
		this.minAge = minAge;
		this.childrenCount = 0;
		this.status = true;
		this.myTimer = new Timer();
		myTimer.schedule(new RideTimerTask(this),this.delay);
		this.myChildren = new ArrayList<Child>();
		this.numOfPurchasedTickets=0;
	}
	
	/*
	 * Function returns true if child can enter this ride according to 2,3 conditions,
	 * i.e child has proper age and height and the ride is in standby.
	 * @param child - the child to check
	 * @returns true if child can enter
	 */
	boolean childCanEnterLegally(Child child) {
		if(child.getAge() >= minAge && child.getHeight() >= minHeight && status &&
				childrenCount < capacity) {
			return true;
		}
		return false;
	}
	
	/*
	 * Function used in case of a child entering this ride, returns true if he enterd successfully
	 * @param child - the child that wants to enter
	 * @returns true if child entered gracefully
	 */
	boolean childEnters(Child child) {
		if(childCanEnterLegally(child)==false) {
			return false;
		}
		childrenCount++;
		myChildren.add(child);
		this.numOfPurchasedTickets++;
		if(childrenCount==capacity) {
			//ride is now active!
			activateRide();
		}
		return true;
		
	}
	
	/*
	 * Function is called when the ride becomes active. it makes the ride work, and clears
	 * all previous data of this ride
	 */
	void activateRide() {
		//RIDE NOW WORKS
		this.status = false;
		this.myTimer.cancel();
		//very quick ride, it finished immediately, perhaps TODO: delay for a few seconds 
		
		//RIDE FINISHED WORKING
		this.childrenCount=0;
		for(Child c: myChildren) {
			c.getOffRide();
		}
		this.myChildren = new ArrayList<Child>();
		this.status = true; //ride is now standby
		this.myTimer = new Timer();
		myTimer.schedule(new RideTimerTask(this),this.delay);
	}
	
	public int getID() {
		return rideID;
	}
	int getCapacity() {
		return capacity;
	}
	int getMinAge() {
		return minAge;
	}
	double getMinHeight() {
		return minHeight;
	}
	int getChildrenCount() {
		return childrenCount;
	}
	void setCapacity(int newCapacity) {
		this.capacity = newCapacity;
	}
	void setMinHeight(double newHeight) {
		this.minHeight=newHeight;
	}
	void setMinAge(int newAge) {
		this.minAge=newAge;
	}
	int getNumOfPurchasedTickets() {
		return this.numOfPurchasedTickets;
	}
	
}
