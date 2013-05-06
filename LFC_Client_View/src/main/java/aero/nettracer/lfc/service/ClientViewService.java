package aero.nettracer.lfc.service;

import java.util.List;

import javax.faces.model.SelectItem;

import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CCBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.RateBean;
import aero.nettracer.lfc.model.ShippingBean;

public interface ClientViewService {
	public String convertToUpperCase(String word);
	public LostReportBean login(LoginBean loginBean, String company);
	public LostReportBean loginShipping(LoginBean loginBean, String company, String hashKey);
	public long create(LostReportBean lostReport);
	public ShippingBean createAndShip(LostReportBean lostReport);
	public boolean sendConfirmationEmail(LostReportBean lostReport);
	public List<SelectItem> getLocations(String subCompany);
	public List<CategoryBean> getCategories(String company);
	public List<SelectItem> getColors();
	public List<SelectItem> getStates();
	public List<SelectItem> getCountries();
	public List<SelectItem> getLocationsByState(String subCompany, String state);
	public AddressBean validateAddressFedex(LostReportBean bean);
	public List<RateBean> getRatesForAddress(LostReportBean bean);
	public boolean authorizeCC(LostReportBean lostReport);
}
