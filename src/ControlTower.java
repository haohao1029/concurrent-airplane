import java.util.concurrent.LinkedBlockingDeque;

class ControlTower {
	Runway rw;
	LinkedBlockingDeque<Airplane> listAirplane;

	public ControlTower(LinkedBlockingDeque<Airplane> listAirplane, Runway rw) {
		this.listAirplane = listAirplane;
		this.rw = rw;
	}
	 
	public Airplane askPlaneToLane(Gateway gt) throws InterruptedException {
		Airplane ap = null;
		synchronized(listAirplane) {
			while(listAirplane.size() == 0 ) {
				listAirplane.wait();
			}
			ap = listAirplane.take();
			System.out.println("\tAirplane " + ap.getId() + " is assigned to gateway "+ gt.id+".");			
		}
		return ap;
	}
	
	public void add(Airplane ap) {
		if (ap.getShortage() == true) {
			System.out.println("Shortage Airplane "+ String.valueOf(ap.getId())+ " with " + ap + " is entering the queue ");
			listAirplane.offerFirst(ap);
		} else {
			System.out.println("Airplane "+ String.valueOf(ap.getId())+ " with " + ap + " is entering the queue ");
			listAirplane.offer(ap);			
		}		
		synchronized (listAirplane) {
			int queueSize = listAirplane.size();
			System.out.println("Airplane queue have " + queueSize + " on waiting");
			if (listAirplane.size() == 1)
				listAirplane.notify();
		}
	}
}
