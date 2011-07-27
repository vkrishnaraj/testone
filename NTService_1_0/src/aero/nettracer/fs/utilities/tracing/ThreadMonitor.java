package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

import aero.nettracer.fs.model.detection.MatchHistory;

public class ThreadMonitor implements Runnable{
	private static Vector<ThreadContainer>v;
	
	private static ArrayBlockingQueue<MatchHistory> matchQueue;
	
	private static final long TIMEOUT = 60000;
	private static final long POLL_INTERVAL = 60000;

	public ThreadMonitor(Vector<ThreadContainer>v, ArrayBlockingQueue<MatchHistory> matchQueue){
		this.v = v;
		this.matchQueue = matchQueue;
	}

	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(POLL_INTERVAL);
				//System.out.println("Consumer Queue count: " + matchQueue.size());
				for(ThreadContainer tc:v){
					Date now = new Date();
					if(tc.isWaiting() == false){
						System.out.println("Consumer Thread: " + tc.getId() + " is working " + (now.getTime() - tc.getStartTime().getTime()) );
						if(now.getTime() - tc.getStartTime().getTime() > TIMEOUT){
							try{
								//TODO either monitor or interrupt something that is interruptable
//								tc.getConsumer().interrupt();
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}