package com.bagnet.nettracer.tracing.db;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 * @hibernate.class table="wt_queue"
 */
public class WT_Queue implements Serializable{
	private int wt_queue_id;
	private String stationcode;
	private Agent agent; 
	private Date createdate;
	private String type_id;
	private String type;
	private int queue_status;
	/**
	 * @return the queue_status
	 * @hibernate.property type="int"
	 */
	public int getQueue_status() {
		return queue_status;
	}
	/**
	 * @param queue_status the queue_status to set
	 */
	public void setQueue_status(int queue_status) {
		this.queue_status = queue_status;
	}
	/**
	 * @hibernate.id generator-class="native" type="integer" column="wt_queue_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="wt_queue_id"
	 * 
	 * 
	 * @return Returns the wt_queue_id.
	 */
	public int getWt_queue_id() {
		return wt_queue_id;
	}
	/**
	 * @param wt_queue_id the wt_queue_id to set
	 */
	public void setWt_queue_id(int wt_queue_id) {
		this.wt_queue_id = wt_queue_id;
	}
	/**
	 * @return the stationcode
	 * @hibernate.property type="string"
	 */
	public String getStationcode() {
		return stationcode;
	}
	/**
	 * @param stationcode the stationcode to set
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	/**
	 * @return the agent
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"  foreign-key="agent_ID"
	 */
	public Agent getAgent() {
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	/**
	 * @return the createdate
	 * @hibernate.property type="timestamp"
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return the type
	 * @hibernate.property type="string"
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the type_id
	 * @hibernate.property type="string"
	 */
	public String getType_id() {
		return type_id;
	}
	/**
	 * @param type_id the type_id to set
	 */
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

}
