import java.util.concurrent.LinkedBlockingDeque;

class ControlTower {
	LinkedBlockingDeque<Airplane> listAirplane;
	
	public ControlTower() {
		listAirplane = new LinkedBlockingDeque<Airplane>();
	}
	 
	public Airplane askPlaneToLane() throws InterruptedException {
		
		Airplane ap = null;
		synchronized(listAirplane) {
			System.out.println(listAirplane);
			while(listAirplane.size() == 0 ) {
				listAirplane.wait();
			}
			System.out.println("airplane is in queue");
		}
		return ap;
	}
	
	public void add(Airplane ap) {
		
		System.out.println("Airplane "+ String.valueOf(ap.getId())+ " entering the queue ");
		synchronized (listAirplane) {
			
			if (ap.fuel) {
				listAirplane.add(ap);				
			}else {
				listAirplane.addFirst(ap);
			}
			int queueSize = listAirplane.size();
			System.out.println("Airplane queue has " + queueSize);
			if (listAirplane.size() == 1)
				listAirplane.notify();
		}
	}
}
