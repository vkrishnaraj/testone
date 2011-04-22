//package fs;
//
//import java.util.Date;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//
//public class ImportIncidentThread implements Runnable {
//	ArrayBlockingQueue<String> queue = null;
//	int totalSize = 0;
//	String hibernateAirline;
//	SessionFactory fraudFactory;
//	SessionFactory sessFactory;
//	Date startTime = null;
//	ConcurrentHashMap<String, Integer> completed = null;
//	public static Integer ref = new Integer(0);
//	private int myCount = 0;
//
//	public ImportIncidentThread(ArrayBlockingQueue<String> queue, int totalSize, String hibernateAirline, SessionFactory sess, SessionFactory fraudFactory, Date startTime,
//	    ConcurrentHashMap<String, Integer> completed) {
//		this.startTime = startTime;
//		this.queue = queue;
//		this.totalSize = totalSize;
//		this.hibernateAirline = hibernateAirline;
//		this.fraudFactory = fraudFactory;
//		this.sessFactory = sess;
//		this.completed = completed;
//	}
//
//	@Override
//	public void run() {
//		Session sess = null;
//		Session fraudSess = null;
//
//		while (true) {
//
//			if (sess == null) {
//				sess = sessFactory.openSession();
//				fraudSess = fraudFactory.openSession();
//			}
//
//			try {
//
//				String incidentId = queue.take();
//				myCount++;
//				try {
//					
//					ImportIncident.importIncident(incidentId, sess, fraudSess);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				completed.put(incidentId, ref);
//				int i = completed.size();
//				if (i % 50 == 0) {
//					Date nowTime = new Date();
//					System.out.println(incidentId);
//					double percComplete = ((double) i / (double) totalSize * 100);
//					long timeElapsed = (nowTime.getTime() - startTime.getTime()) / 1000;
//					double percentRemaining = 100 - percComplete;
//					double secondsToComplete = (double) timeElapsed / percComplete * percentRemaining;
//					System.out.println(hibernateAirline + " percent complete: " + (i / totalSize * 100) + " (" + i + "/" + totalSize + ")  Minutes remaining: " + secondsToComplete / 60);
//					
//					if (i % 200 == 0) {
//						sess.close();
//						sess = null;
//						fraudSess.close();
//						fraudSess = null;
//						myCount = 0;
//						System.out.println("Cleansing Thread's Hibernate Sessions...");
//					}
//				}
//
//			} catch (Exception ex) {
//
//			}
//
//		}
//
//	}
//}
