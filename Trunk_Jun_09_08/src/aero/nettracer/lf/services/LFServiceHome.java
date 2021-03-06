package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.NonUniqueResultException;

import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.db.lf.LFShipping;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.forms.lfc.SalvageSearchForm;

public interface LFServiceHome {

	public String echo(String s);
	
	//LF services
	public LFLost getLostReport(long id);
	public LFLost getLostReport(long id, String lastname);
	public int searchLostCount(LFSearchDTO dto);
	public List<LFLost> searchLost(LFSearchDTO dto, int start, int offset);
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent) throws UpdateException;
	
	public LFFound getFoundItem(long id);
	public int searchFoundCount(LFSearchDTO dto);
	public List<LFFound> searchFound(LFSearchDTO dto, int start, int offset);
	public long saveOrUpdateFoundItem(LFFound foundItem, Agent agent) throws NonUniqueBarcodeException, UpdateException;
	
	public ArrayList<LabelValueBean> getColors();
	public List<LFCategory> getCategories(String companycode);

	public boolean closeLostReport(long id, Agent agent);
	public void sendStillSearching(long id);
	
	//LF TaskManager services
	public int getLostCount(Station station, Subcompany subcomp);
	public int getLostCount(Agent agent);
	public List<LFLost> getLostPaginatedList(Station station, int start, int offset, Subcompany subcomp);
	public int getFoundCount(Station station, Subcompany subcomp);
	public int getFoundCount(Agent agent);
	public List<LFFound> getFoundPaginatedList(Station station, int start, int offset, Subcompany subcomp);
	public int getItemsToSalvageCount(Station station, Subcompany subcomp);
	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset, Subcompany subcomp);
	public int getTraceResultsCount(Station station, Subcompany subcomp);
	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset, Subcompany subcomp);
	public int getDeliveryPendingCount(Station station, Subcompany subcomp);
	public List<LFItem> getDeliveryPendingPaginatedList(Station station, int start, int offset, Subcompany subcomp);
	public int getShelvedTraceResultsCount(Station station, int value);
	public List<LFFound> getShelvedTraceResultsPaginated(Station station, int value, int start, int offset, Subcompany subcomp);
	public int getSalvageCount(Station station, SalvageSearchForm ssForm);
	public List<LFSalvage> getSalvagesPaginated(Station station, SalvageSearchForm ssForm, int start, int offset);
	
	//LF reporting
	public String getLostReport(Date startdate, Date enddate, Station station, int matchType, boolean shipped);
	public String getFoundReport(Date startdate, Date enddate, Station station, int matchType, boolean shipped);
	
	//LF tracing
	public List<LFMatchHistory> getTraceResultsForLost(long id);
	public List<LFMatchHistory> getTraceResultsForFound(long id);
	public List<LFMatchHistory> traceFoundItem(long id);
	public List<LFMatchHistory> traceLostItem(long id);
	public List<LFMatchHistory> traceFoundItemPrimary(long id);
	public List<LFMatchHistory> traceLostItemPrimary(long id);
	public List<LFMatchHistory> traceFoundItemSecondary(long id);
	public List<LFMatchHistory> traceLostItemSecondary(long id);
	public boolean confirmMatch(long id, Agent agent);
	public boolean rejectMatch(long id, Agent agent);
	public boolean undoMatch(long id, Agent agent);

	long saveOrUpdateDelivery(LFDelivery delivery);

	LFDelivery getDelivery(long id);

	LFMatchHistory getTraceResult(long id);

	public long saveOrUpdateTraceResult(LFMatchHistory match);

	LFItem getItem(long id);

	void getStillSearchingList();

	public long createManualMatch(LFLost lost, LFFound found);

	public long findConfirmedMatch(long lostId, long foundId)
			throws NonUniqueResultException;

	public boolean unrejectMatch(long id, Agent agent);

	public LFFound getFoundItemByBarcode(String barcode) throws NonUniqueBarcodeException;

	public List<LFMatchHistory> traceLostItem(long id, boolean isPrimary);
	public List<LFMatchHistory> traceFoundItem(long id, boolean isPrimary);

	public LFShipping getShipment(long id);

	public int getToBeShippedCount(Agent agent);

	public List<LFMatchHistory> getToBeShippedList(Agent agent, int start, int offset);
}
