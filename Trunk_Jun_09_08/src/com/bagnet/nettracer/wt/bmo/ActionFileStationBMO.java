package com.bagnet.nettracer.wt.bmo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class ActionFileStationBMO extends HibernateDaoSupport {

	@Transactional(readOnly=true)
	public ActionFileStation getAfStation(String companyCode, String wtStation) {
		Session sess = getSession(false);
		Query q = sess.createQuery("from ActionFileStation afs where afs.companyCode = :companyCode and afs.stationCode = :wtStation");
		q.setString("companyCode", companyCode);
		q.setString("wtStation", wtStation);
		return (ActionFileStation) q.uniqueResult();
	}

	@Transactional
	public ActionFileStation updateStation(String companyCode, String wtStation, Agent user) throws Exception {
		ActionFileStation afStation = getAfStation(companyCode, wtStation);
		if (!isCurrent(afStation)) {
			WorldTracerService wtService = SpringUtils.getWorldTracerService();
			Map<ActionFileType, ActionFileCount> countMap;
			try {
				wtService.getWtConnector().initialize();
				countMap = wtService.getActionFileCount(companyCode, wtStation, user);
			} finally {
				wtService.getWtConnector().logout();
			}
			if(afStation == null) {
				afStation = new ActionFileStation();
				afStation.setCompanyCode(companyCode);
				afStation.setStationCode(wtStation);
			}
			afStation.setCountMap(countMap);
			afStation.setLastUpdated(new Date());
			Session sess = getSession(false);
			Query q = sess.createQuery(WT_ActionFileBmo.DELETE_BY_STATION);
			q.setString("airline", companyCode);
			q.setString("station", wtStation);
			q.executeUpdate();
			sess.saveOrUpdate(afStation);
		}
		return afStation;
	}

	private boolean isCurrent(ActionFileStation afStation) {
		if(afStation == null) return false;
		
		Date now = new Date();
		if ((now.getTime() - afStation.getLastUpdated().getTime()) < 1800000) {
			return true;
		}
		return false;
	}

	@Transactional
	public List<Worldtracer_Actionfiles> updateSummary(String companyCode, String wtStation,
			ActionFileType category, int day, Agent user) throws Exception {
		WorldTracerService wtService = SpringUtils.getWorldTracerService();
		ActionFileStation afStation = getAfStation(companyCode, wtStation);
		if(afStation == null) return null;
		try {
			wtService.getWtConnector().initialize();
			List<Worldtracer_Actionfiles> result = wtService.getActionFileSummary(companyCode, wtStation, category, day, user);
			
			if(result == null || result.size() < 1) {
				return null;
			}
			ActionFileCount counts = afStation.getCountMap().get(category);
			if(counts == null && result.size() > 0)
				counts = new ActionFileCount();
			if(counts == null) return null;
			switch (day) {
			case 1:
				counts.setDayOne(result.size());
				counts.setDayOneLoaded(true);
				break;
			case 2:
				counts.setDayTwo(result.size());
				counts.setDayTwoLoaded(true);
				break;
			case 3:
				counts.setDayThree(result.size());
				counts.setDayThreeLoaded(true);
				break;
			case 4:
				counts.setDayFour(result.size());
				counts.setDayFourLoaded(true);
				break;
			case 5:
				counts.setDayFive(result.size());
				counts.setDayFiveLoaded(true);
				break;
			case 6:
				counts.setDaySix(result.size());
				counts.setDaySixLoaded(true);
				break;
			case 7:
				counts.setDaySeven(result.size());
				counts.setDaySevenLoaded(true);
				break;
			default:
				return null;
			}
			afStation.getCountMap().put(category, counts);
			Session sess = getSession(false);
			sess.saveOrUpdate(afStation);
			Query q = sess.createQuery(WT_ActionFileBmo.DELETE_BY_DAY_TYPE);
			q.setParameter("airline", companyCode);
			q.setParameter("station", wtStation);
			q.setParameter("day", day);
			q.setParameter("actionFileType", category);
			q.executeUpdate();
			for (Worldtracer_Actionfiles af : result) {
				sess.save(af);
			}
			return result;
		} finally {
			wtService.getWtConnector().logout();
		}

	}

}
