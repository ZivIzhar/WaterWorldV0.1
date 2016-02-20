import java.util.ArrayList;
import java.util.List;

public class Child implements IDable {

	int id;
	int age;
	double height;
	int rideLocation; //rideID of the child's current ride, -1 if child is not in a ride
	Guardian myGuardian;
	/*
	 * list of all tickets, each node in the list represents one kind of a ticket.
	 * The first element represents the regular ride tickets and it's created in 
	 * the constructor. the rest of the elements represent each of the extreme rides,
	 * these elements are created upon the first purchase of a specific extreme ticket.
	 */
	List<Ticket> tickets;
	
	
	public Child(int id,int age,double height,Guardian myGuardian) {
		this.id = id;
		this.age = age;
		this.height = height;
		this.rideLocation = -1;
		this.tickets = new ArrayList<Ticket>();
		this.tickets.add(new Ticket(true,-1)); //add regular tickets to the list
		this.myGuardian=myGuardian;
	}
	
	
	private Ticket getTicket(int rideID) {
		for(Ticket t: tickets) {
			if(t.getRideID() == rideID) {
				return t;
			}
		}
		return null;
	}
	
	/*
	 * This function checks if a child has a valid ticket for a ride. if regularTicket
	 * is true then the function will return true iff a child has a valid ticket for a
	 * regular ride (first element of the list). otherwise the function will return true
	 * iff the child has a ticket for a ride with rideID.
	 * @param regularTicket  - true if this is a regular ride, false otherwise
	 * @param rideID - used only when regularTicket is false.
	 * @return true if child has valid ticket, false otherwise
	 */
	boolean hasTicket(boolean regularTicket,int rideID) {
		int remainingTickets=0;
		if(regularTicket) {
			remainingTickets = this.tickets.get(0).getQuantity(); //regular tickets always first
			if(remainingTickets>0) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			for(Ticket t : tickets) {
				if(t.getRideID()==rideID) {
					remainingTickets = t.getQuantity();
					if(remainingTickets > 0 ) {
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Function add a delta num of tickets to this child.
	 * @param regularTicket - true if this is a regular ride, false otherwise
	 * @param rideID - used only when regularTicket is false
	 * @param delta - can be positive or negative, the amount of tickets to add to this child
	 * @exception BadTicketNumException  - when adding delta, the amount of tickets is negative
	 * @return nothing
	 */
	void changeNumOfTickets(boolean regularTicket,int rideID,int delta) throws BadTicketNumException {
		
		if(regularTicket) {
			tickets.get(0).addToQuantity(delta);
		}
		else {
			Ticket t = getTicket(rideID);
			if(t==null) {
				if(delta < 0) {
					throw new BadTicketNumException();
				}
				tickets.add(new Ticket(regularTicket,rideID,delta));
				return;
			}
			else {
				t.addToQuantity(delta);
			}
		}
	}
	
	/*
	 * Function makes the child to ride the rideID.
	 * @param rideID - the id of the ride, used only if it's an extreme ride
	 * @param regularTicket - true iff it's a regular ride
	 */
	void useRide(int rideID,boolean regularTicket) throws BadTicketNumException {
		if(this.hasTicket(regularTicket, rideID)==false) {
			throw new BadTicketNumException();
		}
		this.changeNumOfTickets(regularTicket, rideID, -1);
		this.updateLocation(rideID);
	}
	
	void getOffRide() {
		this.updateLocation(-1);
	}
	
	void updateLocation(int rideID) {
		this.rideLocation=rideID;
	}
	int getLocation() {
		return this.rideLocation;
	}
	int getAge() {
		return this.age;
	}
	Guardian getMyGuardian() {
		return this.myGuardian;
	}
	
	double getHeight() {
		return this.height;
	}
	public int getID() {
		return this.id;
	}
	
}
