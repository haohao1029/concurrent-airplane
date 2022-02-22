import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
public class Gateway implements Runnable{

	private Semaphore assigned = new Semaphore(1);
	ControlTower ct;
	int id;
	LinkedBlockingDeque<Airplane> listAirplane;
	public Gateway(ControlTower ct, int id, LinkedBlockingDeque<Airplane> listAirplane) {
		this.ct = ct;
		this.id = id;
		this.listAirplane = listAirplane;

	};
	
	@Override
	public void run() {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
		while (true) {
			try {
				Airplane ap = ct.askPlaneToLane(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}		
	
	synchronized void docktoGateway(Airplane ap) {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
			assigned.acquire();
			System.out.println("Airplane "+ ap.id + " docked to gateway " + id + ".");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized void undocktoGateway(Airplane ap) {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
			assigned.release();
			System.out.println("Airplane "+ ap.id + " undocked from gateway " + id + ".");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	synchronized void planeLeave() {
		
	}
}
