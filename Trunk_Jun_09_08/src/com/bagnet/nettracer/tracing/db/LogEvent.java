/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="Logs"
 */
public class LogEvent implements Serializable {
	private int log_ID;
	private String loglevel;
	private String message;
	private String className;
	private String fileName;
	private String lineNumber;
	private String methodName;
	private String loggerName;
	private Date logTime;
	private Date logDate;
	private String threadName;
	private List loggingEventThrowableWrapper = new ArrayList();

	public void addThrowableMessage(int position, String throwableMessage) {
		LogEventThrowable letw = new LogEventThrowable();
		letw.setT_position(position);
		letw.setMessage(throwableMessage);
		loggingEventThrowableWrapper.add(letw);
	}

	/**
	 * @return Returns the className.
	 * 
	 * @hibernate.property type="string" column="class_name" not-null="true"
	 *                     length="255"
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *          The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return Returns the fileName.
	 * 
	 * @hibernate.property type="string" column="file_name" not-null="true"
	 *                     length="255"
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *          The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the level.
	 * 
	 * @hibernate.property type="string" column="log_level" not-null="true"
	 *                     length="7"
	 */
	public String getLoglevel() {
		return loglevel;
	}

	/**
	 * @param level
	 *          The level to set.
	 */
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	/**
	 * @return Returns the lineNumber.
	 * 
	 * @hibernate.property type="string" column="line_number" not-null="true"
	 *                     length="5"
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *          The lineNumber to set.
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return Returns the log_ID.
	 * 
	 * @hibernate.id generator-class="native" column="log_ID" type="integer"
	 * @hibernate.generator-param name="sequence" value="Logs_0"
	 *  
	 */
	public int getLog_ID() {
		return log_ID;
	}

	/**
	 * @param log_ID
	 *          The log_ID to set.
	 */
	public void setLog_ID(int log_ID) {
		this.log_ID = log_ID;
	}

	/**
	 * @return Returns the logDate.
	 * 
	 * @hibernate.property type="date" column="log_date" not-null="true"
	 */
	public Date getLogDate() {
		return logDate;
	}

	/**
	 * @param logDate
	 *          The logDate to set.
	 */
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	/**
	 * @return Returns the loggerName.
	 * 
	 * @hibernate.property type="string" column="logger_name" not-null="true"
	 *                     length="255"
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * @param loggerName
	 *          The loggerName to set.
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	/**
	 * @return Returns the loggingEventThrowableWrapper.
	 * @hibernate.list cascade="all" inverse="true" lazy="true"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.LogEventThrowable"
	 * @hibernate.index column="t_position"
	 * @hibernate.key column="log_ID"
	 *  
	 */
	public List getLoggingEventThrowableWrapper() {
		return loggingEventThrowableWrapper;
	}

	/**
	 * @param loggingEventThrowableWrapper
	 *          The loggingEventThrowableWrapper to set.
	 */
	public void setLoggingEventThrowableWrapper(List loggingEventThrowableWrapper) {
		this.loggingEventThrowableWrapper = loggingEventThrowableWrapper;
	}

	/**
	 * @return Returns the message.
	 * 
	 * @hibernate.property type="string" column="message" not-null="true"
	 *                     length="255"
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
	 * @return Returns the methodName.
	 * 
	 * @hibernate.property type="string" column="method_name" not-null="true"
	 *                     length="255"
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *          The methodName to set.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return Returns the logTime.
	 * 
	 * @hibernate.property type="time" column="log_time" not-null="true"
	 */
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime
	 *          The logTime to set.
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/**
	 * @return Returns the threadName.
	 * 
	 * @hibernate.property type="string" column="thread_name" not-null="true"
	 *                     length="255"
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName
	 *          The threadName to set.
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
}