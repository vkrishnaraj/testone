package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsActionAudit;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.utilities.AuditUtil;

public class ConsumerQueueElement {

	long id;//unique trace id
	ConcurrentLinkedQueue<MatchHistory> queue;

	Date producerTotalStart;
	Date producerTotalEnd;
	
	Date producerStart;
	Date producerEnd;
	boolean producerFinished = false;
	int producerCount;

	Date producerGeoStart;
	Date producerGeoEnd;
	boolean producerGeoFinished = false;
	int producerGeoCount;
	
	Date consumerStart;
	Date consumerEnd;
	Date consumerPaused;

	FsActionAudit audit;
	ConcurrentHashMap<Long,Object> traceIds;
	File file;
	boolean onConsumerQueue;


	public static ConsumerQueueElement getInstance(File file){
		ConsumerQueueElement element = new ConsumerQueueElement();
		element.file = file;
		element.queue = new ConcurrentLinkedQueue<MatchHistory>();
		element.traceIds = new ConcurrentHashMap<Long,Object>();
		element.traceIds.put(file.getId(), file.getId());//adding search claim to map so we don't trace against itself
		element.audit = new FsActionAudit();
		element.audit.setFile_id(file.getId());
		element.audit.setAction(AuditUtil.ACTION_TRACE_FILE);
		element.audit.setActiondate(new Date());
		element.audit.setCompanycode_id(file.getValidatingCompanycode());
		return element;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	public ConcurrentLinkedQueue<MatchHistory> getQueue() {
		return queue;
	}


	public void setQueue(ConcurrentLinkedQueue<MatchHistory> queue) {
		this.queue = queue;
	}
	
	public Date getProducerTotalStart() {
		return producerTotalStart;
	}

	public void setProducerTotalStart(Date producerTotalStart) {
		this.producerTotalStart = producerTotalStart;
	}

	public Date getProducerTotalEnd() {
		return producerTotalEnd;
	}

	public void setProducerTotalEnd(Date producerTotalEnd) {
		this.producerTotalEnd = producerTotalEnd;
	}
	
	public Date getProducerStart() {
		return producerStart;
	}


	public void setProducerStart(Date producerStart) {
		this.producerStart = producerStart;
	}


	public Date getProducerEnd() {
		return producerEnd;
	}


	public void setProducerEnd(Date producerEnd) {
		this.producerEnd = producerEnd;
	}

	public boolean isProducerFinished() {
		return producerFinished;
	}

	public void setProducerFinished(boolean producerFinished) {
		this.producerFinished = producerFinished;
		if(isAllProducersFinished()){
			setProducerTotalEnd(new Date());
		}
		if(this.producerFinished == true){
			this.setOnConsumerQueue(true);
		}
	}
	
	public int getProducerCount() {
		return producerCount;
	}

	public void setProducerCount(int producerCount) {
		this.producerCount = producerCount;
	}
	
	public Date getProducerGeoStart() {
		return producerGeoStart;
	}

	public void setProducerGeoStart(Date producerGeoStart) {
		this.producerGeoStart = producerGeoStart;
	}

	public Date getProducerGeoEnd() {
		return producerGeoEnd;
	}
	
	public void setProducerGeoEnd(Date producerGeoEnd) {
		this.producerGeoEnd = producerGeoEnd;
	}
	
	public boolean isProducerGeoFinished() {
		return producerGeoFinished;
	}

	public void setProducerGeoFinished(boolean producerGeoFinished) {
		this.producerGeoFinished = producerGeoFinished;
		if(isAllProducersFinished()){
			setProducerTotalEnd(new Date());
		}
		if(this.producerGeoFinished == true){
			this.setOnConsumerQueue(true);
		}
	}

	public int getProducerGeoCount() {
		return producerGeoCount;
	}

	public void setProducerGeoCount(int producerGeoCount) {
		this.producerGeoCount = producerGeoCount;
	}
	
	public Date getConsumerStart() {
		return consumerStart;
	}


	public void setConsumerStart(Date consumerStart) {
		this.consumerStart = consumerStart;
	}


	public Date getConsumerEnd() {
		return consumerEnd;
	}
	
	public Date getConsumerPaused() {
		return consumerPaused;
	}

	public void setConsumerPaused(Date consumerPaused) {
		this.consumerPaused = consumerPaused;
	}

	public FsActionAudit getAudit() {
		return audit;
	}


	public void setAudit(FsActionAudit audit) {
		this.audit = audit;
	}


	public void setConsumerEnd(Date consumerEnd) {
		this.consumerEnd = consumerEnd;
	}
	

	public ConcurrentHashMap<Long, Object> getTraceIds() {
		return traceIds;
	}


	public void setTraceIds(ConcurrentHashMap<Long, Object> traceIds) {
		this.traceIds = traceIds;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public boolean isOnConsumerQueue() {
		return onConsumerQueue;
	}


	
	public void setOnConsumerQueue(boolean onConsumerQueue) {
		synchronized(this){
			if(onConsumerQueue == true){
				this.startConusmerTimer();
				if(this.isOnConsumerQueue() == false){
					ConsumerQueueManager.getInstance().put(this);
				}
				ConsumerQueueManager.getInstance().consumerNotify();
			} else {
				this.pauseConsumerTimer();
			}
			this.onConsumerQueue = onConsumerQueue;
		}
	}
	
	public boolean isAllProducersFinished(){
		return producerFinished && producerGeoFinished;
	}
	
	public boolean isTraceFinished(){
		return this.isAllProducersFinished() && this.queue.size() == 0;
	}
	
	public int getProducerTotalCount(){
		return this.traceIds.size() - 1;//remove reference to self
	}
	
	public void startConusmerTimer(){
		if(this.consumerStart == null){
			this.consumerStart = new Date();
		}
		if(this.consumerPaused != null){
			//adjust start time
			this.consumerStart.setTime(this.consumerStart.getTime() + ((new Date()).getTime() - this.consumerPaused.getTime()));
			this.consumerPaused = null;
		}
	}
	
	public void pauseConsumerTimer(){
		this.consumerPaused = new Date();
	}
}
