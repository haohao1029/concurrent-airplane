

public class Runway {
	ControlTower ct;
	boolean taken = false;
	public Runway(ControlTower ct) {
		this.ct = ct;
	};

	synchronized void Coasting() throws InterruptedException {
		while (taken) wait();
	    taken=true;		
	}
	
	synchronized void notInUse() {
	    taken=false;
		notify();
	}
	
}
