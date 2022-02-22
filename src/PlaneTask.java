import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class PlaneTask implements Callable{
	String task;
	
	public PlaneTask(String task) {
		this.task = task;
	}
	
	@Override
	public Integer call() throws Exception {
		int duration = ThreadLocalRandom.current().nextInt(5, 10 + 1);
		duration = duration * 100;
		if (task == "fuel") {
			Thread.sleep(duration);
			return 1;
		} else if (task == "supply") {
			Thread.sleep(duration);
			return 1;
		}
		return 0;
	}

}
