package aero.nettracer.integrations.us;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import aero.nettracer.integrations.us.scanners.SendForward;
import aero.nettracer.integrations.us.scanners.data.Forward;

@MessageDriven(name = "MDBForwardBag", activationConfig = { 
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue") 
})
@TransactionManagement(value= TransactionManagementType.CONTAINER)
@TransactionAttribute(value= TransactionAttributeType.REQUIRED)
public class MDBForwardBag implements MessageListener {
	
	private static final String FORWARD_MESSAGE_RECEIVED = "Forward message received...";
	Logger logger = Logger.getLogger(MDBForwardBag.class);
	
	
	
	@Override
	public void onMessage(Message message) {
		System.out.println(FORWARD_MESSAGE_RECEIVED);
		logger.info(FORWARD_MESSAGE_RECEIVED);
		
		Connection conn = null;

		long singleCallTime = 0;

		try {
			long singleCallStart = System.currentTimeMillis();
			if (message instanceof ObjectMessage) {

				ObjectMessage objectMessage = (ObjectMessage) message;

				objectMessage.getObject();
				
				List<Forward> forwards = (List<Forward>) objectMessage.getObject();
				
				SendForward f = new SendForward();
				f.sendForwards(forwards);

			}
			long singleCallEnd = System.currentTimeMillis();
			singleCallTime = singleCallEnd - singleCallStart;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception encountered: ", e);
		} finally {
			
			System.out.println("This single test took approximately: " + singleCallTime + " ms");
			if (conn != null) {
				try {
					conn.close();
				} catch (JMSException e) {
				}
			}
		}
		logger.info("Message processed...");
	}
}
