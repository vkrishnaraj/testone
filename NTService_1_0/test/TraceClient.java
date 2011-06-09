import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;


public class TraceClient {
	public class ProducerThread implements Runnable{
		private ArrayBlockingQueue<Long> queue;

		public ProducerThread(ArrayBlockingQueue<Long>queue){
			this.queue = queue;
		}
		
		@Override
		public void run() {
			
			while(true){
				try{
					Long id = queue.take();
					Producer.matchFile(id, 1, false, true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String [] args){
		ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(10);
		
		TraceClient ta = new TraceClient();
		ProducerThread p1 = ta.new ProducerThread(queue);
		ProducerThread p2 = ta.new ProducerThread(queue);
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		t1.start();
		t2.start();
		
		String sql = "select file_id as id from fsclaim where airline = \'WN\'";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("id", Hibernate.LONG);
		List<Long> result = pq.list();
		sess.close();
		
		int i = 0;
		Date start = new Date();
		Date tick = new Date();
		for(Long id:result){
			i++;
			if(i%20 == 0){
				tick = new Date();
				double percentDone = i/result.size();
				long tpc = (long) ((tick.getTime() - start.getTime())/i);
				long timeLeft = tpc * (result.size() - i);
				System.out.println("" + (timeLeft/60000) + "   " + i + "/" + result.size());
			}
			try {
				queue.put(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
