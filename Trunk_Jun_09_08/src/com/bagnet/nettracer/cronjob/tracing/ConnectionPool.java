/*
 * Created on Jan 19, 2005
 * 
 * matt
 */
package com.bagnet.nettracer.cronjob.tracing;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class ConnectionPool {
	private String URI;
	private String dbuser;
	private String dbpass;
	private String drivername;
	private int maxconn;
	private static int clients;
	private static ConnectionPool cp;
	private ArrayList freeConnections = new ArrayList();
	private static Logger logger = Logger.getLogger(ConnectionPool.class);

	static synchronized public ConnectionPool getInstance(String URI, String dbuser, String dbpass,
			String drivername, int maxconn) {
		if (cp == null) {
			cp = new ConnectionPool(URI, dbuser, dbpass, drivername, maxconn);
		}
		clients++;
		return cp;
	}

	private ConnectionPool(String URI, String dbuser, String dbpass, String drivername, int maxconn) {
		this.URI = URI;
		this.dbuser = dbuser;
		this.dbpass = dbpass;
		this.drivername = drivername;
		this.maxconn = maxconn;
		loadJDBCDriver();
	}

	private void loadJDBCDriver() {
		try {
			Driver driver = (Driver) Class.forName(this.drivername).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			logger.error("Can't load/register JDBC driver ");
		}
	}

	public synchronized Connection getConnection() {
		Connection rescon = null;
		if (!this.freeConnections.isEmpty()) {
			rescon = (Connection) this.freeConnections.get(this.freeConnections.size() - 1);
			this.freeConnections.remove(rescon);
			try {
				if (rescon.isClosed()) {
					logger.warn("Removed closed connection!");
					// Try again recursively
					rescon = getConnection();
				}
			} catch (SQLException e) {
				logger.warn("Removed closed connection!");
				// Try again recursively
				rescon = getConnection();
			} catch (Exception e) {
				logger.warn("Removed closed connection!");
				// Try again recursively
				rescon = getConnection();
			}
		} else {
			rescon = createConnection();
		}
		return rescon;
	}

	private Connection createConnection() {
		Connection rescon = null;
		try {
			if (this.dbuser == null) {
				rescon = DriverManager.getConnection(this.URI);
			} else {
				rescon = DriverManager.getConnection(this.URI, this.dbuser, this.dbpass);
			}
			// new connection in connection pool created
		} catch (SQLException e) {
			logger.error("Cannot create a new connection!");
			rescon = null;
		}
		return rescon;
	}

	public synchronized void returnConnection(Connection con) {
		if ((con != null) && (this.freeConnections.size() <= this.maxconn)) {
			this.freeConnections.add(con);
		}
	}

	/* close all connections */
	public synchronized void release() {
		Iterator allc = this.freeConnections.iterator();
		while (allc.hasNext()) {
			Connection con = (Connection) allc.next();
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Cannot close connection! (Probably already closed?)");
			}
		}
		this.freeConnections.clear();
	}
}