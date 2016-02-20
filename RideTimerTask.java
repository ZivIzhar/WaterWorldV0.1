import java.util.TimerTask;


public class RideTimerTask extends TimerTask {
	Ride myRide;
	public RideTimerTask(Ride ride) {
		this.myRide = ride;
	}
	
	public void run() {
		this.myRide.activateRide();
	}
}
