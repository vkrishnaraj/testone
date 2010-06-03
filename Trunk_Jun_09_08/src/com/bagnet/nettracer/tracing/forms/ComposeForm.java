package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for inbox related functionality.
 */
public final class ComposeForm extends ActionForm {

	private String date; //message date
	private List recp_list = new ArrayList(); //recipients
	private String subject; //subject
	private String body; //message body
	private String companyCode;
	private String stationCode;
	private String message_id;
	private String file_ref_number;
	private String file_type;
	private String agentName;

	//search fields
	private String message_status;
	private String s_time;
	private String e_time;
	private String search_sub;
	private String search_file_ref;

	/**
	 * @return Returns the file_ref_number.
	 */
	public String getFile_ref_number() {
		return file_ref_number;
	}

	/**
	 * @param file_ref_number
	 *          The file_ref_number to set.
	 */
	public void setFile_ref_number(String file_ref_number) {
		this.file_ref_number = file_ref_number;
	}

	/**
	 * @return Returns the file_type.
	 */
	public String getFile_type() {
		return file_type;
	}

	/**
	 * @param file_type
	 *          The file_type to set.
	 */
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *          The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *          The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *          The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return Returns the message_id.
	 */
	public String getMessage_id() {
		return message_id;
	}

	/**
	 * @param message_id
	 *          The message_id to set.
	 */
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	/**
	 * @return Returns the recp_list.
	 */
	public List getRecp_list() {
		return recp_list;
	}

	/**
	 * @param recp_list
	 *          The recp_list to set.
	 */
	public void setRecp_list(List recp_list) {
		this.recp_list = recp_list;
	}

	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode
	 *          The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName
	 *          The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return Returns the e_time.
	 */
	public String getE_time() {
		return e_time;
	}

	/**
	 * @param e_time
	 *          The e_time to set.
	 */
	public void setE_time(String e_time) {
		this.e_time = e_time;
	}

	/**
	 * @return Returns the message_status.
	 */
	public String getMessage_status() {
		return message_status;
	}

	/**
	 * @param message_status
	 *          The message_status to set.
	 */
	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}

	/**
	 * @return Returns the s_time.
	 */
	public String getS_time() {
		return s_time;
	}

	/**
	 * @param s_time
	 *          The s_time to set.
	 */
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}

	/**
	 * @return Returns the search_file_ref.
	 */
	public String getSearch_file_ref() {
		return search_file_ref;
	}

	/**
	 * @param search_file_ref
	 *          The search_file_ref to set.
	 */
	public void setSearch_file_ref(String search_file_ref) {
		this.search_file_ref = search_file_ref;
	}

	/**
	 * @return Returns the search_sub.
	 */
	public String getSearch_sub() {
		return search_sub;
	}

	/**
	 * @param search_sub
	 *          The search_sub to set.
	 */
	public void setSearch_sub(String search_sub) {
		this.search_sub = search_sub;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *          The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
    /**
     * log
     */
    private String log = "";

    public String getLog() {
        return this.log;
    }
    
    public void setLog(String log) {
        this.log = log;
    }
}