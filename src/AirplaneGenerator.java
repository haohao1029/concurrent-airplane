
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AirplaneGenerator implements Runnable{
	ControlTower ct;
	LinkedBlockingDeque<Airplane> listAirplane;
	public AirplaneGenerator(ControlTower ct, LinkedBlockingDeque<Airplane> listAirplane) {
		this.ct = ct;
		this.listAirplane = listAirplane;
	};
	
	@Override
	public void run() {
		int airplaneID = 1;
		
		while (airplaneID != 11) {
			int pfuelShortage = ThreadLocalRandom.current().nextInt(1, 10 + 1);
			boolean shortage = false;
			if (pfuelShortage == 10) {
				shortage = true;
			}
			
			Airplane airplane = new Airplane(
					airplaneID, 
					false, 
					true, 
					shortage, 
					false, 
					ct);
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
