package com.bagnet.nettracer.cronjob.wt;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.utils.ParsingUtils;

@Configurable("afWorker")
public class ActionFileWorker implements Runnable {

	private static final Logger logger = Logger.getLogger(ActionFileWorker.class);
	private ArrayBlockingQueue<String> stationQ;
	private CountDownLatch doneSignal;
	private String companyCode;
	private WT_ActionFileBmo wafBmo;
	private WorldTracerConnector conn;

	public WT_ActionFileBmo getWafBmo() {
		return wafBmo;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}

	public WorldTracerConnector getConn() {
		return conn;
	}

	public void setConn(WorldTracerConnector conn) {
		this.conn = conn;
	}

	public ActionFileWorker(ArrayBlockingQueue<String> stationQ,
			CountDownLatch doneSignal, String companyCode, WT_ActionFileBmo wafBmo) {
		this.stationQ = stationQ;
		this.doneSignal = doneSignal;
		this.companyCode = companyCode;
		this.wafBmo = wafBmo;
	}

	public void run() {
		
		try {
			conn.initialize();	
			String stationCode;
			while((stationCode = stationQ.poll()) != null) {
				EnumMap<ActionFileType, int[]> countMap = conn.getActionFileCounts(companyCode, stationCode);
				for(ActionFileType afType : ActionFileType.values()) {
					if(countMap.get(afType) != null) {
						int[] counts = countMap.get(afType);
						for (int day = 0; day < counts.length; day++) {
							if(counts[day] > 0) {

								List<Worldtracer_Actionfiles> afList = conn.getActionFiles(companyCode, stationCode, afType, day + 1, counts[day]);
								
								for(Worldtracer_Actionfiles af : afList) {
									logger.debug(String.format("%s %s %s %d %d:\n%s", companyCode, stationCode, afType.name(), day, af.getItem_number(), af.getAction_file_text()));
									String ahl_id = ParsingUtils.parseAhlId(af.getAction_file_text()) == null ? "" : ParsingUtils.parseAhlId(af.getAction_file_text());
									
									String ohd_id = ParsingUtils.parseOhdId(af.getAction_file_text()) == null ? "" : ParsingUtils.parseOhdId(af.getAction_file_text()); ;
									Double percent = ParsingUtils.parsePercentMatch(af.getAction_file_text());
									af.setWt_incident_id(ahl_id);
									af.setWt_ohd_id(ohd_id);
									af.setPercent_match(percent == null ? 0.0D : percent);
								}
								wafBmo.replaceActionFiles(afList, companyCode, stationCode, afType, day + 1);
							}
							else {
								wafBmo.deleteActionFiles(companyCode, stationCode, afType, day + 1);
							}
						}
					}
					else {
						//just delete all action files for this station / type
						wafBmo.deleteActionFiles(companyCode, stationCode, afType);
					}
				}
			}

		} catch (WorldTracerException e) {
			logger.error("Error retrieving action files", e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			//need to make sure signal is sent no matter what
			try {
				conn.logout();
			} catch (Exception e) {
				// Ignore
			}
			this.doneSignal.countDown();
			
		}

	}

}
