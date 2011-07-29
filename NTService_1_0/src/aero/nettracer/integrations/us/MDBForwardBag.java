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
import org.jboss.ejb3.annotation.ResourceAdapter;

import aero.nettracer.integrations.us.scanners.SendForward;
import aero.nettracer.integrations.us.scanners.data.Forward;

@MessageDriven(name = "MDBForwardBag", activationConfig = { 
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue") 
})
@TransactionManagement(value= TransactionManagementType.CONTAINER)
@TransactionAttribute(value= TransactionAttributeType.REQUIRED)
@ResourceAdapter("hornetq-ra.rar")
public class MDBForwardBag implements MessageListener {
	
	private static final String FORWARD_MESSAGE_RECEIVED = "Forward message received...";
	Logger logger = Logger.getLogger(MDBForwardBag.class);
	
	
	
	@Override
	public void onMessage(Message message) {
		logger.info(FORWARD_MESSAGE_RECEIVED);
		
		Connection conn = null;

	
		try {
			if (message instanceof ObjectMessage) {

				ObjectMessage objectMessage = (ObjectMessage) message;

				objectMessage.getObject();
				
				List<Forward> forwards = (List<Forward>) objectMessage.getObject();
				
				SendForward f = new SendForward();
				if (forwards.size() > 0) {
					f.sendForwards(forwards);
				} else {
					logger.info("Empty forward list... no items being forwarded...");
				}

			}
	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception encountered: ", e);
		} finally {
			
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
