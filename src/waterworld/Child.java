package waterworld;

import java.util.LinkedList;

/*
 *	This class represents a child visiting WaterWorld. A Child type has necessary information about the visitor. 
 */
public class Child {
    int age; // the visitor's age in years, an integer
    int identity; // unique identification assigned to the child
    int curr_ride; // id of current ride (location) of the child
    double height; // child's height in centimetres

    LinkedList<Ticket> tickets; // all the tickets in the visitor's possession
    private int ticket_id;

    // Child c'tor
    public Child(int n_age, double n_height, int n_identity, int n_curr_ride) {
        this.age = n_age;
        this.height = n_height;
        this.identity = n_identity;
        this.curr_ride = n_curr_ride;
        this.tickets = new LinkedList<>(); // removed explicit type
    }

    // adds a new ticket to the list
    void add_ticket(Ticket new_ticket) {
        tickets.add(new_ticket);
    }

    // tickets for ride @ticket_id
    void remove_ticket(int ticket_id) {
        // assumes has_ticket() is true;
        for (Ticket t : tickets) {
            if (t.is_for_ride(ticket_id)) {
                tickets.remove(t);
                break;
            }
        }
    }

    // true if there's a ticket for a ride @ticket_id in the child's possession,
    // false otherwise
    boolean has_ticket(int ticket_id) {
        for (Ticket t : tickets)
            if (t.is_for_ride(ticket_id)) // changed
                return true;
        return false;
    }

    // returns the current ride
    int get_location() {
        return curr_ride;
    }

    // after signing in to a new ride, update using this function
    void update_location(int ride_id) {
        this.curr_ride = ride_id;
    }
}
