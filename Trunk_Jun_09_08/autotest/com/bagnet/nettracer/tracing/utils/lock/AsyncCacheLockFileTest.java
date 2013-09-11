package com.bagnet.nettracer.tracing.utils.lock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;

public class AsyncCacheLockFileTest {
	
	static AsyncCacheLockFile aclf;
	static CacheLockFile clfMock;
	
	/**
	 * Since the Async consumer threads/queue are statically defined, we must also statically define the mock objects.
	 * Otherwise you would have to place all test cases in one massive test case.
	 */
	public AsyncCacheLockFileTest(){
		if(aclf == null){
			clfMock = mock(CacheLockFile.class);
			aclf = new AsyncCacheLockFile(clfMock);
			aclf.init();
		}
	}
	
	
	/**
	 * Testing the basic queue add/consume functionality of lockIncident.
	 */
	@Test
	public void lockIncidentTest(){
		/*
		 * Testing happy path returning true
		 */
		Agent agentTrue = new Agent();
		Incident incidentTrue = new Incident();
		when(clfMock.lockIncident(agentTrue, incidentTrue)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				return true;
			}
		});
		assertTrue(aclf.lockIncident(agentTrue, incidentTrue));
		
		/*
		 * Testing happy path returning false
		 */
		Agent agentFalse = new Agent();
		Incident incidentFalse = new Incident();
		when(clfMock.lockIncident(agentFalse, incidentFalse)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				return false;
			}
		});
		assertFalse(aclf.lockIncident(agentFalse, incidentFalse));
		
		
		/*
		 * Testing a forced timeout.  Even though the mock method returns true,
		 * the AsyncCacheLockFile class is to return false in the event of a timeout.
		 */
		Agent agentTimeout = new Agent();
		Incident incidentTimeout = new Incident();
		when(clfMock.lockIncident(agentTimeout, incidentTimeout)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				try {
					Thread.sleep(AsyncCacheLockFile.MAX_WAIT_TIME + 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
	}

	
	/**
	 * Testing the basic queue add/consume functionality of getLockActionMessage.
	 */
	@Test
	public void getLockActionMessageTest(){
		Agent agent = new Agent();
		
		/*
		 * Testing happy path returning a non-null response
		 */
		String incidentIdSuccess = "success";
		when(clfMock.getLockActionMessages(incidentIdSuccess, agent)).thenAnswer(new Answer<List<ActionMessage>>(){
			@Override
			public List<ActionMessage> answer(InvocationOnMock invocation){
				return new ArrayList<ActionMessage>();
			}
		});
		assertTrue(aclf.getLockActionMessages(incidentIdSuccess, agent) != null);
		
		
		/*
		 * Testing a forced timeout.  Even though the mock method returns true,
		 * the AsyncCacheLockFile class is to return false in the event of a timeout.
		 */
		String incidentIdTimeout = "timeout";
		when(clfMock.getLockActionMessages(incidentIdTimeout, agent)).thenAnswer(new Answer<List<ActionMessage>>(){
			@Override
			public List<ActionMessage> answer(InvocationOnMock invocation){
				try {
					Thread.sleep(AsyncCacheLockFile.MAX_WAIT_TIME + 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new ArrayList<ActionMessage>();
			}
		});
		assertTrue(aclf.getLockActionMessages(incidentIdTimeout, agent) == null);
	}
	
	/**
	 * Testing that the notification of one thread does not wake the other thread.
	 */
	@Test
	public void lockTest(){
		drainQueue();
		
		Agent agentTrue = new Agent();
		Incident incidentTrue = new Incident();
		when(clfMock.lockIncident(agentTrue, incidentTrue)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				return true;
			}
		});
		
		Agent agentTimeout = new Agent();
		Incident incidentTimeout = new Incident();
		when(clfMock.lockIncident(agentTimeout, incidentTimeout)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				try {
					Thread.sleep(AsyncCacheLockFile.MAX_WAIT_TIME + 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		
		QueueItem item1 = new QueueItem();
		CacheLockFileThreadTest c1 = new CacheLockFileThreadTest(aclf, incidentTimeout, agentTimeout, item1);
		Thread t1 = new Thread(c1);
		t1.start();
		
		QueueItem item2 = new QueueItem();
		CacheLockFileThreadTest c2 = new CacheLockFileThreadTest(aclf, incidentTrue, agentTrue, item2);
		Thread t2 = new Thread(c2);
		t2.start();
		
		drainQueue();
		
		assertFalse(item1.isLockIncidentReturn());
		assertTrue(item2.isLockIncidentReturn());
	}
	
	
	/**
	 * Testing the main purpose of the AsyncQueue which is that in the event of a failure, once the queue is full
	 * we effectively disable the queue to prevent blocking
	 */
	@Test
	public void queueFullTest(){
		drainQueue();
		
		Agent agentTimeout = new Agent();
		Incident incidentTimeout = new Incident();
		when(clfMock.lockIncident(agentTimeout, incidentTimeout)).thenAnswer(new Answer<Boolean>(){
			@Override
			public Boolean answer(InvocationOnMock invocation){
				try {
					Thread.sleep(AsyncCacheLockFile.MAX_WAIT_TIME * 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		
		//Fill the queue using threaded clients to block the queue
		for(int i = 0; i < AsyncCacheLockFile.MAX_QUEUE_SIZE * 2; i++){
			QueueItem item1 = new QueueItem();
			CacheLockFileThreadTest c1 = new CacheLockFileThreadTest(aclf, incidentTimeout, agentTimeout, item1, 30000);
			Thread t1 = new Thread(c1);
			t1.start();
		}
		
		//Create non-threaded requests to test if they block
		Date start = new Date();
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		assertFalse(aclf.lockIncident(agentTimeout, incidentTimeout));
		Date end = new Date();
		
		//assert that a full queue does not block
		assertTrue((end.getTime() - start.getTime()) < 100);
		drainQueue();
		
	}
	
	
	/**
	 * Since we are testing against a static consumer queue with test cases that are forcing timeouts, 
	 * we need to allow the queue to finish processing the previous test before running the next test case.
	 */
	private void drainQueue(){
		try {
			Thread.sleep(AsyncCacheLockFile.MAX_WAIT_TIME * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
