import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
		while (GlobalVal.COUNT_PLANE != 10) {

			try {
				Airplane ap = ct.askPlaneToLane(this);
				long startTime = System.currentTimeMillis();
				rw.coasting(ap);
				docktoGateway(ap);
				disembarking(ap);
				ExecutorService service = Executors.newFixedThreadPool(3);
				Future<Integer> fuelAdding = service.submit(new PlaneTask("fuel",ap));
				Future<Integer> supplyAdding = service.submit(new PlaneTask("supply",ap));
				while (fuelAdding.isDone() == false || supplyAdding.isDone() == false) {}
				service.shutdown();
				embarking(ap);
				undocktoGateway(ap);
				rw.leaving(ap);
				long endTime = System.currentTimeMillis();
		        long timeElapsed = endTime - startTime;
				System.out.println("Airplane "+ ap.getId() +" time in milliseconds: " + timeElapsed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}		
	synchronized void disembarking(Airplane ap) {
		System.out.println("\t\t\tUser Airplane " + ap.getId() + "  Disembarking." + ap.airPlaneStatus());
		int duration = ap.getPeople() * 10;
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GlobalVal.COUNT_PEOPLE += ap.getPeople();
		ap.setPeople(0);
		System.out.println("\t\t\tUser Airplane" + ap.getId() + " Disembarking Done in " + duration + " milliseconds." + ap.airPlaneStatus());
	}
	synchronized void embarking(Airplane ap) {
		int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
		System.out.println("\t\t\tUser Airplane " + ap.getId() + "  embarking" + ap.airPlaneStatus());
		duration = duration * 100;
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ap.setPeople(duration * 10);
		System.out.println("\t\t\tUser Airplane " + ap.getId() + " embarking done in "+ duration +" milliseconds." + ap.airPlaneStatus());

	}

	synchronized void docktoGateway(Airplane ap) {
		try {
			int duration = 500;
			TimeUnit.MILLISECONDS.sleep(duration);
			assigned.acquire();
			System.out.println("\t\tAirplane "+ ap.getId() + " docked to gateway " + id + " in " + duration + " milliseconds.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized void undocktoGateway(Airplane ap) {
		try {
			int duration = 500;
			TimeUnit.MILLISECONDS.sleep(duration);
			assigned.release();
			System.out.println("\t\tAirplane "+ ap.getId() + " undocked from gateway " + id + " in " + duration + " milliseconds.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
