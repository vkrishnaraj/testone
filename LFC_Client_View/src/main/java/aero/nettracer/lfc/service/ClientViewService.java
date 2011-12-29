package aero.nettracer.lfc.service;

import java.util.List;

import javax.faces.model.SelectItem;

import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;

public interface ClientViewService {
	public String convertToUpperCase(String word);
	public LostReportBean login(LoginBean loginBean);
	public long create(LostReportBean lostReport);
	public List<SelectItem> getLocations(String subCompany);
	public List<CategoryBean> getCategories(String company);
	public List<SelectItem> getColors();
	public List<SelectItem> getStates();
	public List<SelectItem> getCountries();
	public List<SelectItem> getLocationsByState(String subCompany, String state);
}
