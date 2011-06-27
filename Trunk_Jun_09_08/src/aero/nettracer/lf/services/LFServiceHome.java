package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public interface LFServiceHome {

	public String echo(String s);
	
	//LF services
	public LFLost getLostReport(long id);
	public LFLost getLostReport(long id, String lastname);
	public int searchLostCount(LFSearchDTO dto);
	public List<LFLost> searchLost(LFSearchDTO dto, int start, int offset);
	public long saveOrUpdateLostReport(LFLost lostReport);
	
	public LFFound getFoundItem(long id);
	public int searchFoundCount(LFSearchDTO dto);
	public List<LFFound> searchFound(LFSearchDTO dto, int start, int offset);
	public long saveOrUpdateFoundItem(LFFound foundItem);
	
	public
	ArrayList<LabelValueBean> getColors();
	public List<LFCategory> getCategories();

	public boolean closeLostReport(long id);
	public void sendStillSearching(long id);
	
	//LF TaskManager services
	public int getLostCount(Station station);
	public List<LFLost> getLostPaginatedList(Station station, int start, int offset);
	public int getFoundCount(Station station);
	public List<LFFound> getFoundPaginatedList(Station station, int start, int offset);
	public int getLostReportToCloseCount(Station station);
	public List<LFLost> getLostReportToClosePaginatedList(Station station, int start, int offset);
	public int getItemsToSalvageCount(Station station);
	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset);
	public int getTraceResultsCount(Station station);
	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset);
	public int getDeliveryPendingCount(Station station);
	public List<LFItem> getDeliveryPendingPaginatedList(Station station, int start, int offset);
	
	//LF reporting
	public String getLostReport(Date startdate, Date enddate, Station station, int matchType, boolean shipped);
	public String getFoundReport(Date startdate, Date enddate, Station station, int matchType, boolean shipped);
	
	//LF tracing
	public List<LFMatchHistory> getTraceResultsForLost(long id);
	public List<LFMatchHistory> getTraceResultsForFound(long id);
	public List<LFMatchHistory> traceFoundItem(long id);
	public List<LFMatchHistory> traceLostItem(long id);
	public boolean confirmMatch(long id);
	public boolean rejectMatch(long id);
	public boolean undoMatch(long id);

	long saveOrUpdateDelivery(LFDelivery delivery);

	LFDelivery getDelivery(long id);

	LFMatchHistory getTraceResult(long id);

	long saveOrUpdateTraceResult(LFMatchHistory match);

	LFItem getItem(long id);

	void getStillSearchingList();
}
