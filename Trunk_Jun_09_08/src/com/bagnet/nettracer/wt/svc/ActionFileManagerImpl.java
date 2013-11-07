package com.bagnet.nettracer.wt.svc;

import java.util.List;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLockException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.bmo.ActionFileStationBMO;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.utils.ParsingUtils;

public class ActionFileManagerImpl implements ActionFileManager {

	private static final Logger logger = Logger
			.getLogger(ActionFileManagerImpl.class);

	private ActionFileStationBMO afsBmo;

	private LockBMO lockBmo;
	
	private WT_ActionFileBmo wafBmo;

	private ActionFileManagerImpl() {

	}

	private static class ActionFileManagerHolder {
		static ActionFileManagerImpl instance = new ActionFileManagerImpl();
	}

	public static ActionFileManagerImpl getInstance() {
		return ActionFileManagerHolder.instance;
	}

	@Override
	public ActionFileStation getCounts(String companyCode, String wtStation,
			Agent user, WebServiceDto dto) throws WorldTracerDisabledException, WorldTracerException, CaptchaException {

		ActionFileStation afStation = null;
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.AF_COUNT, companyCode+ wtStation, 10000L);
				} catch (Exception ex) {
					logger.info("counts for " + companyCode + wtStation
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new WorldTracerException(
								"unable to acquire count lock", e);
					}
				}
			} while (lock == null && i < 4);
			if(lock == null) {
				throw new WorldTracerLockException("unable to load counts");
			}
			afStation = afsBmo.updateStation(companyCode, wtStation, user, dto);
			
		} catch (WorldTracerException e) {
			throw new WorldTracerException("unable to update counts", e);
		} finally {
			if (lock != null) {
				lockBmo.releaseLock(lock);
			}
		}
		return afStation;
	}



	public List<Worldtracer_Actionfiles> getSummary(String companyCode,
			String wtStation, ActionFileType category, String seq, int day, Agent user, WebServiceDto dto) throws WorldTracerException, CaptchaException, WorldTracerDisabledException {
		ActionFileStation afs = this.getCounts(companyCode, wtStation, user, dto);
		if(afs.summaryLoaded(category, day, seq)) {
			return wafBmo.findActionFileSummary(companyCode, wtStation, category, seq, day);
		}
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.AF_SUMMARY, companyCode+ wtStation + category.name() + seq + day, 10000L);
				} catch (Exception ex) {
					logger.info("counts for " + companyCode + wtStation
							+ " alreaedy locked, waiting..");
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						throw new WorldTracerException(
								"unable to acquire summary lock", e);
					}
				}
			} while (lock == null && i < 4);
			if(lock == null) {
				throw new WorldTracerLockException("unable to load counts");
			}
			return afsBmo.updateSummary(companyCode, wtStation, category, seq, day, user, dto);
		} catch (CaptchaException e) {
			throw new CaptchaException();
		} catch (Exception e) {
			throw new WorldTracerException("unable to load summary", e);
		} finally {
			if (lock != null) {
				lockBmo.releaseLock(lock);
			}
		}
	}

	public void updateDetails(String companyCode, String wtStation,
			ActionFileType category, String seq, int day, int fileNum, Agent user, WebServiceDto dto)
			throws WorldTracerException {
		WorldTracerService wtService = SpringUtils.getWorldTracerService();
		try {
			wtService.getWtConnector().initialize();

			String result = wtService.getActionFileDetail(user, companyCode,
					wtStation, category, day, fileNum, dto);
			String ahl_id = ParsingUtils.parseAhlId(result);
			String ohd_id = ParsingUtils.parseOhdId(result);
			double percent = ParsingUtils.parsePercentMatch(result);
			if (result != null && result.trim().length() > 0) {
				wafBmo.updateDetails(companyCode, wtStation, category, seq, day,
						fileNum, result, ahl_id, ohd_id, percent);
			}
		} catch (Exception e) {
			throw new WorldTracerException(e);
		} finally {
			wtService.getWtConnector().logout();
		}
	}

	public void setAfsBmo(ActionFileStationBMO afsBmo) {
		this.afsBmo = afsBmo;
	}

	public boolean eraseActionFile(String companyCode, String wtStation,
			ActionFileType category, String seq, int day, int fileNum, Agent user, WebServiceDto dto) throws CaptchaException, WorldTracerDisabledException, WorldTracerException {
		ActionFileStation afs = this.getCounts(companyCode, wtStation, user, dto);
		Worldtracer_Actionfiles waf = afsBmo.eraseActionFile(companyCode, wtStation, category, seq, day, fileNum, afs);
		WtqEraseActionFile wq = new WtqEraseActionFile();
		wq.setAgent(user);
		wq.setCreatedate(TracerDateTime.getGMTDate());
		wq.setAf_id(waf.generateId());
		WorldTracerQueueUtils.createOrReplaceQueue(wq);
		return false;
	}

	public void setLockBmo(LockBMO lockBmo) {
		this.lockBmo = lockBmo;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}
	
}
