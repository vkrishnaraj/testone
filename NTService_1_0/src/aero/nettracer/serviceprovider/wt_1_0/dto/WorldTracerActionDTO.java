package aero.nettracer.serviceprovider.wt_1_0.dto;

import java.util.HashMap;

import org.dozer.DozerBeanMapperSingletonWrapper;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.common.Parameter;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerConnection;

public class WorldTracerActionDTO {
	private WorldTracerConnection connection = null; 
	private WorldTracerActionType type;
	private User user;
	private Object payload;
	private boolean authCheckPerformed;
	private Parameter[] parameters = null;
	private HashMap<String, String> parameterHash = new HashMap<String, String>();
	
	public String getParameterValue(String key) {
		return parameterHash.get(key);
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}

	public WorldTracerActionDTO(WorldTracerActionType type, User user,
			Object payload, boolean authCheckPerformed, RequestHeader header) {
		this.user = user;
		this.payload = payload;
		this.type = type;
		this.authCheckPerformed = authCheckPerformed;
		
		if (header != null && header.getParametersArray() != null) {
			aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter[] array = header.getParametersArray();
			this.parameters = new Parameter[array.length];
			for (int i = 0; i< array.length; ++i) {
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter item = array[i];
				Parameter newItem = DozerBeanMapperSingletonWrapper.getInstance().map(item, Parameter.class);
				this.parameters[i] = newItem;
				parameterHash.put(newItem.getName(), newItem.getValue());
			}
		}
	}

	public WorldTracerActionType getType() {
		return type;
	}

	public void setType(WorldTracerActionType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public boolean isAuthCheckPerformed() {
		return authCheckPerformed;
	}

	public void setAuthCheckPerformed(boolean authCheckPerformed) {
		this.authCheckPerformed = authCheckPerformed;
	}

	public WorldTracerConnection getConnection() {
		return connection;
	}

	public void setConnection(WorldTracerConnection connection) {
		this.connection = connection;
	}
}
