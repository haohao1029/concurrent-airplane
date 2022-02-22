
public class Main {
	public static void main(String[] args) {
		
		ControlTower ct = new ControlTower();
		
		AirplaneGenerator ag = new AirplaneGenerator(ct);
		Runway rw = new Runway(ct);
		Gateway gw1 = new Gateway(ct);
		Gateway gw2 = new Gateway(ct);

		Thread thag = new Thread(ag);
		//Thread thrw = new Thread(rw);
		Thread thgw1 = new Thread(gw1);
		Thread thgw2 = new Thread(gw2);
		thag.start();
		//thrw.start();
		thgw1.start();
		thgw2.start();
	}

}
