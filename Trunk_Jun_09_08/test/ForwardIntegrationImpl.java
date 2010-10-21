

import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import aero.nettracer.integrations.us.scanners.data.Forward;

public class ForwardIntegrationImpl {
	private static Logger logger = Logger.getLogger(ForwardIntegrationImpl.class);

	
	@org.junit.Test
	public void sendMessage() {
		ArrayList<Forward> payload = new ArrayList<Forward> ();
		Forward f = new Forward();
		f.setComment("COMMENT");
		payload.add(f);
		logger.info("Setting up...");
		Connection connection = null;
		InitialContext initialContext = null;

		try {
			// Step 1. Create an initial context to perform the JNDI lookup.
			initialContext = new InitialContext();

			// Step 2. Perfom a lookup on the queue
			Queue queue = (Queue) initialContext.lookup("/queue/testQueue");

			// Step 3. Perform a lookup on the Connection Factory
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("/ConnectionFactory");

			// Step 4.Create a JMS Connection
			connection = cf.createConnection();

			// Step 5. Create a JMS Session
			Session s = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 6. Create a JMS Message Producer
			MessageProducer producer = s.createProducer(queue);

			// Step 7. Create a Text Message
			ObjectMessage message = s.createObjectMessage(payload);

			// Step 8. Send the Message
			logger.info("About to send message...");
			producer.send(message);
			logger.info("Message sent...");

			Thread.sleep(10 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Step 19. Be sure to close our JMS resources!
				if (initialContext != null) {
					initialContext.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
