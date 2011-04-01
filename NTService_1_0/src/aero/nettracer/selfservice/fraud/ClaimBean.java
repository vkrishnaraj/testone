package aero.nettracer.selfservice.fraud;

import javax.ejb.Stateless;


@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome{

	public String echoTest(String s){
		return "echo: " + s;
	}
	
}
