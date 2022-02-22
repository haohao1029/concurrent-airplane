import java.awt.*;

class Airplane implements Runnable{
	int id; boolean fuel; boolean people; boolean supplies; ControlTower ct; boolean shortage;
	
	public Airplane(int id, boolean fuel, boolean people, boolean shortage, boolean supplies, ControlTower ct) {
		this.id = id;
		this.fuel = fuel;
		this.people = people;
		this.supplies = supplies;
		this.ct = ct;
		this.shortage = shortage;
	}
	
	public int getId() {
		return id;
	}
	public boolean getShortage() {
		return shortage;
	}
	public boolean getPeople() {
		return people;
	}
	public boolean getSupply() {
		return supplies;
	}
	public boolean getFuel() {
		return fuel;
	}
	
	@Override
	public void run() {
		goForQueue();
	}	
	public void customerDisembarking() {
		
	}
	public void refillFuel() {

	}
	private synchronized void goForQueue()
    {
		ct.add(this);
    }

}
