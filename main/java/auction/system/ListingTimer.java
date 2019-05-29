package auction.system;

import java.util.Timer;
import java.util.TimerTask;

public class ListingTimer extends Timer {
	public final String itemListingId;
	private final long tick;
	public final Thread monitorTimeoutThread = new Thread(
		new Runnable() {
			@Override
			public void run() {
				int ntick = 0;
				boolean stop = false;
				while (!stop) {
					try {
						Thread.sleep(tick);
						ntick++;
						System.out.println(String.format("%s listing duration monitor: tick %d", itemListingId, ntick));
					} catch (InterruptedException e) {
						System.out.println(String.format("%s listing duration monitor: CANCELED", itemListingId));
						stop = true;
					}
				}
			}
		}
	);
	
	public ListingTimer(String itemListingId, long tick, boolean isDaemon) {
		super(isDaemon);	//not a daemon
		this.itemListingId = itemListingId;
		this.tick = tick;
	}

	@Override
	public void schedule(TimerTask task, long delay) {
		if (delay > tick)
			monitorTimeoutThread.start();
		
		super.schedule(task, delay);
	}
}
