package aero.nettracer.serviceprovider.wt_1_0.dto;

import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.ISharesServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.webservices.WTWebServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.WtrWebServiceManager;

public enum WorldTracerServiceManagers {
	WTRWEB {
		public AbstractServiceManager getServiceManager() {
			return WtrWebServiceManager.getInstance();
		}
	}, 
	
	WEBSERVICES {
		public AbstractServiceManager getServiceManager() {
			return WTWebServiceManager.getInstance();
		}
	}, 
	
	I_SABRE {
		public AbstractServiceManager getServiceManager() {
			return ISharesServiceManager.getInstance();
		}
	}, 
	
	TYPE_A{
		public AbstractServiceManager getServiceManager() {
			return null;
		}
	}, 
	
	TYPE_B{
		public AbstractServiceManager getServiceManager() {
			return null;
		}
	}, 
	
	TYPE_X{
		public AbstractServiceManager getServiceManager() {
			return null;
		}
	};
	
	abstract public AbstractServiceManager getServiceManager();
}
