package waterworld;

public class Main {

    public static void main(String[] args) {
        Child child = new Child(13, 187.1, 1270, 0);
        Ticket new_t = new Ticket(12);
        Ticket am = new Ticket(13);

        boolean t0 = child.has_ticket(12);
        if(!t0){
            System.out.println("don't have");
        }
        child.add_ticket(am);
        boolean t1 = child.has_ticket(13);
        boolean t2 = ! child.has_ticket(12);
        if(t1 && t2){
            System.out.println("correct");
        }
        System.out.println("printing is OK");
    }
}
