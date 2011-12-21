package aero.nettracer.lfc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.stereotype.Service;

import aero.nettracer.lfc.faces.util.FacesUtil;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LoginBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.remote.RemoteService;

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
				LostReportBean remote = RemoteService.getReport(Long.parseLong(id), name, company);
				if (remote != null) {
					if (remote.getReportId() != null) {
						return remote;
					}
					FacesUtil.addError("Server Communication Error.");
				} else {
					FacesUtil.addError("Last Name and Report ID combination not found.");
				}
			} else {
				if (name == null || name.length() == 0) {
					FacesUtil.addError("Last Name required.");
				}
				if (id == null || id.length() == 0) {
					FacesUtil.addError("Report ID required.");
				} else if (!id.matches("^[0-9]*$")){
					FacesUtil.addError("Report ID may only be numbers.");
				}
			}
		} else {
			FacesUtil.addError("Login Error!");
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
	public List<SelectItem> getLocations(String company) {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getLocations(company));
		return toReturn;
	}
	
	@Override
	public List<SelectItem> getLocationsByState(String company, String state) {
		HashMap<String, ArrayList<KeyValueBean>> locationList = RemoteService.getLocationsByState(company);
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
	public List<SelectItem> getColors(String company) {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getColors(company));
		return toReturn;
	}

	@Override
	public List<SelectItem> getStates(String company) {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getStates(company));
		return toReturn;
	}

	@Override
	public List<SelectItem> getCountries(String company) {
		List<SelectItem> toReturn = convertToSelectItem(RemoteService.getCountries(company));
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

}
