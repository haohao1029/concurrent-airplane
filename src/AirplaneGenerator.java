
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AirplaneGenerator implements Runnable{
	ControlTower ct;
	public AirplaneGenerator(ControlTower ct) {
		this.ct = ct;
	};
	
	@Override
	public void run() {
		int airplaneID = 1;
		
		while (airplaneID != 11) {
			int pfuelShortage = ThreadLocalRandom.current().nextInt(1, 10 + 1);
			int people = ThreadLocalRandom.current().nextInt(150, 200 + 1);
			int supplies = ThreadLocalRandom.current().nextInt(10, 30 + 1);
			int fuelLeft = ThreadLocalRandom.current().nextInt(5, 20 + 1);
			boolean fuel = true;
			if (pfuelShortage == 10) {
				fuel = false;
			}
			
			Airplane airplane = new Airplane(airplaneID, fuel, people, fuelLeft, supplies, ct);
			Thread thap = new Thread(airplane);
			thap.start();
			
			
			airplaneID++;
			
			
			try {
				int duration = ThreadLocalRandom.current().nextInt(0, 3 + 1);
				TimeUnit.SECONDS.sleep((long)(duration));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
