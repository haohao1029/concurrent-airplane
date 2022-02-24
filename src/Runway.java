import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Runway {
	private Semaphore runwayAssigned = new Semaphore(1);
	public Runway() {
	};

	synchronized void coasting(Airplane ap) throws InterruptedException {
		if (runwayAssigned.availablePermits() == 0) {
			System.out.println("\tAirplane " + ap.getId() + "Waiting for runway avaiable");
		}
		while (runwayAssigned.availablePermits() == 0) wait();
		System.out.println("\tAirplane " + ap.getId() + " prepare to costing to runway.");
		runwayAssigned.acquire();
		TimeUnit.MILLISECONDS.sleep(1000);	
		System.out.println("\tAirplane " + ap.getId() + " reached gateway.");
		runwayAssigned.release();
		notify();
	}
	
	synchronized void leaving(Airplane ap) throws InterruptedException {
		if (runwayAssigned.availablePermits() == 0) {
			System.out.println("\tAirplane " + ap.getId() + " Waiting for runway avaiable");
		}
		while (runwayAssigned.availablePermits() == 0) wait();
		System.out.println("\tAirplane " + ap.getId() + " is coasting to runway prepare to leaving.");
		runwayAssigned.acquire();
		TimeUnit.MILLISECONDS.sleep(1000);
		runwayAssigned.release();
		notify();
	}
	
}
