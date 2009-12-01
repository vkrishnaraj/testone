package aero;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.stream.XMLStreamException;


import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.jaxws.description.builder.WebServiceClientAnnot;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;
import org.junit.Test;

import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;

public class DelayedService {

    private static Policy loadPolicy(String name) throws XMLStreamException  {
        ClassLoader loader = WebServiceClientAnnot.class.getClassLoader();
        InputStream resource = loader.getResourceAsStream(name);
        StAXOMBuilder builder = new StAXOMBuilder(resource);
        return PolicyEngine.getPolicy(builder.getDocumentElement());
    }
    
	@Test
	public void test() throws RemoteException, XMLStreamException {
		String endpoint = "http://localhost:8080";
		
		
		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
		ServiceClient client = stub._getServiceClient();
		client.engageModule("rampart");
		
        Options options = client.getOptions();
        options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, loadPolicy("/policy.xml"));
        options.setUserName("libuser");
        options.setPassword("books");
        client.engageModule("rampart");

		
		WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
		d.addNewWTRCloseRecordsRQ().addNewPOS().addNewSource().addNewRequestorID().addNewCompanyName().setCode("US");
		
		try {
			System.out.println(d);
			stub.close(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
