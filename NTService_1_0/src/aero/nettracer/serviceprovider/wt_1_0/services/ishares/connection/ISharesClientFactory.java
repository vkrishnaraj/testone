package aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.pool.BasePoolableObjectFactory;

import aero.nettracer.serviceprovider.common.db.WorldTracerISharesAccount;



public class ISharesClientFactory extends BasePoolableObjectFactory{
	
	public static final int ALLOWED_MILLIS_WITH_NOACTIVITIY = 15*60*1000; // 20 Minutes = 1200000
	private List<WorldTracerISharesAccount> accounts = null;
	private int itemsUsed = 0;

	
	public ISharesClientFactory(List<WorldTracerISharesAccount> accounts) {
		this.accounts = accounts;
	}

	private synchronized WorldTracerISharesAccount getNextItem() {
		++itemsUsed;
		if (itemsUsed > accounts.size()) {
			throw new RuntimeException("Cannot create more items");
		}
		return accounts.get(itemsUsed - 1);
	}	


	@Override
	public synchronized Object makeObject() throws Exception {
		WorldTracerISharesAccount wta = getNextItem();
		ISharesHttpClient connection = new ISharesHttpClient(wta);
		return connection;
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		ISharesHttpClient connection = (ISharesHttpClient) obj;
		
		GregorianCalendar cal = new GregorianCalendar();
		long diff = cal.getTimeInMillis() - connection.getLastUsed().getTimeInMillis();
		
		if (diff > ALLOWED_MILLIS_WITH_NOACTIVITIY) {
			connection.setValidConnection(false);
		}
	}

	
}
