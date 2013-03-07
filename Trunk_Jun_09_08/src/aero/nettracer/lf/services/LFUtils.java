package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.hibernate.classic.Session;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLossInfo;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFRemark;
import com.bagnet.nettracer.tracing.db.lf.LFSegment;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class LFUtils {
	public static final int LF_STATUS_OPEN = 1;
	public static final int LF_STATUS_CLOSED = 2;
	public static final int LF_STATUS_RECOVERED = 3;
	public static final int LF_STATUS_SALVAGED = 4;
	
	public static LFLost createLFLost(Agent agent) {
		LFLost lost = new LFLost();
		Status lostStatus = new Status();
		lostStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		lost.setStatus(lostStatus);
		if (agent.getStation().getAssociated_airport() == null || agent.getStation().getAssociated_airport().isEmpty()) {
			lost.setCompanyId(TracingConstants.LF_ABG_COMPANY_ID);
		} else {
			lost.setCompanyId(agent.getStation().getAssociated_airport());
		}
		
		
		LinkedHashSet<LFItem> items = new LinkedHashSet<LFItem>();
		LFItem item = new LFItem();
		item.setType(TracingConstants.LF_TYPE_LOST);
		item.setLost(lost);
		
		Status disposition = new Status();
		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(disposition);
		
		items.add(item);
		lost.setItems(items);
		
		LFLossInfo lossinfo = new LFLossInfo();
		Station dropoffLocation = new Station();
		//Station pickupLocation = new Station();
		dropoffLocation.setStation_ID(agent.getStation().getStation_ID());
		//lossinfo.setOrigin(pickupLocation);
		lossinfo.setDestination(dropoffLocation);
		lost.setLossInfo(lossinfo);
		
		LFPerson client = new LFPerson();
		LFAddress address = new LFAddress();
		client.setAddress(address);
		
		LinkedHashSet<LFPhone> phones = new LinkedHashSet<LFPhone>();
		client.setPhones(phones);
		
		lost.setClient(client);
		lost.setOpenDate(new Date());
		lost.setAgent(agent);
		lost.setLocation(agent.getStation());
		if(agent.getSubcompany()!=null){
			lost.setCompanyId(agent.getSubcompany().getSubcompanyCode());
		}
		LFRemark r = new LFRemark();
		r.getRemark().setType(TracingConstants.REMARK_REGULAR);
		r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
		r.getRemark().setAgent(agent);
		r.getRemark().setRemarktext("");
		r.getRemark().set_DATEFORMAT(agent.getDateformat().getFormat());
		r.getRemark().set_TIMEFORMAT(agent.getTimeformat().getFormat());
		r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone()));
		List<LFRemark> remarklist = new ArrayList<LFRemark>();
		remarklist.add(r);
		lost.setAgentRemarks(new LinkedHashSet<LFRemark>(remarklist));
		
		if (TracingConstants.LF_LF_COMPANY_ID.equals(agent.getCompanycode_ID())) {
			LFSegment s = new LFSegment();
			s.setLost(lost);
			LinkedHashSet<LFSegment> segs = new LinkedHashSet<LFSegment>();
			segs.add(s);
			lost.setSegments(segs);
		}
		
		return lost;
	}
	
	public static LFFound createLFFound(Agent agent) {
		LFFound found = new LFFound();
		Status foundStatus = new Status();
		foundStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		found.setStatus(foundStatus);
		if (agent.getStation().getAssociated_airport() == null || agent.getStation().getAssociated_airport().isEmpty()) {
			found.setCompanyId(TracingConstants.LF_ABG_COMPANY_ID);
		} else {
			found.setCompanyId(agent.getStation().getAssociated_airport());
		}
		
		LFItem item = new LFItem();
		item.setType(TracingConstants.LF_TYPE_FOUND);
		Status disposition = new Status();
		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(disposition);
		
		item.setFound(found);
		found.setItem(item);
		
		LFPerson client = new LFPerson();
		LFAddress address = new LFAddress();
		client.setAddress(address);
		
		LinkedHashSet<LFPhone> phones = new LinkedHashSet<LFPhone>();
		client.setPhones(phones);
		
		found.setClient(client);
		found.setFoundDate(new Date());
		found.setAgent(agent);
		found.setLocation(agent.getStation());
		if(agent.getSubcompany()!=null){
			found.setCompanyId(agent.getSubcompany().getSubcompanyCode());
		}

		LFRemark r = new LFRemark();
		r.getRemark().setType(TracingConstants.REMARK_REGULAR);
		r.getRemark().setRemarkdate(TracerDateTime.getGMTDate());
		r.getRemark().setAgent(agent);
		r.getRemark().setRemarktext("");
		r.getRemark().set_DATEFORMAT(agent.getDateformat().getFormat());
		r.getRemark().set_TIMEFORMAT(agent.getTimeformat().getFormat());
		r.getRemark().set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone()));
		List<LFRemark> remarklist = new ArrayList<LFRemark>();
		remarklist.add(r);
		found.setAgentRemarks(new LinkedHashSet<LFRemark>(remarklist));
		
		return found;
	}
	
	public static void getLists(Agent user, HttpSession session) {
		
		if (session.getAttribute("lfcolorlist") == null) {
			session.setAttribute("lfcolorlist", new LFServiceBean().getColors());
		}
		
		if (session.getAttribute("lfcategorylist") == null) {
			session.setAttribute("lfcategorylist", new LFServiceBean().getCategories(user.getCompanycode_ID()));
		}
		
		if (session.getAttribute("lfsubcategorylist") == null) {
			session.setAttribute("lfsubcategorylist", new ArrayList<LFSubCategory>());
		}
		

		if (session.getAttribute("subComplist") == null) {
			List<Subcompany> subcomps=new LFServiceBean().getSubcompanies(user.getCompanycode_ID());
			session.setAttribute("subComplist",subcomps);
			for(Subcompany sc:subcomps){
				session.setAttribute("subComplist"+sc.getSubcompanyCode(), new ArrayList());
			}
		}
		
		if (session.getAttribute("stationList") == null) {
			session.setAttribute("stationList", new ArrayList<Station>());
		}
		
		List<LFCategory> categories = (List) session.getAttribute("lfcategorylist");
		for (LFCategory c: categories) {
				session.setAttribute("lfsubcategorylist"+c.getId(), c.getSubcategories());
				//success = true;
				//break;
		}
		
		List<SubcompanyStation> companyStations = new LFServiceBean().getSubcompanyStations(user.getCompanycode_ID());
		HashMap<String, List<Station>> stationComps=new HashMap();
		for (SubcompanyStation s: companyStations) {
			if(stationComps.get(s.getSubcompany().getSubcompanyCode())!=null){
				List stations=stationComps.get(s.getSubcompany().getSubcompanyCode());
				stations.add(s.getStation());
				stationComps.put(s.getSubcompany().getSubcompanyCode(),stations);
			} else {
				List stations=new ArrayList();
				stations.add(s.getStation());
				stationComps.put(s.getSubcompany().getSubcompanyCode(),stations);
			}
				
//			session.setAttribute("subComplist"+s.getSubcompany().getId(), s.getSubcategories());
			//success = true;
			//break;
		}
		for(String key:stationComps.keySet()){
			session.setAttribute("subComplist"+key, stationComps.get(key));
		}
		
		
		
		session.setAttribute("lfstatuslist", session
				.getAttribute("lfstatuslist") != null ? session
						.getAttribute("lfstatuslist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_STATUS, user.getCurrentlocale()));

		session.setAttribute("lfdispositionlist", session
				.getAttribute("lfdispositionlist") != null ? session
				.getAttribute("lfdispositionlist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_DISPOSITION, user.getCurrentlocale()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean actionChangeSubCategory(long catId, HttpServletRequest request) {
		boolean success = false;
		if (request.getParameter("changesubcategory") != null && request.getParameter("changesubcategory").equals("1")) {
			if (catId <= 0) {
				request.getSession().setAttribute("lfsubcategorylist", new ArrayList<LFSubCategory>());
				success = true;
			} else {
				List<LFCategory> categories = (List) request.getSession().getAttribute("lfcategorylist");
				for (LFCategory c: categories) {
					if (c.getId() == catId) {
						request.getSession().setAttribute("lfsubcategorylist", c.getSubcategories());
						success = true;
						break;
					}
				}
			}
		}
		return success;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean actionChangeSubCompany(long compId, HttpServletRequest request) {
		boolean success = false;
		if (request.getParameter("changestation") != null && request.getParameter("changestation").equals("1")) {
			if (compId <= 0) {
				request.getSession().setAttribute("stationList", new ArrayList<LFSubCategory>());
				success = true;
			} else {
//				List<Subcompany> subcompnies = (List) request.getSession().getAttribute("subCompList");
//				for (Subcompany s: subcompnies) {
//					if (s.getId() == compId) {
						request.getSession().setAttribute("lfsubcategorylist", request.getSession().getAttribute("subCompList"+compId));
						success = true;
						//break;
//					}
//				}
			}
		}
		return success;
	}
	
	public static LFCategory loadCategory(long id) {
		Session session = null;
		LFCategory category = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			category = (LFCategory) session.get(LFCategory.class, id);
		} catch (Exception e) {
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return category;
	}

}
