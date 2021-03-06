package aero.nettracer.lfc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.stereotype.Service;

import aero.nettracer.lfc.faces.util.FacesUtil;
import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CCBean;
import aero.nettracer.lfc.model.RateBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.ShippingBean;
import aero.nettracer.lfc.remote.RemoteService;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

@Service
public class ClientViewServiceImpl implements ClientViewService {

	@Override
	public String convertToUpperCase(String word) {
		return word.toUpperCase();
	}

	@Override
	public LostReportBean login(LoginBean loginBean, String company) {
		if (loginBean != null) {
			String name = loginBean.getLastName();
			String id = loginBean.getTrackingNumber();
			if (name != null && id != null && name.length() > 0 && id.length() > 0 && id.matches("^[0-9]*$")) {
				LostReportBean remote = null;
				if (company != null && company.equals(TracingConstants.LF_AB_COMPANY_ID)) {
					remote = RemoteService.getReportAB(Long.parseLong(id), name);
				} else {
					remote = RemoteService.getReportLF(Long.parseLong(id), name);
				}
				if (remote != null) {
					if (remote.getReportId() != null) {
						return remote;
					}
					FacesUtil.addError("ERROR: Server Communication Error.");
				} else {
					FacesUtil.addError("ERROR: Last Name and Report ID combination not found.");
				}
			} else {
				if (name == null || name.length() == 0) {
					FacesUtil.addError("ERROR: Last Name required.");
				}
				if (id == null || id.length() == 0) {
					FacesUtil.addError("ERROR: Report ID required.");
				} else if (!id.matches("^[0-9]*$")){
					FacesUtil.addError("ERROR: Report ID may only be numbers.");
				}
			}
		} else {
			FacesUtil.addError("ERROR: Login Error!");
		}
		return null;
	}
	
	@Override
	public LostReportBean loginShipping(LoginBean loginBean, String company, String hashKey) {
		if (loginBean != null) {
			String name = loginBean.getLastName();
			String id = loginBean.getTrackingNumber();
			if (name != null && id != null && name.length() > 0 && id.length() > 0 && id.matches("^[0-9]*$")) {
				LostReportBean remote = null; 
				if (company != null && company.equals(TracingConstants.LF_AB_COMPANY_ID)) {
					remote = RemoteService.getReportAB(Long.parseLong(id), name);
				} else {
					remote = RemoteService.getReportShippingLF(Long.parseLong(id), name); //Check for HashKey before getting report 
				}
				if (remote != null) {
					if (remote.getReportId() != null) {
//						if(remote.getContact().getPrefshipaddress()==null || remote.getContact().getPrefshipaddress().getAddress1()==null){
//							remote.getContact().setPrefshipaddress(new AddressBean());
//							remote.getContact().getPrefshipaddress().setAddress1(remote.getContact().getAddress().getAddress1());
//							remote.getContact().getPrefshipaddress().setAddress2(remote.getContact().getAddress().getAddress2());
//							remote.getContact().getPrefshipaddress().setCity(remote.getContact().getAddress().getCity());
//							remote.getContact().getPrefshipaddress().setState(remote.getContact().getAddress().getState());
//							remote.getContact().getPrefshipaddress().setPostal(remote.getContact().getAddress().getPostal());
//							remote.getContact().getPrefshipaddress().setProvince(remote.getContact().getAddress().getProvince());
//							remote.getContact().getPrefshipaddress().setCountry(remote.getContact().getAddress().getCountry());
//						}
						return remote;
					}
					FacesUtil.addError("ERROR: Server Communication Error.");
				} else {
					FacesUtil.addError("ERROR: Last Name and Report ID combination not found or Shipping Information was not created properly.");
				}
			} else {
				if (name == null || name.length() == 0) {
					FacesUtil.addError("ERROR: Last Name required.");
				}
				if (id == null || id.length() == 0) {
					FacesUtil.addError("ERROR: Report ID required.");
				} else if (!id.matches("^[0-9]*$")){
					FacesUtil.addError("ERROR: Report ID may only be numbers.");
				}
			}
		} else {
			FacesUtil.addError("ERROR: Login Error!");
		}
		return null;
	}

