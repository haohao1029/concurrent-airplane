import java.util.concurrent.LinkedBlockingDeque;

class ControlTower {
	Runway rw = new Runway();
	LinkedBlockingDeque<Airplane> listAirplane;

	public ControlTower(LinkedBlockingDeque<Airplane> listAirplane) {
		this.listAirplane = listAirplane;
	}
	 
	public Airplane askPlaneToLane(Gateway gt) throws InterruptedException {
		Airplane ap = null;
		synchronized(listAirplane) {
			while(listAirplane.size() == 0 ) {
				listAirplane.wait();
			}
			System.out.println(listAirplane);
			ap = listAirplane.take();
			System.out.println("Airplane " + ap.id + " is assigned to gateway "+ gt.id+" prepare to costing to runway.");
			if (rw.getAvaiability()) 
				rw.coasting(ap);
			
			gt.docktoGateway(ap);			
			gt.undocktoGateway(ap);	
			
			if (rw.getAvaiability()) 
				rw.leaving(ap);			
			
		}
		return ap;
	}
	
	public void add(Airplane ap) {
		if (ap.shortage == true) {
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
