import java.util.concurrent.LinkedBlockingDeque;

public class Main {
	public static void main(String[] args) {
		Runway rw = new Runway();
		LinkedBlockingDeque<Airplane> listAirplane = new LinkedBlockingDeque<Airplane>();
		ControlTower ct = new ControlTower(listAirplane, rw);
		
		AirplaneGenerator ag = new AirplaneGenerator(ct, listAirplane);
		Gateway gw1 = new Gateway(ct, rw, 1, listAirplane);
		Gateway gw2 = new Gateway(ct, rw, 2, listAirplane);

		Thread thag = new Thread(ag);
		Thread thgw1 = new Thread(gw1);
		Thread thgw2 = new Thread(gw2);
		thag.start();
		thgw1.start();
		thgw2.start();
		
		while(thgw1.isAlive() == true || thgw2.isAlive() == true) {}
		
		System.out.println("Plane Served = " + GlobalVal.COUNT_PLANE);
		System.out.println("People Served = " + GlobalVal.COUNT_PEOPLE);
		System.out.println("Plane TOtal Time Wait = " + GlobalVal.TOTAL_TIME_WAIT + " milliseconds.");
		System.out.println("Plane MAX Time Wait = " + GlobalVal.MAX_WAIT + " milliseconds.");
		System.out.println("Plane AVG Time Wait = " + GlobalVal.TOTAL_TIME_WAIT / GlobalVal.COUNT_PLANE + " milliseconds.");
		System.out.println("Plane MIN Time Wait = " + GlobalVal.MIN_WAIT + " milliseconds.");
	}
}
