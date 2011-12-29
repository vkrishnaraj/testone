package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLossInfo;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.ContactBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.PhoneBean;
import aero.nettracer.general.services.GeneralServiceBean;

@Stateless
public class LFCClientServiceBean implements LFCClientServiceRemote{

	@Override
	public String echo(String s) {
		return "echo: " + s;
	}

	@Override
	public LostReportBean getLostReport(long id, String lastname) {
		GeneralServiceBean gbean = new GeneralServiceBean();
		LFServiceBean bean = new LFServiceBean();
		LFLost host = bean.getLostReport(id, lastname);
		if(host == null){
			return null;
		}
		LostReportBean remote = new LostReportBean();
		remote.setSubCompany(host.getCompanyId());
		remote.setCompany(gbean.getCompanyFromSubCompany(host.getCompanyId()));
		
		remote.setWhereLost(host.getRemarks());
		
		if(host.getLossInfo() != null){
			remote.setMvaNumber(host.getLossInfo().getMvaNumber());
			remote.setAgreementNumber(host.getLossInfo().getAgreementNumber());
			remote.setDropOffLocation(host.getLossInfo().getDestinationId());
			remote.setPickUpLocation(host.getLossInfo().getOriginId());
			remote.setDateLost(host.getLossInfo().getLossdate());
		}
		
		if(host.getItem() != null){
			if(host.getItem().getDisposition() != null){
				remote.setDisposition(host.getItem().getDisposition().getDescription());
			}
			remote.setItemBrand(host.getItem().getBrand());
			remote.setItemColor(host.getItem().getColor());
			remote.setItemDesc(host.getItem().getDescription());
			remote.setItemSerial(host.getItem().getSerialNumber());
			remote.setItemSubCategory(host.getItem().getSubCategory());
			remote.setItemCategory(host.getItem().getCategory());
			remote.setTrackingNumber(host.getItem().getTrackingNumber());
		}
		
		remote.setReportId("" + host.getId());
		
		if(host.getStatus() != null){
			remote.setStatus(host.getStatus().getDescription());
		}
		
		if(host.getClient() != null){
			ContactBean contact = new ContactBean();
			contact.setConfirmEmail(host.getClient().getConfirmEmail());
			contact.setEmailAddress(host.getClient().getDecryptedEmail());
			contact.setFirstName(host.getClient().getFirstName());
			contact.setLastName(host.getClient().getLastName());
			contact.setMiddleInitial(host.getClient().getMiddleName());

			if(host.getClient().getAddress() != null){
				AddressBean address = new AddressBean();
				address.setAddress1(host.getClient().getAddress().getDecryptedAddress1());
				address.setAddress2(host.getClient().getAddress().getDecryptedAddress2());
				address.setCity(host.getClient().getAddress().getDecryptedCity());
				address.setCountry(host.getClient().getAddress().getCountry());
				address.setPostal(host.getClient().getAddress().getDecryptedZip());
				address.setProvince(host.getClient().getAddress().getDecryptedProvince());
				address.setState(host.getClient().getAddress().getDecryptedState());
				contact.setAddress(address);
			}

			if(host.getClient().getPhones() != null){
				for(LFPhone phone: host.getClient().getPhones()){
					PhoneBean toAdd = new PhoneBean();
					toAdd.setNumber(phone.getDecryptedPhoneNumber());
					toAdd.setType(phone.getNumberType());
					if(phone.getPhoneType() == TracingConstants.LF_PHONE_PRIMARY){
						contact.setPrimaryPhone(toAdd);
					}
					if(phone.getPhoneType() == TracingConstants.LF_PHONE_SECONDARY){
						contact.setSecondaryPhone(toAdd);
					}
				}
			}
			remote.setContact(contact);
		}
		
		return remote;
	}

	private Agent getWebAgent(){
		GeneralServiceBean bean = new GeneralServiceBean();
		return bean.getAgent("avisweb", TracerProperties.get("wt.company.code"));
	}
	
