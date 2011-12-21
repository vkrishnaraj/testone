package aero.nettracer.lfc.service;

import java.util.List;

import javax.faces.model.SelectItem;

import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;

public interface ClientViewService {
	public String convertToUpperCase(String word);
	public LostReportBean login(LoginBean loginBean, String company);
	public long create(LostReportBean lostReport);
	public List<SelectItem> getLocations(String company);
	public List<CategoryBean> getCategories(String company);
	public List<SelectItem> getColors(String company);
	public List<SelectItem> getStates(String company);
	public List<SelectItem> getCountries(String company);
	public List<SelectItem> getLocationsByState(String company, String state);
}
