package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;

@Remote
public interface LFCClientServiceRemote {
	public String echo(String s);

	public LostReportBean getLostReport(long id, String lastname);
	public long saveOrUpdateLostReport(LostReportBean lostReport);
	
	public ArrayList<KeyValueBean> getColors();
	public List<CategoryBean> getCategories(String companycode);
	
	public List<KeyValueBean> getCountries();
	public List<KeyValueBean> getStations(String companycode);
	public List<KeyValueBean> getState();

	public List<KeyValueBean> getStations(String companycode, String sub_company);

	public HashMap<String, ArrayList<KeyValueBean>> getStationsByState(
			String companycode, String sub_company);
}
