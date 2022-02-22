import java.awt.*;

class Airplane implements Runnable{
	private int id;
	private  boolean fuel;
	private boolean people;
	private boolean supplies;
	private ControlTower ct;
	private boolean shortage;
	
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
	public int setId(int id) {
		this.id = id;
        return id;
	}
	public boolean setShortage(boolean shortage) {
		this.shortage = shortage;
        return shortage;
	}
	public boolean setPeople(boolean people) {
		this.people = people;
        return people;
	}
	public boolean setSupply(boolean supplies) {
		this.supplies = supplies;
        return supplies;
	}
	public boolean setFuel(boolean fuel) {
		this.fuel = fuel;
        return fuel;
	}
	@Override
	public void run() {
		goForQueue();
	}	
	public void customerDisembarking() {
		
	}
	private synchronized void goForQueue()
    {
		ct.add(this);
    }

}
