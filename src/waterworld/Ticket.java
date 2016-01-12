package waterworld;

/*
 * Class for tickets
 */
public class Ticket {
    int ride_id; // has the ride corresponding to the ticket

    // create a ticket from @new_id
    public Ticket(int new_id) {
        this.ride_id = new_id;
    }

    // checks if ticket is for @ride
    public boolean is_for_ride(int ride) {
        return (ride == ride_id);
    }
}
