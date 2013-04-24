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
	
	public MatchHistory take(){
		ConsumerQueueElement element = null;
		synchronized(consumerQueue){
			int index = getNextIndex();
			if(index == -1){
				return null;
			}
			element = consumerQueue.get(index);
			if(element.getQueue().peek() != null){
				return element.getQueue().poll();
			} else {
				if(element.isProducerFinished()){
					consumerQueue.remove(element);
					element.setConsumerEnd(new Date());
				}
			}
		}//end synchronized
		
		if(element != null && element.getConsumerEnd() != null){
			AuditUtil.saveActionAudit(element.getAudit()
					.addMetric(AuditUtil.METRIC_TOTAL_PRODUCER, 
							element.getProducerEnd().getTime() - element.getProducerStart().getTime(), 
							element.getProducerCount(), null)
					.addMetric(AuditUtil.METRIC_TOTAL_CONSUMER, 
							element.getConsumerEnd().getTime() - element.getConsumerStart().getTime(), 
							element.getProducerCount(), null)
					.addMetric(AuditUtil.METRIC_TOTAL_TRACE, 
							element.getConsumerEnd().getTime() - element.getProducerStart().getTime(), 
							element.getProducerCount(), null));
		}
		return null;//TODO for now it is okay since queue is fifo, need to revisit later
	}
	
	private static int getNextIndex(){
		if(consumerQueue.size() == 0){
			return -1;
		} 
		return currentIndex;
	}
}
