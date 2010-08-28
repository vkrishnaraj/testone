package aero.nettracer.integrations.us.scanners;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import aero.nettracer.integrations.us.scanners.data.Forward;
import aero.nettracer.integrations.us.scanners.data.Segment;
import aero.nettracer.serviceprovider.common.dao.UserDao;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.usairways.www.scans.wsdl.ForwardType;
import com.usairways.www.scans.wsdl.NTForwardsStub;
import com.usairways.www.scans.wsdl.PostForwardsDocument;
import com.usairways.www.scans.wsdl.SegmentType;
import com.usairways.www.scans.wsdl.ForwardType.Segments;
import com.usairways.www.scans.wsdl.PostForwardsDocument.PostForwards;

public class SendForward {
	private static Logger logger = Logger.getLogger(SendForward.class);
	private static String endpoint = null;

	public void sendForwards(List<Forward> forwards) {
		try {
			
			if (endpoint == null) {
				Session sess = HibernateWrapper.getSession().openSession();
			
				User user = UserDao.getByUsername(sess, "usairways");
				sess.close();
				endpoint = user.getProfile().getParameters().get(ParameterType.FWD_NOTIFICATION_ENDPOINT);
			}
			
			NTForwardsStub stub = new NTForwardsStub(endpoint);

			PostForwardsDocument d = PostForwardsDocument.Factory.newInstance();
			PostForwards e = d.addNewPostForwards();

			for (Forward fw : forwards) {
				ForwardType f = e.addNewForward();
				f.setComment(fw.getComment());
				f.setOnHandId(fw.getOnHandId());
				f.setTag(fw.getTagNumber());
				
				Segments g = f.addNewSegments();
				f.setSegments(g);

				for (Segment s : fw.getSegments()) {
					SegmentType seg = g.addNewSegment();
					BeanUtils.copyProperties(seg, s);
				}
			}

			logger.info(d);
			stub.postForwards(d);

		} catch (AxisFault e) {
			// Output errors for logging purposes but otherwise ignore.
			logger.error("Error: ", e);
		} catch (RemoteException e) {
			// Output errors for logging purposes but otherwise ignore.
			logger.error("Error: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ", e);
		} catch (InvocationTargetException e) {
			logger.error("Error: ", e);
		}

	}
}
