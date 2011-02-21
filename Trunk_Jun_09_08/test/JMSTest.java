
import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.jms.QueueConnectionFactory;


public class JMSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
			//env.put(Context.PROVIDER_URL, "jndi.properties");
			
			InitialContext ctx = new InitialContext(env);

		//InitialContext ctx = new InitialContext();
		//ctx.addToEnvironment(propName, propVal);
		 // lookup the queue connection factory
	    QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("queue/connectionFactory");
	    Connection conn = connFactory.createConnection();
			
		ConnectionFactory myConnFactory;
		Queue myQueue = null;
		
		//myConnFactory = new com.sun.messaging.ConnectionFactory();
		

		
		Session mySess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//myQueue = new com.sun.messaging.Queue("world");
		
		MessageProducer myMsgProducer = mySess.createProducer(myQueue);
		
        TextMessage myTextMsg = mySess.createTextMessage();
        myTextMsg.setText("Hello World");
        System.out.println("Sending Message: " + myTextMsg.getText());
        myMsgProducer.send(myTextMsg);
		
        MessageConsumer myMsgConsumer = mySess.createConsumer(myQueue);
        
        conn.start();
        
        Message msg = myMsgConsumer.receive();
        
        if (msg instanceof TextMessage) {
            TextMessage txtMsg = (TextMessage) msg;
            System.out.println("Read Message: " + txtMsg.getText());
        }

 
        //Step 12:
        //Close the session and connection resources.
        mySess.close();
        conn.close();
        
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
