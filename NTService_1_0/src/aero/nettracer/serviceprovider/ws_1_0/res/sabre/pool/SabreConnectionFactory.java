package aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

public class SabreConnectionFactory extends BasePoolableObjectFactory {

	private List<SabreConnection> connections = null;
	private volatile int itemsUsed = 0;
	private static Logger logger = Logger.getLogger(SabreConnectionFactory.class);

	public SabreConnectionFactory(long profile, List<SabreConnection> connections) {
		this.connections = connections;
	}

	private synchronized SabreConnection getNextItem() {
		++itemsUsed;
		if (itemsUsed > connections.size()) {
			throw new RuntimeException("Cannot create more items");
		}
		return connections.get(itemsUsed - 1);
	}

	@Override
	public synchronized Object makeObject() throws Exception {
		SabreConnection connection = getNextItem();
		return connection;
	}
	
	@Override
	public void activateObject(Object obj) throws Exception {
		logger.debug("Activating Object...");
	}
	
	@Override
	public void passivateObject(Object obj) throws Exception {		
		logger.debug("Passivating Object...");
	}
	
	@Override
	public boolean validateObject(Object obj){
		logger.debug("Validating object...");
		// Need to review the following code if activated
		// to ensure that we are properly setting the timeouts.
//		try {
//			Reservation.validateSession((SabreConnection)obj);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//			return false;
//		}
		return true;
	}

}
