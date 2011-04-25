package tools;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbqConsumer implements Runnable {
	public static Integer ref = new Integer(0);
	ArrayBlockingQueue<Object> queue;
	int totalSize;
	Date startTime;
	ConcurrentHashMap<String, Integer> completed;
	KeyProcessor keyProcessor = null;

	public KeyProcessor getKeyProcessor() {
		return keyProcessor;
	}

	public void setKeyProcessor(KeyProcessor keyProcessor) {
		this.keyProcessor = keyProcessor;
	}

	int myCount = 0;

	public ArrayBlockingQueue<Object> getQueue() {
		return queue;
	}

	public void setQueue(ArrayBlockingQueue<Object> queue) {
		this.queue = queue;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public ConcurrentHashMap<String, Integer> getCompleted() {
		return completed;
	}

	public void setCompleted(ConcurrentHashMap<String, Integer> completed) {
		this.completed = completed;
	}

	@Override
	public void run() {
		while (true) {
			try {

				Object itemToProcess = queue.take();
				myCount++;
				try {
					processItem(itemToProcess);
				} catch (Exception e) {
					e.printStackTrace();
				}
				completed.put(keyProcessor.generateKey(itemToProcess), ref);

				int i = completed.size();
				if (i % 50 == 0) {
					Date nowTime = new Date();
					double percComplete = ((double) i / (double) totalSize * 100);
					long timeElapsed = (nowTime.getTime() - startTime.getTime()) / 1000;
					double percentRemaining = 100 - percComplete;
					double secondsToComplete = (double) timeElapsed / percComplete * percentRemaining;
					System.out.println("Percent complete: " + (i / totalSize * 100) + " (" + i + "/" + totalSize
							+ ")  Minutes remaining: " + secondsToComplete / 60);

					if (i % 200 == 0) {
						cleanseSessions();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void processItem(Object itemToProcess);

	public abstract void cleanseSessions();

}
