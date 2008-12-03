package com.bagnet.nettracer.cronjob.wt;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_FWD_LogBmo;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateBdo;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.WorldTracerConnectionException;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class WorldTracerQueueSweeper {
	
	private static final Logger logger = Logger.getLogger(WorldTracerQueueSweeper.class);
	
	public static final int MAX_ATTEMPTS = 5;

	public static final String WT_QUEUE_WORKERS = "wt.queue.workers";
	
	//Spring injected
	private WTQueueBmo wtqBmo;
	private String companyCode;
	private WorldTracerService wtService;

	private WT_ActionFileBmo wafBmo;

	private Agent ogadmin;
	
	public WorldTracerQueueSweeper(String defaultAgent, String companyCode) {
		this.companyCode = companyCode;
		ogadmin = AdminUtils.getAgentBasedOnUsername(defaultAgent, companyCode);
		
	}
	
	public void processWtQueue() {
		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);
		if (csv == null || csv.getWt_enabled() != 1 || csv.getWt_write_enabled() != 1) {
			return;
		}
		int workerThreads = Integer.parseInt(PropertyBMO.getValue(WT_QUEUE_WORKERS));
		
		//proess the queued tasks
		List<WorldTracerQueue> qTasks = null;
		try {
			qTasks = wtqBmo.findAllPendingTasks(companyCode);
		}
		catch (Exception e1) {
			logger.error("unable to load world tracer queue tasks, process aborted", e1);
			return;
		}
		if (qTasks == null || qTasks.size() < 1) {
			logger.info("No queued tasks");
			return;
		}
		
		ConcurrentLinkedQueue<WorldTracerQueue> qq = new ConcurrentLinkedQueue<WorldTracerQueue>(qTasks);
		
		Thread[] workers = new Thread[workerThreads];
		
		for(int i = 0; i < workerThreads; i++) {
			WorldTracerQueueWorker ww = new WorldTracerQueueWorker(wtService, wtqBmo, ogadmin, wafBmo, qq);
			Thread t = new Thread(ww);
			workers[i] = t;
			t.start();
		}
		
		for(int i = 0; i < workerThreads; i++) {
			try {
				if(workers[i].isAlive())
					workers[i].join();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("finished a wtqueue run");

	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setWtqBmo(WTQueueBmo wtqBmo) {
		this.wtqBmo = wtqBmo;
	}

	public void setWtService(WorldTracerService wtService) {
		this.wtService = wtService;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}

	
}
