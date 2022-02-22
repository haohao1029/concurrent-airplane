import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Runway {
	private Semaphore runwayAssigned = new Semaphore(1);
	public Runway() {
	};

	synchronized void coasting(Airplane ap) throws InterruptedException {
		if (runwayAssigned.availablePermits() == 1) {
			runwayAssigned.acquire();
		}
		TimeUnit.MILLISECONDS.sleep(1000);
		
		if (runwayAssigned.availablePermits() == 0) {
			runwayAssigned.release();
		}
	}
	synchronized void leaving(Airplane ap) throws InterruptedException {
		System.out.println("Airplane " + ap.id + " is coasting to runway prepare to leaving.");

		if (runwayAssigned.availablePermits() == 1) {
			runwayAssigned.acquire();
		}
		TimeUnit.MILLISECONDS.sleep(1000);
		
		if (runwayAssigned.availablePermits() == 0) {
			runwayAssigned.release();
			this.notify();
		}
		System.out.println("Airplane " + ap.id + " left.");
	}
	boolean getAvaiability() {
		if (runwayAssigned.availablePermits() == 1) {				
			return false;
		}
		return true;
	}
}
