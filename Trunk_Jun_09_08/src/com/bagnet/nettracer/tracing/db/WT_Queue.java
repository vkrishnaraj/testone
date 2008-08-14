package com.bagnet.nettracer.tracing.db;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 * @hibernate.class table="wt_queue"
 */
public class WT_Queue implements Serializable{
	
	public static final String INCIDENT_TYPE = "Incident";
	public static final String OHD_TYPE = "OHD";
	public static final String CLOSE_INCIDENT_TYPE = "closeIncident";
	public static final String CLOSE_OHD_TYPE = "closeOHD";
	public static final String SUS_INC_TYPE = "SUSIncident";
	public static final String SUS_OHD_TYPE = "SUSOhd";
	public static final String PARTIAL_AHL_TYPE = "PartialAhl";
	public static final String ERASE_AF_TYPE = "EraseActionFile";
	
	private int wt_queue_id;
	private String wt_stationcode;
	private Agent agent; 
	private Date createdate;
	private String type_id;
	private String type;
	private int queue_status;
	private int sus_rit_item_id;
	/**
	 * @return the sus_rit_item_id
	 * @hibernate.property type="int"
	 */
	public int getSus_rit_item_id() {
		return sus_rit_item_id;
	}
	/**
	 * @param sus_rit_item_id the sus_rit_item_id to set
	 */
	public void setSus_rit_item_id(int sus_rit_item_id) {
		this.sus_rit_item_id = sus_rit_item_id;
	}
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
	 * @return the wt_stationcode
	 * @hibernate.property type="string"
	 */
	public String getWt_stationcode() {
		return wt_stationcode;
	}
	/**
	 * @param wt_stationcode the wt_stationcode to set
	 */
	public void setWt_stationcode(String wt_stationcode) {
		this.wt_stationcode = wt_stationcode;
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
