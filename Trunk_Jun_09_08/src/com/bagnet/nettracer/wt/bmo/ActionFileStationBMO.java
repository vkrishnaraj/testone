package com.bagnet.nettracer.wt.bmo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class ActionFileStationBMO {

	private static Logger logger = Logger.getLogger(ActionFileStationBMO.class);
	
	private static final String GET_COUNT = "select count(*) from Worldtracer_Actionfiles where action_file_type=? and day=? and airline=? and station=? and seq=? and deleted = false";

	public ActionFileStation getAfStation(String companyCode, String wtStation) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess
				.createQuery("from ActionFileStation afs where afs.companyCode = :companyCode and afs.stationCode = :wtStation");
		q.setString("companyCode", companyCode);
		q.setString("wtStation", wtStation);
		ActionFileStation ret = (ActionFileStation) q.uniqueResult();
		sess.close();
		return ret;
	}

	public ActionFileStation updateStation(String companyCode,
			String wtStation, Agent user, WebServiceDto dto)
			throws CaptchaException {
		ActionFileStation afStation = getAfStation(companyCode, wtStation);
		Session sess = HibernateWrapper.getSession().openSession();
		if (!isCurrent(afStation)) {
			WorldTracerService wtService = SpringUtils.getWorldTracerService();
			List<ActionFileCount> countList;
			try {
				wtService.getWtConnector().initialize();
				countList = wtService.getActionFileCount(companyCode,
						wtStation, user, dto);
			} catch (CaptchaException e) {
				throw e;
			} finally {
				wtService.getWtConnector().logout();
			}
			if (afStation == null) {
				afStation = new ActionFileStation();
				afStation.setCompanyCode(companyCode);
				afStation.setStationCode(wtStation);
			}
			afStation.setCountList(countList);
			afStation.setLastUpdated(new Date());
			Query q = sess.createQuery(WT_ActionFileBmo.DELETE_BY_STATION);
			q.setString("airline", companyCode);
			q.setString("station", wtStation);
			q.executeUpdate();

		}
		Collection<ActionFileCount> countCollection = afStation.getCountList();
		for (ActionFileCount afc : countCollection) {
			ActionFileType type = afc.getAf_type();
			if (afc.isDayOneLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 1);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDayOne(finalCount);
			}
			if (afc.isDayTwoLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 2);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDayTwo(finalCount);
			}
			if (afc.isDayThreeLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 3);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDayThree(finalCount);
			}
			if (afc.isDayFourLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 4);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDayFour(finalCount);
			}
			if (afc.isDayFiveLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 5);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDayFive(finalCount);
			}
			if (afc.isDaySixLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 6);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDaySix(finalCount);
			}
			if (afc.isDaySevenLoaded()) {
				Query query = sess.createQuery(GET_COUNT);
				query.setParameter(0, type);
				query.setParameter(1, 7);
				query.setParameter(2, companyCode);
				query.setParameter(3, wtStation);
				query.setParameter(4, afc.getAf_seq());
				String string_count = query.uniqueResult().toString();
				int finalCount = Integer.parseInt(string_count);
				afc.setDaySeven(finalCount);
			}
		}
		sess.saveOrUpdate(afStation);
		sess.close();
		return afStation;
	}

	private boolean isCurrent(ActionFileStation afStation) {
		if (afStation == null)
			return false;

		Date now = new Date();
		if ((now.getTime() - afStation.getLastUpdated().getTime()) < 60 * 1000 * Integer
				.parseInt(PropertyBMO
						.getValue(PropertyBMO.PROPERTY_WT_AF_EXPIRE))) {
			return true;
		}
		return false;
	}

	public List<Worldtracer_Actionfiles> updateSummary(String companyCode,
			String wtStation, ActionFileType category, String seq, int day,
			Agent user, WebServiceDto dto) throws CaptchaException, Exception {
		WorldTracerService wtService = SpringUtils.getWorldTracerService();
		ActionFileStation afStation = getAfStation(companyCode, wtStation);
		if (afStation == null)
			return null;
		Session sess = null;
		try {
			wtService.getWtConnector().initialize();
			List<Worldtracer_Actionfiles> result = wtService
					.getActionFileSummary(companyCode, wtStation, category,
							seq, day, user, dto);

			if (result == null || result.size() < 1) {
				return null;
			}
			ActionFileCount counts = null;
			if (afStation.getCountList() != null) {
				for (ActionFileCount afc : afStation.getCountList()) {
					if (afc.getAf_type().equals(category)
							&& afc.getAf_seq().equals(seq)) {
						counts = afc;
					}
				}
			}

			if (counts == null && result.size() > 0)
				counts = new ActionFileCount();
			if (counts == null)
				return null;
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

			sess = HibernateWrapper.getSession().openSession();
			sess.saveOrUpdate(afStation);
			Query q = sess.createQuery(WT_ActionFileBmo.DELETE_BY_DAY_TYPE);
			q.setParameter("airline", companyCode);
			q.setParameter("station", wtStation);
			q.setParameter("day", day);
			q.setParameter("actionFileType", category);
			q.setParameter("seq", seq);
			q.executeUpdate();
			for (Worldtracer_Actionfiles af : result) {
				sess.save(af);
			}
			return result;
		} catch (CaptchaException e) {
			throw e;
		} catch (WorldTracerRecordNotFoundException ex) {
			logger.error("No ActionFiles found");
			return null;
		} finally {
			wtService.getWtConnector().logout();
			if(sess != null){
				sess.close();
			}
		}
	}

	public Worldtracer_Actionfiles eraseActionFile(String companyCode,
			String wtStation, ActionFileType category, String seq, int day,
			int fileNum, ActionFileStation afs) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(WT_ActionFileBmo.FIND_WAF);
		q.setParameter("airline", companyCode);
		q.setParameter("afType", category);
		q.setParameter("seq", seq);
		q.setParameter("wtStation", wtStation);
		q.setInteger("day", day);
		q.setInteger("itemNum", fileNum);
		Worldtracer_Actionfiles waf = (Worldtracer_Actionfiles) q
		.uniqueResult();
		waf.setDeleted(true);
		sess.update(waf);
		ActionFileCount count = null;
		for (ActionFileCount afc : afs.getCountList()) {
			if (afc.getAf_type().equals(category) && afc.getAf_seq().equals(seq)) {
				count = afc;
			}
		}
		switch (day) {
		case 1:
			count.setDayOne(count.getDayOne() - 1 >= 0 ? count.getDayOne() - 1
					: 0);
			break;
		case 2:
			count.setDayTwo(count.getDayTwo() - 1 >= 0 ? count.getDayTwo() - 1
					: 0);
			break;
		case 3:
			count.setDayThree(count.getDayThree() - 1 >= 0 ? count
					.getDayThree() - 1 : 0);
			break;
		case 4:
			count
					.setDayFour(count.getDayFour() - 1 >= 0 ? count
							.getDayFour() - 1 : 0);
			break;
		case 5:
			count
					.setDayFive(count.getDayFive() - 1 >= 0 ? count
							.getDayFive() - 1 : 0);
			break;
		case 6:
			count.setDaySix(count.getDaySix() - 1 >= 0 ? count.getDaySix() - 1
					: 0);
			break;
		case 7:
			count.setDaySeven(count.getDaySeven() - 1 >= 0 ? count
					.getDaySeven() - 1 : 0);
			break;
		default:
			break;
		}
		sess.saveOrUpdate(afs);
		sess.close();
		return waf;
	}

}
