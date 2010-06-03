import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument;
import aero.nettracer.serviceprovider.wt_1_0.WorldTracerServiceStub;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;

import com.bagnet.nettracer.wt.WorldTracerException;


public class Test {
	private static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	
	@org.junit.Test
	public void getActionFileCounts(
			) throws WorldTracerException {
		String companyCode = "US"; String wtStation = "XAX";
		try {
			WorldTracerServiceStub stub = new WorldTracerServiceStub("endpoint");
			
			RequestHeader header = new RequestHeader();
			header.setUsername("username");
			header.setPassword("password");
			
			ActionFileRequestData data = new ActionFileRequestData();
			data.setAirline(companyCode);
			data.setStation(wtStation);		
			
			GetActionFileCountsDocument d = GetActionFileCountsDocument.Factory.newInstance();
			GetActionFileCounts c = d.addNewGetActionFileCounts();
			

			c.setHeader(mapper.map(header,aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(data,aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData.class));
			stub.getActionFileCounts(d);
			
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	@org.junit.Test
	public void t1() {
		int[] result = new int[7];
		int i = 1;
	}
	
}
