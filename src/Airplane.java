import java.awt.*;

class Airplane implements Runnable{
	private int id;
	private  boolean fuel;
	private int people;
	private boolean supplies;
	private ControlTower ct;
	private boolean shortage;
	private long startWaitingTIme;
	private long endWaitingTime;
	public Airplane(int id, boolean fuel, int people, boolean shortage, boolean supplies, ControlTower ct) {
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
	public int getPeople() {
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
	public int setPeople(int people) {
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
	
	public void setStart() {
		this.startWaitingTIme = System.currentTimeMillis();
	}
	
	public void setEnd() {
		this.endWaitingTime = System.currentTimeMillis();
	}
	
	public long WaitTime() {
		return this.endWaitingTime - this.startWaitingTIme;
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
	public String airPlaneStatus() {
		return  " Airplane = " + id + " | Shortage = " + shortage + " | People = " + people + " | Fuel = " + fuel +  " | Supplies = " + supplies;
	}
}
