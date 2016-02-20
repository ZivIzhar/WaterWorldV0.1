
public class Ticket {

	boolean regularTicket;
	int rideID;
	int quantity;
	
	/*
	 * if regularTicket is true then this ticket is valid only for
	 * regular ride, so rideID has no meaning. if regularTicket is false
	 * then this is a ticket for an extreme ride, so it's valid only for
	 * the ride with rideID
	 */
	public Ticket(boolean regularTicket,int rideID) {
		this.regularTicket=regularTicket;
		this.rideID = rideID;
		this.quantity=0;
	}
	
	public Ticket(boolean regularTicket,int rideID,int quantity) {
		this.regularTicket=regularTicket;
		this.rideID = rideID;
		this.quantity=quantity;
	}
	
	
	
	boolean getRegularTicket() {
		return regularTicket;
	}
	int getRideID() {
		return rideID;
	}
	void addToQuantity(int delta) throws BadTicketNumException {
		if(this.quantity + delta < 0) {
			throw new BadTicketNumException();
		}
		this.quantity += delta;
	}
	int getQuantity() {
		return this.quantity;
	}
	
}
