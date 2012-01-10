package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
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

}
