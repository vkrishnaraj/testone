package tools;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class AbqProcessor {
	
	public void processQueue(List<Object> list, int numChildThreads, Class threadClass, KeyProcessor keyProcessor,
			HashMap<String, Integer> alreadyProcessed) throws InstantiationException, IllegalAccessException {
		ArrayBlockingQueue<Object> abq = new ArrayBlockingQueue<Object>(10000);
		HashMap<String, Integer> alreadyImported = new HashMap<String, Integer>();
		ConcurrentHashMap<String, Integer> completed = new ConcurrentHashMap<String, Integer>(); 
		
		for (int i = 0; i < numChildThreads; ++i) {
			AbqConsumer thread = (AbqConsumer) threadClass.newInstance();
			thread.setQueue(abq);
			thread.setTotalSize(list.size());
			thread.setStartTime(new Date());
			thread.setCompleted(completed);
			thread.setKeyProcessor(keyProcessor);
			new Thread(thread).start();
		}

		// Add items to queue;
		for (int i = 0; i < list.size(); ++i) {

			while (abq.size() > 9000) {
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Object o = list.get(i);
			String key = keyProcessor.generateKey(o);
			if (!alreadyProcessed.containsKey(key)) {
				abq.add(o);

			}

		}

		while (abq.size() > 0) {
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("Final sleep of 60 seconds to guarantee processing complets");
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
