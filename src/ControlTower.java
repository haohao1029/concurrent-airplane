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
		reachedPlane++;
		synchronized(listAirplane) {
			while(listAirplane.size() == 0 ) {
				listAirplane.wait();
			}
			GlobalVal.COUNT_PLANE++;
			ap = listAirplane.take();
			System.out.println("reachedPlane");
			System.out.println(reachedPlane);

			System.out.println("\tAirplane " + ap.getId() + " is assigned to gateway "+ gt.id+".");			
		}
		ap.setEnd();
		GlobalVal.TOTAL_TIME_WAIT += ap.WaitTime();
		if (ap.WaitTime() > GlobalVal.MAX_WAIT) {
			GlobalVal.MAX_WAIT = ap.WaitTime();
		}
		if (ap.WaitTime() < GlobalVal.MIN_WAIT) {
			GlobalVal.MIN_WAIT = ap.WaitTime();
		}
		GlobalVal.COUNT_PEOPLE += ap.getPeople();
		return ap;
	}
	
	public void add(Airplane ap) {
		if (ap.getShortage() == true) {
			System.out.println("Shortage Airplane "+ String.valueOf(ap.getId())+ " with " + ap + " is entering the queue." + ap.airPlaneStatus());
			listAirplane.offerFirst(ap);
		} else {
			System.out.println("Airplane "+ String.valueOf(ap.getId())+ " with " + ap + " is entering the queue." + ap.airPlaneStatus());
			listAirplane.offer(ap);			
		}		
		ap.setStart();
		synchronized (listAirplane) {
			int queueSize = listAirplane.size();
			System.out.println("Airplane queue have " + queueSize + " on waiting.");
			if (listAirplane.size() == 1)
				listAirplane.notify();
		}
	}
}
