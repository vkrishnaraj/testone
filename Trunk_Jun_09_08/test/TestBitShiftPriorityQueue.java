import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;


public class TestBitShiftPriorityQueue {

	Vector<Queue<String>> vector = new Vector<Queue<String>>();
	
	@org.junit.Test
	public void testBitShiftPriorityQueue(){
		// RANDOMIZED SETUP
		
		int numVec = (int)(Math.random() * 5) + 5;
		System.out.println("VECTORS: " + numVec);
		int i;
		for (i = 1; i <= numVec; i++) {
			addQueue(i);
		}
		
		// END SETUP
		
		// WORK QUEUE
		int count = 1;
		int totalCount = 1;
		while (vector.size() > 0) {
			// THIS IF BLOCK SIMULATES MORE TRACES COMING IN WHILE CONSUMERS ARE ALREADY WORKING ACTIVE TRACES.
			if (totalCount % 150 == 0) {
				addQueue(i);
				i++;
			}
			// COUNT MUST ALWAYS BE BETWEEN 1 AND 2^(X-1) WHERE X IS VECTOR SIZE.
			if (count <= Math.pow(2, (vector.size() - 1))) { 
				popNext(getIndex(count));
				count++;
				totalCount++;
			} else {
				count = 1;
			}
		}
		// END WORK QUEUE
	}
	
	// This method creates a Queue and adds it to the vector.
	// Also starts a thread to simulate producer adding more potentials to the queue.
	private void addQueue(int i) {
		Queue<String> strings = new ConcurrentLinkedQueue<String>();
		int numQueue = (int)(Math.random()*50) + 10;
		System.out.println("QUEUE" + i + ": " + numQueue);
		for (int j = 1; j <= numQueue; j++) {
			strings.add(i + "." + j + "String");
		}
		vector.add(strings);
		new Thread(new QueueWatcher(strings, i)).start();
	}
	
	// This method represents the Consumer
	private void popNext(int idx) {
		try {
			Thread.sleep(10); // LETS JUST SAY IT TAKES 10 MS FOR A CONSUMER TO RUN A SINGLE TRACE
		} catch (Exception e) {
		}
		String toPrint = vector.get(idx).poll();
		if (toPrint == null) {
			vector.remove(idx); // WE PROBABLY WOULDN'T HAVE THE CONSUMER DO THIS IF QUEUE IS EMPTY. 
			                    // MAYBE HAVE PRODUCER DO IT ON SUCCESS CRITERIA?
		} else {
			System.out.println("STRING: " + toPrint);
		}
	}
	
	// This gets the proper index for the Vector for the modified round robin
	private int getIndex(int count) {
		if (count < 1) {
			return 0;
		}
		int p;
		for (p=1; p < 32 && count == (count>>p) <<p; p++) {}
		return p - 1;
	}
	
	// This Thread is here simply to simulate threaded producers adding more stuff to trace to the queue.
	public class QueueWatcher implements Runnable {

	    private volatile Queue<String> strings;
	    private volatile int i;
	    private volatile int size;
	    private volatile boolean addMore;

	    public QueueWatcher(Queue<String> strings, int i){
	        this.strings = strings;
	        this.i = i;
	        this.size = strings.size();
	        this.addMore = size < 35;
	        
	    }

	    public void run(){
	    	int numAdds = 0;
	    	while (addMore) {
	    		if (strings.size() < 10) {
	    			int numQueue = (int)(Math.random()*50) + 10;
	    			System.out.println("QUEUE" + i + ": " + (size + numQueue) + " :: " + numQueue);
	    			for (int j = 1; j <= numQueue; j++) {
	    				strings.add(i + "." + (size + j) + "String");
	    			}
	    			size = size + numQueue;
	    			if (strings.size() > (45 - (numAdds * 5))) {
	    				addMore = false;
	    			}
	    			numAdds++;
	    		}
	    	}
	    	System.out.println("DONE ADDING QUEUE" + i);
	    }
	}
}
