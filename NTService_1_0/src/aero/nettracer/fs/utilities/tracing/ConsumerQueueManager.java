package aero.nettracer.fs.utilities.tracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.AuditUtil;

public class ConsumerQueueManager {
	
	private static ConsumerQueueManager manager;
	
	private static List<ConsumerQueueElement> consumerQueue;
	private static int currentIndex;
	
	private ConsumerQueueManager(){
		consumerQueue = Collections.synchronizedList(new ArrayList<ConsumerQueueElement>());
		currentIndex = 0;
		TraceWrapper.startConsumerThreads();
	}
	
	public static synchronized ConsumerQueueManager getInstance(){
		if(manager == null){
			manager = new ConsumerQueueManager();
		}
		return manager;
	}
	
	public void put(ConsumerQueueElement element){
		synchronized(consumerQueue){
			consumerQueue.add(element);
		}
		synchronized(this){
			notifyAll();
		}
	}
	
	public void consumerNotify(){
		synchronized(this){
			notifyAll();
		}
	}
	
	public MatchHistory take(){
//		Date start = new Date();
		ConsumerQueueElement element = null;
		synchronized(consumerQueue){
			int index = getNextIndex();
			if(index == -1){
//				Date end = new Date();
//				System.out.println("Consumer TAKE: " + (end.getTime() - start.getTime()));
				return null;
			}
			element = consumerQueue.get(index);
			if(element.getQueue().peek() != null){
//				Date end = new Date();
//				System.out.println("Consumer TAKE: " + (end.getTime() - start.getTime()));
				return element.getQueue().poll();
			} else {
				consumerQueue.remove(element);
				element.setOnConsumerQueue(false);
				if(element.isAllProducersFinished()){
					Date consumerEnd = new Date();
					element.getAudit().addMetric(AuditUtil.METRIC_TOTAL_PRODUCER, 
							element.getProducerTotalEnd().getTime() - element.getProducerTotalStart().getTime(), 
							element.getProducerTotalCount(), null);
					element.getAudit().addMetric(AuditUtil.METRIC_PRODUCER_MAIN, 
							element.getProducerEnd().getTime() - element.getProducerStart().getTime(), 
							element.getProducerCount(), null);
					element.getAudit().addMetric(AuditUtil.METRIC_PRODUCER_GEO, 
							element.getProducerGeoEnd().getTime() - element.getProducerGeoStart().getTime(), 
							element.getProducerGeoCount(), null);
					element.getAudit().addMetric(AuditUtil.METRIC_TOTAL_CONSUMER, 
							consumerEnd.getTime() - element.getConsumerStart().getTime(), 
							element.getProducerTotalCount(), null);
					element.getAudit().addMetric(AuditUtil.METRIC_TOTAL_TRACE, 
							consumerEnd.getTime() - element.getProducerTotalStart().getTime(), 
							element.getProducerTotalCount(), null);
					element.setConsumerEnd(consumerEnd);
					System.out.println("Saving Trace Audit");

				}
			}
		}//end synchronized
		if(element != null && element.getConsumerEnd() != null){
//			Date s = new Date();
			AuditUtil.saveActionAudit(element.getAudit(),true);
//			Date e = new Date();
//			System.out.println("Consumer audit thread createion: " + (e.getTime() - s.getTime()));
		}
//		Date end = new Date();
//		System.out.println("Consumer TAKE: " + (end.getTime() - start.getTime()));
		return this.take();
	}
	
	private static int getNextIndex(){
		if(consumerQueue.size() == 0){
			return -1;
		} 
		return currentIndex;
	}
}
