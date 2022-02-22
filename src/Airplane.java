import java.awt.*;

class Airplane implements Runnable{
	int id; boolean fuel; int people; int supplies; ControlTower ct; int fuelLeft;
	
	public Airplane(int id, boolean fuel, int people, int fuelLeft, int supplies, ControlTower ct) {
		this.id = id;
		this.fuel = fuel;
		this.people = people;
		this.supplies = supplies;
		this.ct = ct;
		this.fuelLeft = fuelLeft;
	}
	
	public int getId() {
		return id;
	}
	public boolean getFuel() {
		return fuel;
	}
	public int getPeople() {
		return people;
	}
	public int getSupply() {
		return supplies;
	}
	public int getFuelLeft() {
		return fuelLeft;
	}
	
	@Override
	public void run() {
		goForQueue();
	}	
	
	public void refillFuel() {
		this.fuelLeft = 100;
	}
	private synchronized void goForQueue()
    {
		ct.add(this);
    }

}
