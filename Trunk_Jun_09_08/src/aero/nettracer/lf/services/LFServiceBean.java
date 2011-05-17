package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

@Stateless
public class LFServiceBean implements LFServiceRemote, LFServiceHome{

	@Override
	public String echo(String s) {
		return "echo: " + s;
	}
	
	@Override
	public LFLost getLostReport(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LFLost getLostReport(long id, String lastname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int searchLostCount(LFSearchDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFLost> searchLost(LFSearchDTO dto, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long saveOrUpdateLostReport(LFLost lostReport) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LFFound getFoundItem(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int searchFoundCount(LFSearchDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFFound> searchFound(LFSearchDTO dto, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long saveOrUpdateFoundItem(LFFound foundItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<LabelValueBean> getColors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFCategory> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closeLostReport(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendStillSearching(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLostCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFLost> getLostPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFoundCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFFound> getFoundPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFLost> getLostReportToClose(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemsToSalvageCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFFound> getItemsToSalvagePaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTraceResultsCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsPaginated(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDeliveryPendingCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFDelivery> getDeliveryPendingPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLostReport(Date startdate, Date enddate, Station station,
			int matchType, boolean shipped) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFoundReport(Date startdate, Date enddate, Station station,
			int matchType, boolean shipped) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsForLost(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsForFound(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void traceFoundItem(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceLostItem(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirmMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}




}