	@Override
	public long saveOrUpdateLostReport(LostReportBean lostReport) {
		if(lostReport == null){
			return -1;
		}
		LFLost host = new LFLost();
		
		Agent agent = getWebAgent();
		//TODO web agent
		host.setAgent(agent);
		
		host.setVantiveNumber(lostReport.getVantiveNumber());
		host.setCompanyId(lostReport.getSubCompany());
		
		Station station = StationBMO.getStationByCode("WEB", TracerProperties.get("wt.company.code"));
		//TODO web location
		host.setLocation(station);
		
		//TODO normalize dates
		host.setOpenDate(new Date());
		
		host.setRemarks(lostReport.getWhereLost());
		
		LFItem item = new LFItem();
		item.setBrand(lostReport.getItemBrand());
		item.setCategory(lostReport.getItemCategory());
		item.setColor(lostReport.getItemColor());
		item.setDescription(lostReport.getItemDesc());
		Status disposition = new Status();
		disposition.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
		item.setDisposition(disposition);
		item.setLost(host);
		item.setSerialNumber(lostReport.getItemSerial());
		item.setSubCategory(lostReport.getItemSubCategory());
		item.setType(TracingConstants.LF_TYPE_LOST);
		host.setItem(item);
		
		LFLossInfo lossinfo = new LFLossInfo();
		lossinfo.setAgreementNumber(lostReport.getAgreementNumber());
		Station dropoff = new Station();
		dropoff.setStation_ID(lostReport.getDropOffLocation());
		lossinfo.setDestination(dropoff);
		lossinfo.setMvaNumber(lostReport.getMvaNumber());
		Station pickup = new Station();
		pickup.setStation_ID(lostReport.getPickUpLocation());
		lossinfo.setOrigin(pickup);
		lossinfo.setLossdate(lostReport.getDateLost());
		host.setLossInfo(lossinfo);
		
		if(lostReport.getContact() != null){
			LFPerson client = new LFPerson();
			
			client.setConfirmEmail(lostReport.getContact().getConfirmEmail());
			client.setDecryptedEmail(lostReport.getContact().getEmailAddress());
			client.setFirstName(lostReport.getContact().getFirstName());
			client.setLastName(lostReport.getContact().getLastName());
			client.setMiddleName(lostReport.getContact().getMiddleInitial());
			
			HashSet<LFPhone> phones = new HashSet<LFPhone>();
			if(lostReport.getContact().getPrimaryPhone() != null){
				LFPhone toAdd = new LFPhone();
				toAdd.setPhoneType(TracingConstants.LF_PHONE_PRIMARY);
				toAdd.setNumberType(lostReport.getContact().getPrimaryPhone().getType());
				toAdd.setPerson(client);
				toAdd.setDecryptedPhoneNumber(lostReport.getContact().getPrimaryPhone().getNumber());
				phones.add(toAdd);
			}
			if(lostReport.getContact().getSecondaryPhone() != null){
				LFPhone toAdd = new LFPhone();
				toAdd.setPhoneType(TracingConstants.LF_PHONE_SECONDARY);
				toAdd.setNumberType(lostReport.getContact().getSecondaryPhone().getType());
				toAdd.setPerson(client);
				toAdd.setDecryptedPhoneNumber(lostReport.getContact().getSecondaryPhone().getNumber());
				phones.add(toAdd);
			}
			if(phones.size() > 0){
				client.setPhones(phones);
			}

			if(lostReport.getContact().getAddress() != null){
				LFAddress address = new LFAddress();
				address.setDecryptedAddress1(lostReport.getContact().getAddress().getAddress1());
				address.setDecryptedAddress2(lostReport.getContact().getAddress().getAddress2());
				address.setDecryptedCity(lostReport.getContact().getAddress().getCity());
				address.setCountry(lostReport.getContact().getAddress().getCountry());
				address.setDecryptedProvince(lostReport.getContact().getAddress().getProvince());
				address.setDecryptedState(lostReport.getContact().getAddress().getState());
				address.setDecryptedZip(lostReport.getContact().getAddress().getPostal());
				client.setAddress(address);
			}
			
			
			host.setClient(client);
		}	
		
		
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		host.setStatus(status);
		
		LFServiceBean bean = new LFServiceBean();
		return bean.saveOrUpdateLostReport(host, getWebAgent());
	}

	@Override
	public ArrayList<KeyValueBean> getColors() {
		LFServiceBean bean = new LFServiceBean();
		ArrayList<LabelValueBean> colors = bean.getColors();
		if(colors == null){
			return null;
		}
		ArrayList<KeyValueBean>ret = new ArrayList<KeyValueBean>();
		for(LabelValueBean color:colors){
			KeyValueBean toAdd = new KeyValueBean();
			toAdd.setKey(color.getValue());
			toAdd.setValue(color.getLabel());
			ret.add(toAdd);
		}
		return ret;
	}

