package com.bagnet.nettracer.wt.bmo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	private static final String GET_COUNT = "select count(*) from Worldtracer_Actionfiles where action_file_type=? and day=? and airline=? and station=? and deleted = false";
	
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
		Session sess = getSession(false);
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
			Query q = sess.createQuery(WT_ActionFileBmo.DELETE_BY_STATION);
			q.setString("airline", companyCode);
			q.setString("station", wtStation);
			q.executeUpdate();
			
		}
		Map<ActionFileType, ActionFileCount> countMap = afStation.getCountMap();
		
		for (Entry<ActionFileType, ActionFileCount> entry : countMap.entrySet()) {
			ActionFileType type = entry.getKey();
			ActionFileCount count = entry.getValue();
			if(count.isDayOneLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 1);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDayOne(finalCount);
			}
			if(count.isDayTwoLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 2);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDayTwo(finalCount);
			}
			if(count.isDayThreeLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 3);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDayThree(finalCount);
			}
			if(count.isDayFourLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 4);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDayFour(finalCount);
			}
			if(count.isDayFiveLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 5);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDayFive(finalCount);
			}
			if(count.isDaySixLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 6);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDaySix(finalCount);
			}
			if(count.isDaySevenLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 7);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				String string_count=query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				count.setDaySeven(finalCount);
			}
		}
		sess.saveOrUpdate(afStation);
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

	@Transactional
	public Worldtracer_Actionfiles eraseActionFile(String companyCode, String wtStation,
			ActionFileType category, int day, int fileNum, ActionFileStation afs) {
		Session sess = getSession(false);
		Query q = sess.createQuery(WT_ActionFileBmo.FIND_WAF);
		q.setParameter("airline", companyCode);
		q.setParameter("afType", category);
		q.setParameter("wtStation", wtStation);
		q.setInteger("day", day);
		q.setInteger("itemNum", fileNum);
		Worldtracer_Actionfiles waf = (Worldtracer_Actionfiles) q.uniqueResult();
		waf.setDeleted(true);
		sess.update(waf);
		ActionFileCount count = afs.getCountMap().get(category);
		switch(day){
		case 1:
			count.setDayOne(count.getDayOne() - 1 >= 0 ? count.getDayOne() - 1 : 0);
			break;
		case 2:
			count.setDayTwo(count.getDayTwo() - 1 >= 0 ? count.getDayTwo() - 1 : 0);
			break;
		case 3:
			count.setDayThree(count.getDayThree() - 1 >= 0 ? count.getDayThree() - 1 : 0);
			break;
		case 4:
			count.setDayFour(count.getDayFour() - 1 >= 0 ? count.getDayFour() - 1 : 0);
			break;
		case 5:
			count.setDayFive(count.getDayFive() - 1 >= 0 ? count.getDayFive() - 1 : 0);
			break;
		case 6:
			count.setDaySix(count.getDaySix() - 1 >= 0 ? count.getDaySix() - 1 : 0);
			break;
		case 7:
			count.setDaySeven(count.getDaySeven() - 1 >= 0 ? count.getDaySeven() - 1 : 0);
			break;
			default:
				break;
		}
		sess.saveOrUpdate(afs);
		return waf;
	}

}
