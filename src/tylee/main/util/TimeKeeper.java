package tylee.main.util;

import tylee.gamestate.GameState;

/**
 * Keeps time
 * 
 * Needs to be reviewed for potential synchronization issues.
 * 
 * @author tyvanroy
 *
 */
public class TimeKeeper implements Runnable {
	private GameState s;
	private double tickRate;
	private double targetFrameRate;
	private double frameRate;
	private double startTime;
	private double pauseTime;
	private boolean running;
	private boolean giveFeedback;
	private int frame;
	private boolean loop;
	private int loops = 0;
	private double loopTime;

	public TimeKeeper(GameState s, double tickRate, double targetFrameRate, double loopTime) {
		this.s = s;
		this.tickRate = tickRate;
		this.targetFrameRate = targetFrameRate;
		this.running = false;
		this.giveFeedback = false;
		if (loopTime != 0)
			this.loop = true;
		frame = 1;
		startTime = 0;
		this.loopTime = loopTime;
	}

	public TimeKeeper(GameState s, double tickRate, double targetFrameRate) {
		this(s, tickRate, targetFrameRate, 0);
	}

	public void init() {
		startTime = getTime();
		new Thread(this).start();
	}

	@Override
	public void run() {
		running = true;

		// Time keeper variables
		long timeCycleStartTime = System.nanoTime();

		// Frame variables
		long frameCycleStartTime = timeCycleStartTime;
		long frameTime = 0;
		long cTime;
		long realTime;
		long cFrameTime;
		long realFrameTime;
		long ticks = 0;
		long lastLoopInTicks = 0;

		while (running) {

			cTime = System.nanoTime();
			realTime = cTime - timeCycleStartTime;

			cFrameTime = cTime;
			realFrameTime = cFrameTime - frameCycleStartTime;

			// Evaluate update timer
			if (realTime > (1000000000.0 / tickRate)) {
				if (loop && (ticks - lastLoopInTicks) >= (loopTime * tickRate)) {
					loops++;
					restart();
					lastLoopInTicks = ticks;
					continue;
				}
				s.tick();
				ticks++;

				// reset time cycle
				timeCycleStartTime = cTime;
			}

			// Evaluate frame timer
			if (realFrameTime > (1000000000.0 / targetFrameRate)) {
				frameTime += realFrameTime;

				// print real framerate
				if (frame % targetFrameRate == 0) {
					double frameTimeSeconds = frameTime / 1000000000.0;
					frameTime = 0;

					frameRate = (targetFrameRate / frameTimeSeconds);
					if (giveFeedback) {
						giveFeedback();
					}
				}

				// render
				s.render();
				frame++;

				// reset frame time cycle
				frameCycleStartTime = cFrameTime;
			}
		}
	}

	public void giveFeedback() {
		System.out.printf("Framerate: %.5f FPS (%ds)\n", frameRate, (int) getTime());
	}

	public synchronized double getTime() {
		return pauseTime != 0 ? pauseTime : (System.nanoTime() / 1000000000.0 - startTime);
	}

	private synchronized void resetTime() {
		startTime = 0;
		startTime = getTime();
	}

	public synchronized void jump(long time) {
		startTime = time;
	}

	public synchronized void restart() {
		resetTime();
	}

	public synchronized void pause() {
		pauseTime = getTime();
	}

	public synchronized void play() {
		double pTime = pauseTime;
		pauseTime = 0;
		startTime += (getTime() - pTime);
	}

	public synchronized boolean paused() {
		return pauseTime != 0;
	}

	public synchronized int getLoops() {
		return loops;
	}
	
	public synchronized void halt(){
		running = false;
	}
}