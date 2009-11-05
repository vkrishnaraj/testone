package aero.nettracer.serviceprovider.wt_1_0.dto;

import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.WtrWebServiceManager;

public enum WorldTracerServiceManagers {
	WTRWEB {
		public AbstractServiceManager getServiceManager() {
			return WtrWebServiceManager.getInstance();
		}
	}, 
	
	WEBSERVICES {
		public AbstractServiceManager getServiceManager() {
//			return WTWebServiceManager.getInstance();
			return null;
		}
	}, 
	
	I_SABRE {
		public AbstractServiceManager getServiceManager() {
//			return ISharesServiceManager.getInstance();
			return null;
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