	@Override
	public long create(LostReportBean lostReport) {
		if (lostReport != null) {
			return RemoteService.createReport(lostReport);
		}
		return -1;
	}
	
	@Override
	public ShippingBean createAndShip(LostReportBean lostReport) {
		ShippingBean shipment=new ShippingBean(); 
		if (lostReport != null) {
			return RemoteService.createReportAndShip(lostReport);
		}
		return null;
	}
	
	@Override 
	public AddressBean validateAddressFedex(LostReportBean bean){
		if(bean!=null){
			return RemoteService.validateAddressFedex(bean);
		}
		return null;
	}
	
	@Override 
	public List<RateBean> getRatesForAddress(LostReportBean bean){
		if(bean!=null){
			List<RateBean> toReturn=RemoteService.getRatesForAddress(bean);
			return toReturn;
		}
		return null;
	}

	@Override
	public boolean sendConfirmationEmail(LostReportBean lostReport) {
		if (lostReport != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<SelectItem> getLocations(String subCompany) {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getLocations(subCompany));
		return toReturn;
	}
	
	@Override
	public List<SelectItem> getLocationsByState(String subCompany, String state) {
		HashMap<String, ArrayList<KeyValueBean>> locationList = RemoteService.getLocationsByState(subCompany);
		List<SelectItem> toReturn = new ArrayList<SelectItem>();
		if (locationList != null && state != null && state.length() > 0) {
			List<KeyValueBean> locationsByState = locationList.get(state);
			if (locationsByState != null) {
				toReturn = convertToSelectItem(locationsByState);
			}
		}
		return toReturn;
	}
	
	@Override
	public List<CategoryBean> getCategories(String company) {
		List<CategoryBean> toReturn = RemoteService.getCategories(company);
		return toReturn;
	}

	@Override
	public List<SelectItem> getColors() {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getColors());
		return toReturn;
	}

	@Override
	public List<SelectItem> getStates() {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getStates());
		return toReturn;
	}

	@Override
	public List<SelectItem> getCountries() {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getCountries());
		return toReturn;
	}
	
	private List<SelectItem> convertToSelectItem(List<KeyValueBean> keys) {
		List<SelectItem> toReturn = new ArrayList<SelectItem>();
		if (keys != null) {
			for(KeyValueBean sel : keys) {
				if (sel != null) {
					toReturn.add(new SelectItem(sel.getKey(), sel.getValue()));
				}
			}
		}
		if (toReturn.size() == 0) {
			toReturn.add(new SelectItem("XXX", "Remote Error"));
		}
		return toReturn;
	}
	
	private List<SelectItem> ratesToSelectItem(List<RateBean> rates) {
		List<SelectItem> toReturn = new ArrayList<SelectItem>();
		if (rates != null) {
			for(RateBean sel : rates) {
				if (sel != null) {
					toReturn.add(new SelectItem(sel.getRateKey(), sel.getRateType()+" - "+sel.getRateAmount()));
				}
			}
		}
		if (toReturn.size() == 0) {
			toReturn.add(new SelectItem("XXX", "Remote Error"));
		}
		return toReturn;
	}
	
	private List<SelectItem> convertToSelectItemIntKeys(List<KeyValueBean> keys) {
		List<SelectItem> toReturn = new ArrayList<SelectItem>();
		if (keys != null) {
			for(KeyValueBean sel : keys) {
				if (sel != null && sel.getKey() != null && sel.getKey().matches("^[0-9]*$")) {
					toReturn.add(new SelectItem(Integer.parseInt(sel.getKey()), sel.getValue()));
				}
			}
		}
		if (toReturn.size() == 0) {
			toReturn.add(new SelectItem("XXX", "Remote Error"));
		}
		return toReturn;
	}

	@Override
	public boolean authorizeCC(LostReportBean lostReport) {
		if(lostReport!=null && lostReport.getCc()!=null){
			return RemoteService.authorizeCc(lostReport);
		}
		return false;
	}

}
