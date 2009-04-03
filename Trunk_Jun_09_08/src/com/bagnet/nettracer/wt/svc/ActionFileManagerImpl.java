package com.bagnet.nettracer.wt.svc;

import java.util.List;

import com.bagnet.nettracer.exceptions.WorldTracerDisabledException;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.bmo.ActionFileStationBMO;

public class ActionFileManagerImpl implements ActionFileManager {

	private ActionFileStationBMO afsBmo;
	
	private WorldTracerService wtService;
	
	private ActionFileManagerImpl() {
		
	}
	
	private static class ActionFileManagerHolder {
		static ActionFileManagerImpl instance = new ActionFileManagerImpl();
	}
	
	public static ActionFileManagerImpl getInstance() {
		return ActionFileManagerHolder.instance;
	}
	
	public ActionFileStation getCounts(String companyCode, String wtStation)
			throws WorldTracerDisabledException, WorldTracerException {
		
		ActionFileStation afStation = null;
		//TODO proper locking
		synchronized (this) {
			afStation = afsBmo.getAfStation(companyCode, wtStation);
			if(!isCurrent(afStation)) {
				afStation  = wtService.getActionFileCount(companyCode, wtStation);
			}
		}
		return afStation;
	}
	
	private boolean isCurrent(ActionFileStation afStation) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Worldtracer_Actionfiles> getSummary(String companyCode, String wtStation, ActionFileType category, int day) {
		return null;

	}
	
	public Worldtracer_Actionfiles getDetails(String companyCode, String wtStation, ActionFileType category, int day, int fileNum) {
		return null;
	}

	public void setAfsBmo(ActionFileStationBMO afsBmo) {
		this.afsBmo = afsBmo;
	}

	public void setWtService(WorldTracerService wtService) {
		this.wtService = wtService;
	}

	public boolean eraseActionFile(String companyCode, String wtStation,
			ActionFileType category, int day, int fileNum) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
