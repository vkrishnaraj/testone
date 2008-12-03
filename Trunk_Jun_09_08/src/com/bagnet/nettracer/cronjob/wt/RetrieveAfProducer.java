package com.bagnet.nettracer.cronjob.wt;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class RetrieveAfProducer implements Runnable {

	private static final Logger logger = Logger.getLogger(RetrieveAfProducer.class);
	private ActionFileType[] types;
	private String companyCode;
	private ArrayBlockingQueue<RetrieveAfDTO> queue;

	public RetrieveAfProducer() {}

	public RetrieveAfProducer(ActionFileType[] types, String companyCode, ArrayBlockingQueue<RetrieveAfDTO> queue) {
		this.types = types;
		this.companyCode = companyCode;
		this.queue = queue;
	}

	public void run() {
		while (true) {
			try {
				Company_Specific_Variable csv = AdminUtils.getCompVariable(this.companyCode);
				if(csv == null || csv.getWt_enabled() != 1) {
					Thread.sleep(1000 * 60 * 15);
					//continue;
				}
				List<String> stationList = StationBMO.getWorldTracerStations(this.companyCode);

				if(stationList != null && stationList.size() > 0) {
					for(String stationCode : stationList) {

						for(ActionFileType type : this.types) {
							int day = 1;
							for(day = 1; day <= 7; day++) {
								RetrieveAfDTO dto = new RetrieveAfDTO(type, day, this.companyCode, stationCode);
								try {
									queue.put(dto);
								}
								catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					logger.info("finished queueing up action files retrieval for " + types[0].name() + "group");
				}
			}
			catch (Throwable t) {
				logger.warn("error adding to the wt queue", t);
			}
		}
	}

}