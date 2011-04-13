package aero.nettracer.fs.utilities.tracing;

import java.util.concurrent.ArrayBlockingQueue;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.MatchHistory;


public class TraceWrapper {
	private static ArrayBlockingQueue<FsIncident> incidentQueue;
	private static ArrayBlockingQueue<FsClaim> claimQueue;
	private static ArrayBlockingQueue<MatchHistory> matchQueue;
	static int maxThreads = 10;

	public static ArrayBlockingQueue<FsClaim> getClaimQueue(){
		if(claimQueue == null){
			claimQueue = new ArrayBlockingQueue<FsClaim>(100000);
			for (int i=0; i<maxThreads; ++i) {
				try{
					Consumer consumer = new Consumer(claimQueue, Consumer.CLAIM, i);
					new Thread(consumer).start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return claimQueue;
	}

	public static ArrayBlockingQueue<FsIncident> getIncidentQueue(){
		if(incidentQueue == null){
			incidentQueue = new ArrayBlockingQueue<FsIncident>(1000);
			for (int i=0; i<maxThreads; ++i) {
				try{
					Consumer consumer = new Consumer(incidentQueue, Consumer.INCIDENT, i);
					new Thread(consumer).start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return incidentQueue;
	}

	public static ArrayBlockingQueue<MatchHistory> getMatchQueue(){
		if(matchQueue == null){
			matchQueue = new ArrayBlockingQueue<MatchHistory>(1000);
			for (int i=0; i<maxThreads; ++i) {
				try{
					Consumer consumer = new Consumer(matchQueue, Consumer.MATCH, i);
					new Thread(consumer).start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return matchQueue;
	}

}