	@Override
	public List<CategoryBean> getCategories(String companycode) {
		LFServiceBean bean = new LFServiceBean();
		List<LFCategory> categories = bean.getCategories(companycode);
		if(categories == null){
			return null;
		}
		ArrayList<CategoryBean> ret = new ArrayList<CategoryBean>();
		for(LFCategory lfcat:categories){
			CategoryBean cat = new CategoryBean();
			cat.setDescription(lfcat.getDescription());
			cat.setId(lfcat.getId());
			if(lfcat.getSubcategories() != null){
				LinkedHashSet<KeyValueBean> subSet = new LinkedHashSet<KeyValueBean>();
				for(LFSubCategory lfsub:lfcat.getSubcategories()){
					KeyValueBean sub = new KeyValueBean();
					sub.setKey("" + lfsub.getId());
					sub.setValue(lfsub.getDescription());
					subSet.add(sub);
				}
				cat.setSubcategories(subSet);
			}
			ret.add(cat);
		}
		return ret;
	}

	@Override
	public List<KeyValueBean> getCountries() {
		GeneralServiceBean bean = new GeneralServiceBean();
		List<CountryCode> countries = bean.getCountries();
		if(countries == null){
			return null;
		}
		ArrayList<KeyValueBean>ret = new ArrayList<KeyValueBean>();
		for(CountryCode country:countries){
			KeyValueBean toAdd = new KeyValueBean();
			toAdd.setKey(country.getCountryCode_ID());
			toAdd.setValue(country.getCountry());
			ret.add(toAdd);
		}
		return ret;
	}

	@Override
	public HashMap<String,ArrayList<KeyValueBean>> getStationsByState(String companycode, String sub_company){
		GeneralServiceBean bean = new GeneralServiceBean();
		List<Station> stations = null;
		
		//get stations
		if(sub_company != null){
			List<String> companies = new ArrayList<String>();
			if (!sub_company.equals(TracingConstants.LF_ABG_COMPANY_ID)) {
				companies.add(TracingConstants.LF_ABG_COMPANY_ID);
			}
			companies.add(sub_company);
			stations = bean.getStations(companycode, companies);
		} else {
			stations = bean.getStations(companycode);
		}
		if(stations == null){
			return null;
		}
		HashMap<String, ArrayList<KeyValueBean>> map = new HashMap<String, ArrayList<KeyValueBean>>();
		
		//populate station map by state
		for(Station station:stations){
			KeyValueBean toAdd = new KeyValueBean();
			toAdd.setKey("" + station.getStation_ID());
			toAdd.setValue(station.getStationdesc());
			String key = station.getState_ID()!=null&&station.getState_ID().trim().length()>0?station.getState_ID():"XX";
			if(!map.containsKey(key)){
				map.put(key, new ArrayList<KeyValueBean>());
			}
			map.get(key).add(toAdd);
		}
		return map;
	}
	
	@Override
	public List<KeyValueBean> getStations(String companycode, String sub_company) {
		GeneralServiceBean bean = new GeneralServiceBean();
		List<Station> stations = null;
		
		if(sub_company != null){
			List<String> companies = new ArrayList<String>();
			if (!sub_company.equals(TracingConstants.LF_ABG_COMPANY_ID)) {
				companies.add(TracingConstants.LF_ABG_COMPANY_ID);
			}
			companies.add(sub_company);
			stations = bean.getStations(companycode, companies);
		} else {
			stations = bean.getStations(companycode);
		}
		if(stations == null){
			return null;
		}
		ArrayList<KeyValueBean>ret = new ArrayList<KeyValueBean>();
		for(Station station:stations){
			KeyValueBean toAdd = new KeyValueBean();
			toAdd.setKey("" + station.getStation_ID());
			toAdd.setValue(station.getStationdesc());
			ret.add(toAdd);
		}
		return ret;
	}
	
	@Override
	public List<KeyValueBean> getStations(String companycode) {
		return getStations(companycode, null);
	}

	@Override
	public List<KeyValueBean> getState() {
		GeneralServiceBean bean = new GeneralServiceBean();
		List<State> states = bean.getState();
		if(states == null){
			return null;
		}
		ArrayList<KeyValueBean>ret = new ArrayList<KeyValueBean>();
		for(State state:states){
			KeyValueBean toAdd = new KeyValueBean();
			toAdd.setKey(state.getState_ID());
			toAdd.setValue(state.getState());
			ret.add(toAdd);
		}
		
		return ret;
	}

}
