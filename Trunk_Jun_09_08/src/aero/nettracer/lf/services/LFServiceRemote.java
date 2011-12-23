package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

@Remote
public interface LFServiceRemote {
	public String echo(String s);

	public LFLost getLostReport(long id, String lastname);
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent);
	
	public ArrayList<LabelValueBean> getColors();
	public List<LFCategory> getCategories(String companycode);

}
