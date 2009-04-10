package com.bagnet.nettracer.wt.svc;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextAware;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.bmo.LockBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Lock;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Lock.LockType;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLockException;
import com.bagnet.nettracer.wt.bmo.ActionFileStationBMO;

public class ActionFileManagerImpl implements ActionFileManager {

	private static final Logger logger = Logger
			.getLogger(ActionFileManager.class);

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
			Agent user) throws WorldTracerDisabledException,
			WorldTracerException {

		ActionFileStation afStation = null;
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.AF_COUNT, companyCode+ wtStation, 5000L);
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
			afStation = afsBmo.updateStation(companyCode, wtStation, user);
			
		} catch (Exception e) {
			throw new WorldTracerException("unable to update counts", e);
		} finally {
			if (lock != null) {
				lockBmo.releaseLock(lock);
			}
		}
		return afStation;
	}



	public List<Worldtracer_Actionfiles> getSummary(String companyCode,
			String wtStation, ActionFileType category, int day, Agent user) throws Exception {
		ActionFileStation afs = this.getCounts(companyCode, wtStation, user);
		if(afs.summaryLoaded(category, day)) {
			return wafBmo.findActionFileSummary(companyCode, wtStation, category, day);
		}
		Lock lock = null;
		int i = 0;
		try {
			do {
				i++;
				try {
					lock = lockBmo.createLock(LockType.AF_SUMMARY, companyCode+ wtStation + category.name() + day, 5000L);
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
			return afsBmo.updateSummary(companyCode, wtStation, category, day, user);
		} catch (Exception e) {
			throw new WorldTracerException("unable to load summary", e);
		} finally {
			if (lock != null) {
				lockBmo.releaseLock(lock);
			}
		}
	}

	public Worldtracer_Actionfiles getDetails(String companyCode,
			String wtStation, ActionFileType category, int day, int fileNum) {
		return null;
	}

	public void setAfsBmo(ActionFileStationBMO afsBmo) {
		this.afsBmo = afsBmo;
	}

	public boolean eraseActionFile(String companyCode, String wtStation,
			ActionFileType category, int day, int fileNum) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLockBmo(LockBMO lockBmo) {
		this.lockBmo = lockBmo;
	}

	public void setWafBmo(WT_ActionFileBmo wafBmo) {
		this.wafBmo = wafBmo;
	}
	
}
