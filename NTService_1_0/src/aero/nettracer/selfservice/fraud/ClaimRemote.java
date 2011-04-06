package aero.nettracer.selfservice.fraud;

import javax.ejb.Remote;

import org.jboss.ejb3.annotation.RemoteBinding;

import aero.nettracer.fs.model.FsClaim;



//@RemoteBinding(clientBindUrl="sslsocket://127.0.0.1:4850")
@Remote
public interface ClaimRemote {
	public String echoTest(String s);
	public boolean insertClaim(FsClaim claim);
}
