package aero.nettracer.fs.utilities.tracing;

import org.junit.Test;

import aero.nettracer.fs.utilities.tracing.ConsumerQueueManager;
import static org.junit.Assert.*;


public class ConsumerQueueManagerTest{
	@Test
	public void getNextIndexTest(){
		assertTrue(ConsumerQueueManager.getNextIndex(0,0) == -1);
		assertTrue(ConsumerQueueManager.getNextIndex(0,1) == -1);
		assertTrue(ConsumerQueueManager.getNextIndex(1,0) == 0);
		assertTrue(ConsumerQueueManager.getNextIndex(1,1) == 0);
		assertTrue(ConsumerQueueManager.getNextIndex(2,0) == 1);
		assertTrue(ConsumerQueueManager.getNextIndex(2,1) == 0);
		assertTrue(ConsumerQueueManager.getNextIndex(4,0) == 1);
		assertTrue(ConsumerQueueManager.getNextIndex(4,1) == 2);
		assertTrue(ConsumerQueueManager.getNextIndex(4,2) == 3);
		assertTrue(ConsumerQueueManager.getNextIndex(4,3) == 0);
	}
}