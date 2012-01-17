package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import org.apache.struts.util.LabelValueBean;

import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

@Remote
public interface LFServiceRemote {
	public String echo(String s);

	public LFLost getLostReport(long id, String lastname);
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent);
	
	public ArrayList<LabelValueBean> getColors();
	public List<LFCategory> getCategories(String companycode);

	public List<LFMatchHistory> traceFoundItem(long id);
	
	public LFFound getFoundItemByBarcode(String barcode) throws NonUniqueBarcodeException;
}
