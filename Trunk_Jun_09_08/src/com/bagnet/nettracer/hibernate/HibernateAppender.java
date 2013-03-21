/*
 * Created on Aug 13, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.hibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import com.bagnet.nettracer.tracing.db.LogEvent;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Aug 13, 2004
 */
/**
 * Log4J appender that uses Hibernate to store log events in a database. To use
 * this appender, you must provide two properties in the Log4J properties file:
 * 
 * <UL>
 * sessionServiceClass
 * </UL>
 * <UL>
 * loggingEventClass
 * </UL>
 * 
 * <P>
 * The sessionServiceClass must implement the
 * {@link HibernateAppenderSessionService}interface which provides the appender
 * with an open Hibernate session. Your implementation of this interface can
 * perform any additional activities such as registering audit interceptors if
 * required.
 * </P>
 * 
 * <P>
 * The loggingEventClass must implement the
 * {@link HibernateAppenderLoggingEvent}interface. Using an interface for the
 * logging event leaves your implementation free to extend any existing
 * persistence ancestor that you may already be using.
 * </P>
 * 
 * <P>
 * An example Log4J properties file to configure the HibernateAppender would be:
 * </P>
 * 
 * <code>
 * ### direct messages to Hibernate<BR/>
 * log4j.appender.hibernate=HibernateAppender<BR/>
 * log4j.appender.hibernate.sessionServiceClass=HibernateHelper<BR/>
 * log4j.appender.hibernate.loggingEventClass=LogEvent<BR/>
 * </code>
 * 
 * <P>
 * You can now write a Hibernate mapping file for the class specified as the
 * <code>loggingEventClass</code> to persist whichever parts of the logging
 * event that you require.
 * </P>
 * 
 * @author David Howe
 * 
 * @version 1.1
 * 
 */
public class HibernateAppender extends AppenderSkeleton implements Appender {
	private String sessionServiceClass;
	private String loggingEventClass;
	private static Vector buffer = new Vector();
	private static Boolean appending = Boolean.FALSE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	protected void append(LoggingEvent loggingEvent) {
		/*
		 * Ensure exclusive access to the buffer in case another thread is currently
		 * writing the buffer.
		 */
		synchronized (buffer) {
			// Add the current event into the buffer
			buffer.add(loggingEvent);
			/*
			 * Ensure exclusive access to the appending flag to guarantee that it
			 * doesn't change in between checking it's value and setting it
			 */
			synchronized (appending) {
				if (!appending.booleanValue()) {
					/*
					 * No other thread is appending to the log, so this thread can perform
					 * the append
					 */
					appending = Boolean.TRUE;
				} else {
					/*
					 * Another thread is already appending to the log and it will take
					 * care of emptying the buffer
					 */
					return;
				}
			}
		}
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			/*
			 * Ensure exclusive access to the buffer in case another thread is
			 * currently adding to the buffer.
			 */
			synchronized (buffer) {
				Iterator iter = buffer.iterator();
				LoggingEvent bufferLoggingEvent;
				LogEvent loggingEventWrapper = new LogEvent();
				/*
				 * Get the current buffer length. We only want to process events that
				 * are currently in the buffer. If events get added to the buffer after
				 * this point, they must have been caused by this loop, as we have
				 * synchronized on the buffer, so no other thread could be adding an
				 * event. Any events that get added to the buffer as a result of this
				 * loop will be discarded, as to attempt to process them will result in
				 * an infinite loop.
				 */
				int bufferLength = buffer.size();
				for (int i = 0; i < bufferLength; i++) {
					bufferLoggingEvent = (LoggingEvent) buffer.get(i);
					loggingEventWrapper.setMessage(bufferLoggingEvent.getRenderedMessage());
					loggingEventWrapper.setClassName(bufferLoggingEvent.getLocationInformation().getClassName());
					loggingEventWrapper.setFileName(bufferLoggingEvent.getLocationInformation().getFileName());
					loggingEventWrapper.setLineNumber(bufferLoggingEvent.getLocationInformation().getLineNumber());
					Date logDate = TracerDateTime.getGMTDate();
					logDate.setTime(bufferLoggingEvent.timeStamp);
					loggingEventWrapper.setLogDate(logDate);
					loggingEventWrapper.setLogTime(logDate);
					loggingEventWrapper.setLoggerName(bufferLoggingEvent.getLoggerName());
					loggingEventWrapper.setMethodName(bufferLoggingEvent.getLocationInformation().getMethodName());
					// Date startDate = new Date();
					// startDate.setTime(LoggingEvent.getStartTime());
					// loggingEventWrapper.setStartDate(startDate);
					loggingEventWrapper.setThreadName(bufferLoggingEvent.getThreadName());
					if (bufferLoggingEvent.getThrowableStrRep() != null) {
						for (int j = 0; j < bufferLoggingEvent.getThrowableStrRep().length; j++) {
							loggingEventWrapper.addThrowableMessage(j, bufferLoggingEvent.getThrowableStrRep()[j]);
						}
					}
					if (bufferLoggingEvent.equals(Level.ALL)) {
						loggingEventWrapper.setLoglevel("ALL");
					} else if (bufferLoggingEvent.getLevel().equals(Level.DEBUG)) {
						loggingEventWrapper.setLoglevel("DEBUG");
					} else if (bufferLoggingEvent.getLevel().equals(Level.ERROR)) {
						loggingEventWrapper.setLoglevel("ERROR");
					} else if (bufferLoggingEvent.getLevel().equals(Level.FATAL)) {
						loggingEventWrapper.setLoglevel("FATAL");
					} else if (bufferLoggingEvent.getLevel().equals(Level.INFO)) {
						loggingEventWrapper.setLoglevel("INFO");
					} else if (bufferLoggingEvent.getLevel().equals(Level.OFF)) {
						loggingEventWrapper.setLoglevel("OFF");
					} else if (bufferLoggingEvent.getLevel().equals(Level.WARN)) {
						loggingEventWrapper.setLoglevel("WARN");
					} else {
						loggingEventWrapper.setLoglevel("UNKNOWN");
					}
					session.save(loggingEventWrapper);
				}
				session.flush();
				buffer.clear();
				/*
				 * Ensure exclusive access to the appending flag - this really shouldn't
				 * be needed as the only other check on this flag is also synchronized
				 * on the buffer. We don't want to do this in the finally block as
				 * between here and the finally block we will not be synchronized on the
				 * buffer and another process could add an event to the buffer, but the
				 * appending flag will still be true, so that event would not get
				 * written until another log event triggers the buffer to be emptied.
				 */
				synchronized (appending) {
					appending = Boolean.FALSE;
				}
			}
		} catch (HibernateException he) {
			this.errorHandler.error("HibernateException", he, ErrorCode.GENERIC_FAILURE);
			// Reset the appending flag
			appending = Boolean.FALSE;
			return;
		} finally {
			if (session != null) session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	public void close() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout() {
		return false;
	}
}