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
	boolean mainProducerThread = false;
	boolean geoProducerThread = true;
	ConcurrentLinkedQueue<MatchHistory> queue;
	Date producerStart;
	Date producerEnd;
	Date consumerStart;
	Date consumerEnd;
	FsActionAudit audit;
	ConcurrentHashMap<Long,Object> traceIds;
	File file;

	


	public static ConsumerQueueElement getInstance(File file){
		ConsumerQueueElement element = new ConsumerQueueElement();
		element.file = file;
		element.queue = new ConcurrentLinkedQueue<MatchHistory>();
		element.traceIds = new ConcurrentHashMap<Long,Object>();
		element.traceIds.put(file.getId(), file.getId());//adding search claim to map so we don't trace against itself
		element.audit = new FsActionAudit();
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
	
	public boolean isGeoProducerThread() {
		return geoProducerThread;
	}


	public void setGeoProducerThread(boolean geoProducerThread) {
		this.geoProducerThread = geoProducerThread;
	}


	public boolean isMainProducerThread() {
		return mainProducerThread;
	}


	public void setMainProducerThread(boolean mainProducerThread) {
		this.mainProducerThread = mainProducerThread;
	}

	public ConcurrentLinkedQueue<MatchHistory> getQueue() {
		return queue;
	}


	public void setQueue(ConcurrentLinkedQueue<MatchHistory> queue) {
		this.queue = queue;
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


	public Date getConsumerStart() {
		return consumerStart;
	}


	public void setConsumerStart(Date consumerStart) {
		this.consumerStart = consumerStart;
	}


	public Date getConsumerEnd() {
		return consumerEnd;
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
	
	public boolean isProducerFinished(){
		return mainProducerThread && geoProducerThread;
	}
	
	public boolean isTraceFinished(){
		return this.isProducerFinished() && this.queue.size() == 0;
	}
	
	public int getProducerCount(){
		return this.traceIds.size() - 1;//remove reference to self
	}
	
	
}
