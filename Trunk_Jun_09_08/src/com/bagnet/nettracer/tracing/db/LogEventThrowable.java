/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="LOGGER_THROWABLE"
 */
public class LogEventThrowable implements Serializable {
	private int throwable_ID;
	private int t_position;
	private String message;
	private int log_ID;
	
	private LogEvent logevent;

	/**
	 * @return Returns the message.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *          The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the t_position.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getT_position() {
		return t_position;
	}

	/**
	 * @param t_position
	 *          The t_position to set.
	 */
	public void setT_position(int t_position) {
		this.t_position = t_position;
	}

	/**
	 * @return Returns the throwable_ID.
	 * 
	 * @hibernate.id generator-class="native" column="throwable_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Logger_Throwable_0"
	 *  
	 */
	public int getThrowable_ID() {
		return throwable_ID;
	}

	/**
	 * @param throwable_ID
	 *          The throwable_ID to set.
	 */
	public void setThrowable_ID(int throwable_ID) {
		this.throwable_ID = throwable_ID;
	}
	
	
	/**
	 * @return Returns the logevent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.LogEvent"
	 *                        column="log_ID" not-null="true"
	 */
	public LogEvent getLogevent() {
		return logevent;
	}
	/**
	 * @param logevent The logevent to set.
	 */
	public void setLogevent(LogEvent logevent) {
		this.logevent = logevent;
	}
}