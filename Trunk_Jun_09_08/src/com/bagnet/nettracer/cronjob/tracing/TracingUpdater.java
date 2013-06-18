package com.bagnet.nettracer.cronjob.tracing;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.cronjob.tracing.dto.SettingsDTO;
import com.bagnet.nettracer.cronjob.tracing.dto.UpdateDTO;
import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class TracingUpdater implements Runnable {

	private static final int BATCH_SIZE = 10;
	private static final String UPDATE_SQL = "UPDATE INCIDENT SET ohd_lasttraced = :lastTraced where incident_ID = :incidentId";
	private final Logger logger;

	private ArrayBlockingQueue<UpdateDTO> queue;
	private Session sess;
	private SQLQuery updateQuery;
	private SettingsDTO settings;

	public TracingUpdater(ArrayBlockingQueue<UpdateDTO> queue, SettingsDTO settings) {
		this.queue = queue;
		this.settings = settings;
		this.logger = settings.getLogger();

	}

	public void run() {
		boolean keepGoing = true;
		int currentCount = 0;
		this.sess = null;
		this.updateQuery = null;
		Transaction trans = null;

		while (keepGoing) {
			try {
				UpdateDTO updateDto = queue.take();
				if (sess == null) {
					sess = HibernateWrapper.getSession().openSession();
					updateQuery = sess.createSQLQuery(UPDATE_SQL);
				}
				if (updateDto != null) {
					trans = sess.beginTransaction();
					updateQuery.setTimestamp("lastTraced", updateDto
							.getOhdLastTraced());
					updateQuery.setString("incidentId", updateDto
							.getIncidentId());

					updateQuery.executeUpdate();
					trans.commit();
					updateDto = null;
				} else {
					logger.info("got a null dto in the update queue");
					continue;
				}
			} catch (Throwable t) {
				logger.error("error trying to update incident last traced, resetting session", t);
				if(trans != null) {
					trans.rollback();
				}
			} finally {
				if (sess != null) {
					sess.close();
					sess = null;
					updateQuery = null;
				}
			}
		}
	}

}
