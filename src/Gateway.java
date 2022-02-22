import java.util.concurrent.Semaphore;
public class Gateway implements Runnable{
	static Semaphore s = new Semaphore(1);
	ControlTower ct;
	public Gateway(ControlTower ct) {
		this.ct = ct;
	};
	@Override
	public void run() {
		try {
			Airplane ap = ct.askPlaneToLane();
			System.out.println(ap.getId());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	synchronized void docktoGateway() {
		
	}
	synchronized void planeLeave() {
		
	}
	public void lock() throws InterruptedException {
		s.acquire();
	};
	public void release() throws InterruptedException {
		s.release();
	};
}
