package com.bagnet.nettracer.cronjob.wt;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class RetrieveAfConsumer implements Runnable {

	private static final Logger logger = Logger.getLogger(RetrieveAfConsumer.class);
	private BlockingQueue<RetrieveAfDTO> queue;
	private WorldTracerService wtService;
	private WT_ActionFileBmo wafBmo;

	public RetrieveAfConsumer(WT_ActionFileBmo wafBmo, WorldTracerService wtService, BlockingQueue<RetrieveAfDTO> queue) {
		this.queue = queue;
		this.wtService = wtService;
		this.wafBmo = wafBmo;
	}

	public void run() {
		while (true) {
			try {
				RetrieveAfDTO dto = queue.take();
				List<Worldtracer_Actionfiles> result = wtService.getActionFiles(dto.getCompanyCode(), dto
						.getStationCode(), dto.getType(), dto.getDay());

				wafBmo.replaceActionFiles(result, dto.getCompanyCode(), dto.getStationCode(), dto.getType(), dto.getDay());
			}
			catch (Throwable t) {
				logger.warn("Error trying to process ActionFile DTO", t);
			}
		}
	}
}
