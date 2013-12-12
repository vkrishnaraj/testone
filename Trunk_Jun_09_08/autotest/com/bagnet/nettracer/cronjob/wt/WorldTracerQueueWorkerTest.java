package com.bagnet.nettracer.cronjob.wt;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendOhd;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class WorldTracerQueueWorkerTest {
	private Agent getAgent(){
		Agent agent = new Agent();
		agent.setCompanycode_ID("WS");
		return agent;
	}
	
	private Incident createIncident(){
		Incident incident = new Incident();
		Status status = new Status();
		status.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
		incident.setStatus(status);
		return incident;
	}
	
	private OHD createOHD(){
		OHD ohd = new OHD();
		Status status = new Status();
		status.setStatus_ID(TracingConstants.MBR_STATUS_OPEN);
		ohd.setStatus(status);
		return ohd;
	}
	
	@Test
	public void createAhlExceptionTest(){
		Agent agent = getAgent();
		Incident incident = createIncident();

		WtqCreateAhl queue = new WtqCreateAhl();
		queue.setIncident(incident);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.insertIncident(agent,incident, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.createAhl(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void amendAhlExceptionTest(){
		Agent agent = getAgent();
		Incident incident = createIncident();

		WtqAmendAhl queue = new WtqAmendAhl();
		queue.setIncident(incident);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.amendAhl(agent,incident, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.amendAhl(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void closeAhlExceptionTest(){
		Agent agent = getAgent();
		Incident incident = createIncident();

		WtqCloseAhl queue = new WtqCloseAhl();
		queue.setIncident(incident);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.closeIncident(agent,incident, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.closeAhl(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void suspendAhlExceptionTest(){
		Agent agent = getAgent();
		Incident incident = createIncident();

		WtqSuspendAhl queue = new WtqSuspendAhl();
		queue.setIncident(incident);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.suspendIncident(agent,incident, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.suspendAhl(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void reinstateAhlExceptionTest(){
		Agent agent = getAgent();
		Incident incident = createIncident();

		WtqReinstateAhl queue = new WtqReinstateAhl();
		queue.setIncident(incident);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.reinstateIncident(agent,incident, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.reinstateAhl(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void createOhdExceptionTest(){
		Agent agent = getAgent();
		OHD ohd = createOHD();

		WtqCreateOhd queue = new WtqCreateOhd();
		queue.setOhd(ohd);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.insertOhd(agent,ohd, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.createOhd(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void closeOhdExceptionTest(){
		Agent agent = getAgent();
		OHD ohd = createOHD();

		WtqCloseOhd queue = new WtqCloseOhd();
		queue.setOhd(ohd);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.closeOHD(agent,ohd, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.closeOhd(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void amendOhdExceptionTest(){
		Agent agent = getAgent();
		OHD ohd = createOHD();

		WtqAmendOhd queue = new WtqAmendOhd();
		queue.setOhd(ohd);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.amendOhd(agent,ohd, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.amendOhd(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void suspendOhdExceptionTest(){
		Agent agent = getAgent();
		OHD ohd = createOHD();

		WtqSuspendOhd queue = new WtqSuspendOhd();
		queue.setOhd(ohd);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.suspendOhd(agent,ohd, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.suspendOhd(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
	
	@Test
	public void reinstateOhdExceptionTest(){
		Agent agent = getAgent();
		OHD ohd = createOHD();

		WtqReinstateOhd queue = new WtqReinstateOhd();
		queue.setOhd(ohd);
		
		WorldTracerService wtService = mock(DefaultWorldTracerService.class);
		WTQueueBmo wtQueueBmo = mock(WTQueueBmo.class);
		
		Mockito.doNothing().when(wtQueueBmo).updateQueue(queue);

		try {
			when(wtService.reinstateOhd(agent,ohd, null)).thenThrow(new WorldTracerException("test"));
		} catch (Exception e) {
		}
		
		WorldTracerQueueWorker worker = new WorldTracerQueueWorker(wtService, null, wtQueueBmo, agent, null, null, null, null);
		
		try {
			worker.reinstateOhd(queue, null);
		} catch (CaptchaException e) {
			e.printStackTrace();
		}
		
		assertTrue(queue.getStatus().equals(WtqStatus.FAIL));
	}
}
