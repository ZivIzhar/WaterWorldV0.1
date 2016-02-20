import java.util.ArrayList;
import java.util.List;


public class Inspector {

	List<Integer> allMyRides;
	DB<Ride> db;
	int nextAvailableRideID;
	Inspector(DB<Ride> db,int nextAvailableRideID) {
		this.db = db;
		allMyRides = new ArrayList<Integer>();
		this.nextAvailableRideID=nextAvailableRideID;
	}
	
	/*
	 * Function changes the status of the ride "rideID"
	 * @param capacity - the new capacity
	 * @param minHeight - the new min height
	 * @param minAge 0 the new min age
	 * @throws IllegalArgumentException - if bad parameters or ride doesn't exists
	 */
	void setRideStatus(int rideID,int capacity,double minHeight,int minAge) {
		if(capacity <= 0 || minHeight <= 0 || minAge <= 0 || rideID < 0) {
			//ERROR bad arguments
			throw new IllegalArgumentException();
		}
		Ride rideToChange = db.getElement(rideID);
		if(rideToChange==null) {
			//ERROR no such ride
			throw new IllegalArgumentException();
		}
		rideToChange.setCapacity(capacity);
		rideToChange.setMinAge(minAge);
		rideToChange.setMinHeight(minHeight);
	}
	
	/*
	 * Function adds a new ride to db.
	 * @param minAge - the min age
	 * @param minHeight - the minheight
	 * @param capacity - obvious
	 * @param regularRide - true if it's a regular ride, false if it's extreme
	 * @exception IllegalArgumentExcpetion - obvious
	 */
	void addNewRide(int minAge,double minHeight,int capacity,boolean regularRide) {
		if(minAge<=0 || minHeight<=0 || capacity<=0) {
			//ERROR bad parameters
			throw new IllegalArgumentException();
		}
		Ride newRide;
		if(regularRide) {
			newRide = new RegularRide(nextAvailableRideID,capacity,minHeight,minAge);
		}
		else {
			newRide = new ExtremeRide(nextAvailableRideID,capacity,minHeight,minAge);
		}
		db.addElement(newRide);
		allMyRides.add(nextAvailableRideID);
		nextAvailableRideID++;
	}

	/*
	 * Function presents the statistics about the sales to the inspector
	 */
	void presentStats() {
		for(Integer id: allMyRides) {
			Ride current = db.getElement(id);
			int sales = current.getNumOfPurchasedTickets();
			//OUTPUT ("RideID : Sales")
			System.out.println("Ride " + id + " Sold:" + sales);
		}
	}
	
	
}
