package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import org.apache.struts.util.LabelValueBean;

import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

@Remote
public interface LFServiceRemote {
	public String echo(String s);

	public LFLost getLostReport(long id, String lastname);
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent) throws NonUniqueBarcodeException, UpdateException;
	
	public ArrayList<LabelValueBean> getColors();
	public List<LFCategory> getCategories(String companycode);
	public List<Subcompany> getSubcompanies(String companycode);
	public List<SubcompanyStation> getSubcompanyStations(String companycode);

	public List<LFMatchHistory> traceFoundItem(long id);
	public List<LFMatchHistory> traceLostItem(long id);
	public List<LFMatchHistory> traceFoundItemPrimary(long id);
	public List<LFMatchHistory> traceLostItemPrimary(long id);
	public List<LFMatchHistory> traceFoundItemSecondary(long id);
	public List<LFMatchHistory> traceLostItemSecondary(long id);
	
	public List<LFMatchHistory> traceFoundItem(long id, boolean isPrimary);
	public List<LFMatchHistory> traceLostItem(long id, boolean isPrimary);
	
	public LFFound getFoundItemByBarcode(String barcode) throws NonUniqueBarcodeException;

}
