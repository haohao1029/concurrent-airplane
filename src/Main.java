import java.util.concurrent.LinkedBlockingDeque;

public class Main {
	public static void main(String[] args) {
		
		LinkedBlockingDeque<Airplane> listAirplane = new LinkedBlockingDeque<Airplane>();
		ControlTower ct = new ControlTower(listAirplane);

		AirplaneGenerator ag = new AirplaneGenerator(ct, listAirplane);
		Gateway gw1 = new Gateway(ct, 1, listAirplane);
		Gateway gw2 = new Gateway(ct, 2, listAirplane);

		Thread thag = new Thread(ag);
		Thread thgw1 = new Thread(gw1);
		Thread thgw2 = new Thread(gw2);
		thag.start();
		thgw1.start();
		thgw2.start();

	}

}
