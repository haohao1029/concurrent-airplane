import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class PlaneTask implements Callable{
	String task;
	Airplane ap;
	public PlaneTask(String task, Airplane ap) {
		this.task = task;
		this.ap = ap;
	}
	
	@Override
	public Integer call() throws Exception {
		if (task == "fuel") {
			int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
			duration = duration * 100;
			Thread.sleep(duration);
			System.out.println("The fuel of Airplane " + ap.getId() + " is refilled in " + duration + "." + ap.airPlaneStatus());
			return duration;
		} else if (task == "supply") {
			int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
			duration = duration * 100;
			Thread.sleep(duration);
			System.out.println("The supply of Airplane " + ap.getId() + " is refilled in " + duration + "." + ap.airPlaneStatus());
			return duration;
		}
		return 0;
	}
}

