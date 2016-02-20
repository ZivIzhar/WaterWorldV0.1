
public class WaterWorld {

	DB<Guardian> guardiansDB;
	DB<Child> childDB;
	DB<Ride> rideDB;
	int currentPass; //the next free pass we can give a signed up guardian
	int currentChildID; //the next free id we can give a signed up child
	int currentGuardianID; //the next free id we can give to a signed up guardian
	int loggedInGuardianID; //-1 if no guardian is logged in, otherwise his id
	
	public WaterWorld(DB<Child> childdb,DB<Ride> ridedb) {
		this.childDB = childdb;
		this.rideDB = ridedb;
		this.guardiansDB = new DB<Guardian>();
		this.currentPass=0;
		this.currentChildID=0;
		this.currentGuardianID=0;
		this.loggedInGuardianID=-1;
	}
	
	
	/*
	 * Function adds a delta to a child's tickets number
	 * @param childID - the id of the child to change num of tickets
	 * @param rideID - obvious
	 * @param delta - the amount of ticket to add (may be negative)
	 * @excpetion IllegalArgumentException - bad parameters
	 */
	void changeNumTicketsOfChild(int childID,int rideID,int delta) {
		if(childID<0 || rideID<0) {
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
		}
	}
	
	//Function is implemented in Inspector
	/*
	void setRideStatus(int rideID,boolean turnOn,int capacity,int minHeight,int minAge) {	
	}*/
	
	//Function is implemented in Inspector
	/*
	boolean addRide(Ride newRide) {
		return true;
	}*/
	
	/*
	 * Function adds a new child to an existing guardian. it's supposed to be called after
	 * guardian is logged in, so the loggedInguardianID is valid.
	 * @param childHeight - the height of the new child
	 * @param childAge - the age of the new child
	 * @return the unique if of the new child
	 */
	int addNewChild(int guardianID,int childHeight,int childAge) {
		if(childHeight <= 0 || childAge <= 0) {
			//ERROR - illegalparameters
			System.out.println("Error");
			return -1;
		}
		Guardian guardian = guardiansDB.getElement(this.loggedInGuardianID);
		//There's such guardian for sure, because this function is called after guardian login
		Child child = new Child(currentChildID,childAge,childHeight,guardian);
		childDB.addElement(child);
		guardian.addChild(currentChildID);
		currentChildID++;
		
		return currentChildID - 1;
	}
	
	/*
	 * Function creates a new guardian user. it allocates an unique id for the guardian,
	 * and the guardian has to remember it, because the next login will be with this id.
	 * @param name - the name of the new guardian 
	 */
	void createNewGuardian(String name) {
		Guardian guardian = new Guardian(name,currentPass,childDB,currentGuardianID);
		guardiansDB.addElement(guardian);
		//OUTPUT
		System.out.println("Your id is" + currentGuardianID + " Your password: " + currentPass);
		currentPass++;
		currentGuardianID++;
	}
	
	/*
	 * Function makes the child use the ride
	 */
	void childUseRide(int childID,int rideID) {
		Child child = childDB.getElement(childID);
		Ride ride = rideDB.getElement(rideID);
		if(child==null || ride==null) {
			//ERROR bad parameters
			System.out.println("error");
		}
		boolean regularTicket;
		if(ride instanceof RegularRide) {
			regularTicket=true;
		}
		else {
			regularTicket=false;
		}
		if(ride.childCanEnterLegally(child)==true && child.hasTicket(regularTicket, rideID)==true) {
			//child can use this ride
			ride.childEnters(child);
			try {
				child.useRide(rideID, regularTicket);
			} catch (BadTicketNumException e) {
				//will not come here, we checked that child has valid ticket
			}
		}
		else { 
			//child can enter this ride
			System.out.println("Child can't enter");
		}
	}
	
	/*
	 * Function takes care of the login of a guardian.
	 * in the gui, the guardian can use it's function without reentering his id because
	 * we save his id in loggedInGuardianID
	 * @param guardianID - obvious
	 * @param password - obvious
	 * @reurn true iff login successful
	 */
	boolean guardianLogin(int guardianID,int password) {
		Guardian guardian = guardiansDB.getElement(guardianID);
		if(guardian==null) {
			//illegal guardian ID
			return false;
		}
		if(guardian.getPassword()==password) {
			this.loggedInGuardianID=guardianID;
			return true;
		}
		return false;
	}
	
	
	
}
