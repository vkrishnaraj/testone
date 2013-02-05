package com;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnection;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.junit.Test;

public class JMSTesting {
	
    private static final String DEFAULT_CONNECTION_FACTORY = "ConnectionFactory";
    private static final String DEFAULT_DESTINATION = "testQueue";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "nettracer";
    private static final String DEFAULT_PASSWORD = "ntMSGpass1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "remote://localhost:4447";
	
	@Test
	public void mytest() throws Exception {
	      Connection connection = null;
	      InitialContext initialContext = null;
	      try
	      {
//				Properties env = new Properties();
//	            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//	            env.put(Context.PROVIDER_URL, PROVIDER_URL);
//	            env.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
//	            env.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
//	            env.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
//	            env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
//	            env.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "true");
//				// Step 1. Create an initial context to perform the JNDI lookup.
//				initialContext = new InitialContext(env);

				// Step 2. Perfom a lookup on the queue
				Queue queue = HornetQJMSClient.createQueue(DEFAULT_DESTINATION);
				
				TransportConfiguration tc = new TransportConfiguration(NettyConnectorFactory.class.getName());

				// Step 3. Perform a lookup on the Connection Factory
				ConnectionFactory cf = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, tc);

				// Step 4.Create a JMS Connection
				connection = cf.createConnection(DEFAULT_USERNAME, DEFAULT_PASSWORD);

	         //Step 5. Create a JMS Session
	         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	         //Step 6. Create a JMS Message Producer
	         MessageProducer producer = session.createProducer(queue);

	         //Step 7. Create a Text Message
	         TextMessage message = session.createTextMessage("This is a text message");

	         System.out.println("Sent message: " + message.getText());

	         //Step 8. Send the Message
	         producer.send(message);

	         //Step 15. We lookup the reply queue
//	         queue = (Queue) initialContext.lookup("/queue/replyQueue");
//
//	         //Step 16. We create a JMS message consumer
//	         MessageConsumer messageConsumer = session.createConsumer(queue);
//
//	         //Step 17. We start the connedction so we can receive messages
//	         connection.start();
//
//	         //Step 18. We receive the message and print it out
//	         message = (TextMessage) messageConsumer.receive(5000);
//
//	         System.out.println("message.getText() = " + message.getText());

	      }
	      finally
	      {
	         //Step 19. Be sure to close our JMS resources!
	         if (initialContext != null)
	         {
	            initialContext.close();
	         }
	         if(connection != null)
	         {
	            connection.close();
	         }
	      }
		
	}
}
