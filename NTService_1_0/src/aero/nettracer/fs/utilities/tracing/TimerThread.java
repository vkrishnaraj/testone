package aero.nettracer.fs.utilities.tracing;

import java.io.BufferedReader;

public class TimerThread implements Runnable{

	private Thread parentThread;
	private long wait;
	
	
	public TimerThread (Thread parentThread, long wait){
		this.parentThread = parentThread;
		this.wait = wait;
	}
	
	@Override
	public void run() {
		try{
			Thread.sleep(wait);
//			System.out.println("attempt to interrupt...");
			parentThread.interrupt();
		}catch (Exception e){
//			System.out.println("exit timer");
			//fail silently
		}
		
	}

}
