package aero.nettracer.serviceprovider.wt_1_0.services;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.exceptions.ConfigurationException;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerServiceManagers;

public class ServicesManager {
	
	private static ServicesManager instance = null;
	
	public static synchronized ServicesManager getInstance() {
		if (instance == null) {
			instance = new ServicesManager();
		}
		return instance;

	}
	
	public WorldTracerResponse performAction(WorldTracerActionDTO dto) throws UserNotAuthorizedException, ConfigurationException {

		AbstractServiceManager svcMgr = null;
		
		// Authorize
		if (dto.getUser() == null || (!dto.isAuthCheckPerformed() && !ServiceUtilities.authorize(dto.getUser(), PermissionType.WORLDTRACER))) {
			throw new UserNotAuthorizedException();
		}
		
		// Determine Destination Service Manager
		ParameterType actionType = dto.getType().getParameterType();
		if (!dto.getUser().getProfile().getParameters().containsKey(actionType)) {
			throw new ConfigurationException();
		} else {
			String serviceReference = dto.getUser().getProfile().getParameters().get(actionType);
			svcMgr = WorldTracerServiceManagers.valueOf(serviceReference).getServiceManager();
		}
		
		// Send to Service Manager
		return svcMgr.process(dto);
	}

}
