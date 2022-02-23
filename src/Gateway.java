import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
public class Gateway implements Runnable{

	private Semaphore assigned = new Semaphore(1);
	ControlTower ct;
	int id;
	Runway rw;
	LinkedBlockingDeque<Airplane> listAirplane;
	public Gateway(ControlTower ct, Runway rw, int id, LinkedBlockingDeque<Airplane> listAirplane) {
		this.ct = ct;
		this.id = id;
		this.listAirplane = listAirplane;
		this.rw = rw;

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
		while (ct.reachedPlane) {
			try {
				Airplane ap = ct.askPlaneToLane(this);
				rw.coasting(ap);
				docktoGateway(ap);
				disembarking(ap);
				ExecutorService service = Executors.newFixedThreadPool(3);
				List<Future> allfutures = new ArrayList<>();
				Future<Integer> fuelAdding = service.submit(new PlaneTask("fuel"));
				Future<Integer> supplyAdding = service.submit(new PlaneTask("supply"));
				try {
					Integer fuelAdded = fuelAdding.get();
					Integer supplyAdded = fuelAdding.get();
					if (fuelAdded == 1) {
						ap.setFuel(true);
					} else {
						ap.setFuel(false);
					}
					if (supplyAdded == 1) {
						ap.setSupply(true);
					} else {
						ap.setSupply(false);
					}
					System.out.println("\t\t\t\tThe fuel of Airplane " + ap.getId() + " is fulfilled.");
					System.out.println("\t\t\t\tThe supply of Airplane " + ap.getId() + " is fulfilled.");
					
				} catch (ExecutionException e) {
					System.out.println("Result from the task is not");
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println("Result from the task is not" );
					e.printStackTrace();
				}
				embarking(ap);
				undocktoGateway(ap);
				rw.leaving(ap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}		
	synchronized void disembarking(Airplane ap) {
		int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
		System.out.println("\t\t\tCustomers Airplane " + ap.getId() + "  Disembarking");
		duration = duration * 100;
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ap.setPeople(false);
		System.out.println("\t\t\tCustomersAirplane" + ap.getId() + " Disembarking Done");
	}
	synchronized void embarking(Airplane ap) {
		int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
		System.out.println("\t\t\tCustomers Airplane " + ap.getId() + "  embarking");
		duration = duration * 100;
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ap.setPeople(true);
		System.out.println("\t\t\tCustomers Airplane " + ap.getId() + " embarking Done.");

	}

	synchronized void docktoGateway(Airplane ap) {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
			assigned.acquire();
			System.out.println("\t\tAirplane "+ ap.getId() + " docked to gateway " + id + ".");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized void undocktoGateway(Airplane ap) {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
			assigned.release();
			System.out.println("\t\tAirplane "+ ap.getId() + " undocked from gateway " + id + ".");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
