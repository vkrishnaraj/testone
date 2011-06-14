package aero.nettracer.lf.services;

import java.util.Date;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFReservation;
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
		
		
		LinkedHashSet<LFItem> items = new LinkedHashSet<LFItem>();
		LFItem item = new LFItem();
		item.setLost(lost);
		
		Status itemStatus = new Status();
		itemStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		item.setStatus(itemStatus);

		Status disposition = new Status();
		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(disposition);
		
		items.add(item);
		lost.setItems(items);
		
		LFReservation reservation = new LFReservation();
		Station dropoffLocation = new Station();
		Station pickupLocation = new Station();
		dropoffLocation.setStation_ID(agent.getStation().getStation_ID());
		reservation.setPickupLocation(pickupLocation);
		reservation.setDropoffLocation(dropoffLocation);
		lost.setReservation(reservation);
		
		LFPerson client = new LFPerson();
		LFAddress address = new LFAddress();
		client.setAddress(address);
		
		LinkedHashSet<LFPhone> phones = new LinkedHashSet<LFPhone>();
		client.setPhones(phones);
		
		lost.setClient(client);
		lost.setOpenDate(new Date());
		lost.setAgent(agent);
		lost.setLocation(agent.getStation());
		return lost;
	}
	
	public static LFFound createLFFound(Agent agent) {
		LFFound found = new LFFound();
		Status foundStatus = new Status();
		foundStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		found.setStatus(foundStatus);
		
		LFItem item = new LFItem();
		Status itemStatus = new Status();
		itemStatus.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		item.setStatus(itemStatus);
		
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
			session.setAttribute("lfcategorylist", new LFServiceBean().getCategories());
		}
		
		session.setAttribute("lfstatuslist", session
				.getAttribute("lfstatuslist") != null ? session
						.getAttribute("lfstatuslist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_STATUS, user.getCurrentlocale()));

		session.setAttribute("lfdispositionlist", session
				.getAttribute("lfdispositionlist") != null ? session
				.getAttribute("lfdispositionlist") : TracerUtils.getStatusList(TracingConstants.TABLE_LF_DISPOSITION, user.getCurrentlocale()));
	}

}
