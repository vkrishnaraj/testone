package aero.nettracer.lfc.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import aero.nettracer.lfc.service.ClientViewService;

@Component("helloWorld")
@Qualifier("helloWorld")
@Scope("request")
public class HelloWorldBean {
 
	private String name;
 
	@Autowired
	private ClientViewService clientViewService;
 
	public String convertNameToUpperCase() {
		name = clientViewService.convertToUpperCase(name);
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientViewService getClientViewService() {
		return clientViewService;
	}

	public void setClientViewService(ClientViewService clientViewService) {
		this.clientViewService = clientViewService;
	}
}
