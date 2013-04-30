package aero.nettracer.lf.services;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import jxl.write.NumberFormat;

import org.apache.axis2.AxisFault;
import org.apache.struts.util.LabelValueBean;
import org.springframework.beans.BeanUtils;

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
import com.bagnet.nettracer.tracing.db.lf.LFSegment;
import com.bagnet.nettracer.tracing.db.lf.LFShipping;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.lf.RemoteConnectionException;
import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;


import com.fedex.ws.addressvalidation.v2.Address;
import com.fedex.ws.addressvalidation.v2.AddressToValidate;
import com.fedex.ws.addressvalidation.v2.AddressValidationAccuracyType;
import com.fedex.ws.addressvalidation.v2.AddressValidationChangeType;
import com.fedex.ws.addressvalidation.v2.AddressValidationChangeType.Enum;
import com.fedex.ws.addressvalidation.v2.AddressValidationOptions;
import com.fedex.ws.addressvalidation.v2.AddressValidationReply;
import com.fedex.ws.addressvalidation.v2.AddressValidationRequest;
import com.fedex.ws.addressvalidation.v2.AddressValidationRequestDocument;
import com.fedex.ws.addressvalidation.v2.AddressValidationResult;
import com.fedex.ws.addressvalidation.v2.AddressValidationServiceStub;
import com.fedex.ws.addressvalidation.v2.ClientDetail;
import com.fedex.ws.addressvalidation.v2.Notification;
import com.fedex.ws.addressvalidation.v2.NotificationSeverityType;
import com.fedex.ws.addressvalidation.v2.ProposedAddressDetail;
import com.fedex.ws.addressvalidation.v2.TransactionDetail;
import com.fedex.ws.addressvalidation.v2.VersionId;
import com.fedex.ws.addressvalidation.v2.WebAuthenticationCredential;
import com.fedex.ws.addressvalidation.v2.WebAuthenticationDetail;

import aero.nettracer.lf.services.exception.UpdateException;
import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.ContactBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.PhoneBean;
import aero.nettracer.lfc.model.RateBean;
import aero.nettracer.lfc.model.SegmentBean;
import aero.nettracer.lfc.model.ShippingBean;
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
		remote.setCompany(host.getAgent().getCompanycode_ID()); //gbean.getCompanyFromSubCompany(host.getCompanyId())); ??
		
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
			remote.setItemSize(host.getItem().getSize());
			remote.setItemLongDesc(host.getItem().getLongDescription());
			remote.setItemCaseColor(host.getItem().getCaseColor());
			remote.setItemModel(host.getItem().getModel());
			if(host.getShipment()!=null){
				remote.setShippingOption(host.getShipment().getShippingOption());
				remote.setShippingPayment(host.getShipment().getTotalPayment());
			}
			if(host.getItem().getPhone() != null){
				PhoneBean phone = new PhoneBean();
				phone.setNumber(host.getItem().getPhone().getDecryptedPhoneNumber());
				phone.setCountry(host.getItem().getPhone().getDecryptedCountry());
				phone.setArea(host.getItem().getPhone().getDecryptedArea());
				phone.setExchange(host.getItem().getPhone().getDecryptedExchange());
				phone.setLine(host.getItem().getPhone().getDecryptedLine());
				phone.setExtension(host.getItem().getPhone().getExtension());
				remote.setLostPhone(phone);
			}

			remote.setFirstNameBag(host.getFirstName());
			remote.setLastNameBag(host.getLastName());
			remote.setMiddleNameBag(host.getMiddleName());
		}
		
		remote.setReportId("" + host.getId());
		
		if(host.getStatus() != null){
			remote.setStatus(host.getStatus().getDescription());
		}
		
		if(TracingConstants.LF_LF_COMPANY_ID.equals(remote.getCompany())){
			remote.setDaysFromCreate( (Calendar.getInstance().getTime().getTime() - host.getOpenDate().getTime() ) /(1000*60*60*24) );
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
			
			if(host.getShipment()!=null){
				if(host.getShipment().getShippingAddress() != null){
					AddressBean address = new AddressBean();
					address.setAddress1(host.getShipment().getShippingAddress().getDecryptedAddress1());
					address.setAddress2(host.getShipment().getShippingAddress().getDecryptedAddress2());
					address.setCity(host.getShipment().getShippingAddress().getDecryptedCity());
					address.setCountry(host.getShipment().getShippingAddress().getCountry());
					address.setPostal(host.getShipment().getShippingAddress().getDecryptedZip());
					address.setProvince(host.getShipment().getShippingAddress().getDecryptedProvince());
					address.setState(host.getShipment().getShippingAddress().getDecryptedState());
					contact.setPrefshipaddress(address);
				}
				
				if(host.getShipment().getBillingAddress() != null){
					AddressBean address = new AddressBean();
					address.setAddress1(host.getShipment().getBillingAddress().getDecryptedAddress1());
					address.setAddress2(host.getShipment().getBillingAddress().getDecryptedAddress2());
					address.setCity(host.getShipment().getBillingAddress().getDecryptedCity());
					address.setCountry(host.getShipment().getBillingAddress().getCountry());
					address.setPostal(host.getShipment().getBillingAddress().getDecryptedZip());
					address.setProvince(host.getShipment().getBillingAddress().getDecryptedProvince());
					address.setState(host.getShipment().getBillingAddress().getDecryptedState());
					contact.setBillingaddress(address);
				}
				
				remote.setShippingOption(host.getShipment().getShippingOption());
				remote.setDeclaredValue(host.getShipment().getDeclaredValue());
			}

			if(host.getClient().getPhones() != null){
				for(LFPhone phone: host.getClient().getPhones()){
					PhoneBean toAdd = new PhoneBean();
					toAdd.setNumber(phone.getDecryptedPhoneNumber());
					toAdd.setCountry(phone.getDecryptedCountry());
					toAdd.setArea(phone.getDecryptedArea());
					toAdd.setExchange(phone.getDecryptedExchange());
					toAdd.setLine(phone.getDecryptedLine());
					toAdd.setType(phone.getNumberType());
					if(phone.getPhoneType() == TracingConstants.LF_PHONE_PRIMARY){
						contact.setPrimaryPhone(toAdd);

						if(host.getShipment()!=null && host.getShipment().getShippingPhone()==null){
							PhoneBean toAdd2 = new PhoneBean();
							BeanUtils.copyProperties(toAdd, toAdd2);
							contact.setShippingPhone(toAdd2);
						}
					}
					if(phone.getPhoneType() == TracingConstants.LF_PHONE_SECONDARY){
						contact.setSecondaryPhone(toAdd);
					}
				}
				
				if(host.getShipment()!=null && host.getShipment().getShippingPhone()!=null){
					PhoneBean toAdd = new PhoneBean();
					toAdd.setNumber(host.getShipment().getShippingPhone().getDecryptedPhoneNumber());
					toAdd.setCountry(host.getShipment().getShippingPhone().getDecryptedCountry());
					toAdd.setArea(host.getShipment().getShippingPhone().getDecryptedArea());
					toAdd.setExchange(host.getShipment().getShippingPhone().getDecryptedExchange());
					toAdd.setLine(host.getShipment().getShippingPhone().getDecryptedLine());
					toAdd.setType(host.getShipment().getShippingPhone().getNumberType());
					toAdd.setExtension(host.getShipment().getShippingPhone().getExtension());
					contact.setShippingPhone(toAdd);
				} 
					
			}
			remote.setContact(contact);
		}
		
		if (host.getSegments() != null) {
			List<SegmentBean> remoteSegs = new ArrayList<SegmentBean>();
			for (LFSegment seg : host.getSegments()) {
				SegmentBean remoteSeg = new SegmentBean();
				remoteSeg.setArrivalLocation(seg.getDestinationId());
				remoteSeg.setArrivalLocationDesc(seg.getDestination().getStationdesc());
				remoteSeg.setDepartureLocation(seg.getOriginId());
				remoteSeg.setDepartureLocationDesc(seg.getOrigin().getStationdesc());
				remoteSeg.setFlightNumber(seg.getFlightNumber());
				remoteSeg.setId(seg.getId());
				remoteSegs.add(remoteSeg);
			}
			remote.setSegments(remoteSegs);
		}
		
		return remote;
	}
	

	@Override
	public LostReportBean getLostReportShipping(long id, String lastname) {
		LFServiceBean bean=new LFServiceBean();
		if(bean.getShipment(id)!=null){
			return getLostReport(id,lastname);
		}
		return null;
		
	}

	private Agent getWebAgent(){
		GeneralServiceBean bean = new GeneralServiceBean();
		return bean.getAgent("webagent", TracerProperties.get("wt.company.code"));
	}
	
	
	@Override
	public long saveOrUpdateLostReport(LostReportBean lostReport) {
		
		if(lostReport == null){
			return -1;
		}// else if (lostReport.IsShipping() && !FedexUtils.validateAddressFedex(lostReport.getContact().getPrefshipaddress())){
//			return -1;
//		}
		
		LFLost host = null;
		boolean update = false;

		if (lostReport.getReportId()!=null) {
			LFServiceBean bean = new LFServiceBean();
			host = bean.getLostReport(Long.valueOf(lostReport.getReportId()));
//			shipment = bean.getShipment(Long.valueOf(lostReport.getReportId()));
		}
		
		
		
		if (host != null) {
			update = true;
		} else {
			host = new LFLost();
		}
		
		if (!update) {
			Agent agent = getWebAgent();
			//TODO web agent
			host.setAgent(agent);
			host.setCompanyId(lostReport.getSubCompany());
			
			Station station = StationBMO.getStationByCode("WEB", TracerProperties.get(agent.getCompanycode_ID(),"wt.company.code"));
			//TODO web location
			host.setLocation(station);
			
			//TODO normalize dates
			host.setOpenDate(new Date());
			
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
			host.setStatus(status);
		}
		
		host.setVantiveNumber(lostReport.getVantiveNumber());
		
		host.setRemarks(lostReport.getWhereLost());
		
		double insuredValue=0;
		if(lostReport.getDeclaredValue()>=150){
			DecimalFormat format = (DecimalFormat) java.text.NumberFormat.getInstance();
			format.applyPattern("##0.00");
			format.setMinimumFractionDigits(2);
			insuredValue = Double.valueOf(format.format((lostReport.getDeclaredValue()/100)*.85));
		}
		
		LFItem item = new LFItem();
		if (update) {
			item = host.getItem();
		}
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
		item.setModel(lostReport.getItemModel());
		item.setCaseColor(lostReport.getItemCaseColor());
		item.setLongDescription(lostReport.getItemLongDesc());
		item.setSize(lostReport.getItemSize());
		
		if(lostReport.getLostPhone() != null){
			LFPhone lostPhone = new LFPhone();
			if (update && item.getPhone()!=null) {
				lostPhone = item.getPhone();
			}
			lostPhone.setDecryptedPhoneNumber(lostReport.getLostPhone().getNumber());
			lostPhone.setDecryptedCountry(lostReport.getLostPhone().getCountry());
			lostPhone.setDecryptedArea(lostReport.getLostPhone().getArea());
			lostPhone.setDecryptedExchange(lostReport.getLostPhone().getExchange());
			lostPhone.setDecryptedLine(lostReport.getLostPhone().getLine());
			lostPhone.setPhoneType(lostReport.getLostPhone().getType());
			lostPhone.setItem(item);
			
			if (!update || item.getPhone()==null) {
				item.setPhone(lostPhone);
			}
		}
		
		if (!update) {
			host.setItem(item);
		}
		host.setFirstName(lostReport.getFirstNameBag());
		host.setLastName(lostReport.getLastNameBag());
		host.setMiddleName(lostReport.getMiddleNameBag());
		
		LFLossInfo lossinfo = new LFLossInfo();
		if (update) {
			lossinfo = host.getLossInfo();
		}
		
		lossinfo.setAgreementNumber(lostReport.getAgreementNumber());
		if (lostReport.getDropOffLocation() > 0) {
			Station dropoff = new Station();
			dropoff.setStation_ID(lostReport.getDropOffLocation());
			lossinfo.setDestination(dropoff);
		}
		lossinfo.setMvaNumber(lostReport.getMvaNumber());
		if (lostReport.getPickUpLocation() > 0) {
			Station pickup = new Station();
			pickup.setStation_ID(lostReport.getPickUpLocation());
			lossinfo.setOrigin(pickup);
		}
		lossinfo.setLossdate(lostReport.getDateLost());
		
		if (!update) {
			host.setLossInfo(lossinfo);
		}
		
		if(lostReport.getContact() != null){
			LFPerson client = new LFPerson();
			if (update) {
				client = host.getClient();
			}
			
			client.setConfirmEmail(lostReport.getContact().getConfirmEmail());
			client.setDecryptedEmail(lostReport.getContact().getEmailAddress());
			client.setFirstName(lostReport.getContact().getFirstName());
			client.setLastName(lostReport.getContact().getLastName());
			client.setMiddleName(lostReport.getContact().getMiddleInitial());
			
			Set<LFPhone> phones = new HashSet<LFPhone>();
			Iterator<LFPhone> phoneIter = null;
			boolean addPhone = true;
			if (update) {
				phones = client.getPhones();
				phoneIter = phones.iterator();
			}
			if(lostReport.getContact().getPrimaryPhone() != null){
				LFPhone toAdd = new LFPhone();
				if (update && phoneIter != null && phoneIter.hasNext()) {
					toAdd = phoneIter.next();
					addPhone = false;
				}
				toAdd.setPhoneType(TracingConstants.LF_PHONE_PRIMARY);
				toAdd.setNumberType(lostReport.getContact().getPrimaryPhone().getType());
				toAdd.setPerson(client);
				toAdd.setDecryptedPhoneNumber(lostReport.getContact().getPrimaryPhone().getNumber());
				toAdd.setDecryptedCountry(lostReport.getContact().getPrimaryPhone().getCountry());
				toAdd.setDecryptedArea(lostReport.getContact().getPrimaryPhone().getArea());
				toAdd.setDecryptedExchange(lostReport.getContact().getPrimaryPhone().getExchange());
				toAdd.setDecryptedLine(lostReport.getContact().getPrimaryPhone().getLine());
				if (addPhone) {
					phones.add(toAdd);
				}
			}
			addPhone = true;
			if(lostReport.getContact().getSecondaryPhone() != null){
				LFPhone toAdd = new LFPhone();
				if (update && phoneIter != null  && phoneIter.hasNext()) {
					toAdd = phoneIter.next();
					addPhone = false;
				}
				toAdd.setPhoneType(TracingConstants.LF_PHONE_SECONDARY);
				toAdd.setNumberType(lostReport.getContact().getSecondaryPhone().getType());
				toAdd.setPerson(client);
				toAdd.setDecryptedPhoneNumber(lostReport.getContact().getSecondaryPhone().getNumber());
				toAdd.setDecryptedCountry(lostReport.getContact().getSecondaryPhone().getCountry());
				toAdd.setDecryptedArea(lostReport.getContact().getSecondaryPhone().getArea());
				toAdd.setDecryptedExchange(lostReport.getContact().getSecondaryPhone().getExchange());
				toAdd.setDecryptedLine(lostReport.getContact().getSecondaryPhone().getLine());
				if (addPhone) {
					phones.add(toAdd);
				}
			}
			
			if(!update && phones.size() > 0){
				client.setPhones(phones);
			}

			if(lostReport.getContact().getAddress() != null){
				LFAddress address = new LFAddress();
				if (update) {
					address = client.getAddress();
				}
				address.setDecryptedAddress1(lostReport.getContact().getAddress().getAddress1());
				address.setDecryptedAddress2(lostReport.getContact().getAddress().getAddress2());
				address.setDecryptedCity(lostReport.getContact().getAddress().getCity());
				address.setCountry(lostReport.getContact().getAddress().getCountry());
				address.setDecryptedProvince(lostReport.getContact().getAddress().getProvince());
				address.setDecryptedState(lostReport.getContact().getAddress().getState());
				address.setDecryptedZip(lostReport.getContact().getAddress().getPostal());
				if (!update) {
					client.setAddress(address);
				}
			}
			
			if (!update) {
				host.setClient(client);
			}
		}
		
		if(lostReport.getReportId()!=null) {
			host.setId(Long.valueOf(lostReport.getReportId()));
		}
		
		if (lostReport.getSegments() != null && TracingConstants.LF_SWA_COMPANY_ID.equals(host.getCompanyId())) {
			Set<LFSegment> segments = new LinkedHashSet<LFSegment>();
			for (SegmentBean remoteSeg : lostReport.getSegments()) {
				LFSegment segment = new LFSegment();
				segment.setDestinationId(remoteSeg.getArrivalLocation());
				segment.setOriginId(remoteSeg.getDepartureLocation());
				segment.setFlightNumber(remoteSeg.getFlightNumber());
				segment.setLost(host);
				if (remoteSeg.getId() != 0) {
					segment.setId(remoteSeg.getId());
				}
				segments.add(segment);
			}
			host.setSegments(segments);
		}
		
		if(host.getShipment()!=null){
			host.getShipment().setLost(host);
			host.getShipment().setShippingOption(lostReport.getShippingOption()); //???
			host.getShipment().setTotalPayment(lostReport.getShippingPayment()); //???
			
			if(lostReport.getContact().getShippingPhone()!=null){
				LFPhone toAdd = new LFPhone();
				if (update && host.getShipment().getShippingPhone()!=null) {
					toAdd = host.getShipment().getShippingPhone();
				}
				toAdd.setPhoneType(TracingConstants.LF_PHONE_PRIMARY);
				toAdd.setNumberType(lostReport.getContact().getShippingPhone().getType());
				toAdd.setPerson(host.getClient());
				toAdd.setDecryptedPhoneNumber(lostReport.getContact().getShippingPhone().getNumber());
				toAdd.setDecryptedCountry(lostReport.getContact().getShippingPhone().getCountry());
				toAdd.setDecryptedArea(lostReport.getContact().getShippingPhone().getArea());
				toAdd.setDecryptedExchange(lostReport.getContact().getShippingPhone().getExchange());
				toAdd.setDecryptedLine(lostReport.getContact().getShippingPhone().getLine());
				toAdd.setExtension(lostReport.getContact().getShippingPhone().getExtension());
				host.getShipment().setShippingPhone(toAdd);
			}
			
			if(lostReport.getContact().getPrefshipaddress() != null){
				LFAddress address = new LFAddress();
				if (update && host.getShipment().getShippingAddress()!=null) {
					address = host.getShipment().getShippingAddress();
				}
				address.setDecryptedAddress1(lostReport.getContact().getPrefshipaddress().getAddress1());
				address.setDecryptedAddress2(lostReport.getContact().getPrefshipaddress().getAddress2());
				address.setDecryptedCity(lostReport.getContact().getPrefshipaddress().getCity());
				address.setCountry(lostReport.getContact().getPrefshipaddress().getCountry());
				address.setDecryptedProvince(lostReport.getContact().getPrefshipaddress().getProvince());
				address.setDecryptedState(lostReport.getContact().getPrefshipaddress().getState());
				address.setDecryptedZip(lostReport.getContact().getPrefshipaddress().getPostal());
				host.getShipment().setShippingAddress(address);
			}
			
			if(lostReport.getContact().getBillingaddress()!=null){
				LFAddress address = new LFAddress();
				if (host.getShipment().getBillingAddress()!=null) {
					address = host.getShipment().getBillingAddress();
				}
				address.setDecryptedAddress1(lostReport.getContact().getBillingaddress().getAddress1());
				address.setDecryptedAddress2(lostReport.getContact().getBillingaddress().getAddress2());
				address.setDecryptedCity(lostReport.getContact().getBillingaddress().getCity());
				address.setCountry(lostReport.getContact().getBillingaddress().getCountry());
				address.setDecryptedProvince(lostReport.getContact().getBillingaddress().getProvince());
				address.setDecryptedState(lostReport.getContact().getBillingaddress().getState());
				address.setDecryptedZip(lostReport.getContact().getBillingaddress().getPostal());
				host.getShipment().setBillingAddress(address);
				
			}

			host.getShipment().setDeclaredValue(lostReport.getDeclaredValue());
		}
		
		LFServiceBean bean = new LFServiceBean();
		try {
			
//			if(shipment!=null){
//				bean.saveOrUpdateShipping(shipment);
//				host.setShipment(shipment);
//			}
			long ret = bean.saveOrUpdateLostReport(host, getWebAgent(), true);
			if(ret > 0){
				try {
					TraceHandler.trace(host);
				} catch (RemoteConnectionException e) {
					//unable to connect to trace service, fail silently
				}
			}
			return ret;
		} catch (UpdateException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public ShippingBean saveOrUpdateShipping(LostReportBean lost){
		long id=saveOrUpdateLostReport(lost);

		LFServiceBean bean = new LFServiceBean();
		
		LFShipping shipment=bean.getShipment(id);
		if(shipment!=null){
			ShippingBean shipbean=new ShippingBean();
			ContactBean client=new ContactBean();
			client.setFirstName(shipment.getClient().getFirstName());
			client.setLastName(shipment.getClient().getLastName());
			client.setEmailAddress(shipment.getClient().getDecryptedEmail());
			if(shipment.getClient().getMiddleName()!=null && shipment.getClient().getMiddleName().length()>0){
				client.setMiddleInitial(shipment.getClient().getMiddleName().substring(0,1));
			}
			
			AddressBean address=new AddressBean();
			address.setAddress1(shipment.getBillingAddress().getDecryptedAddress1());
			address.setAddress2(shipment.getBillingAddress().getDecryptedAddress2());
			address.setCity(shipment.getBillingAddress().getDecryptedCity());
			address.setCountry(shipment.getBillingAddress().getCountry());
			address.setPostal(shipment.getBillingAddress().getDecryptedZip());
			if(shipment.getBillingAddress().getCountry().equals("US")){
				address.setState(shipment.getBillingAddress().getDecryptedState());
			} else {
				address.setProvince(shipment.getBillingAddress().getDecryptedProvince());
			}
			shipbean.setBillingAddress(address);
			client.setBillingaddress(address);
			address.setAddress1(shipment.getShippingAddress().getDecryptedAddress1());
			address.setAddress2(shipment.getShippingAddress().getDecryptedAddress2());
			address.setCity(shipment.getShippingAddress().getDecryptedCity());
			address.setCountry(shipment.getShippingAddress().getCountry());
			address.setPostal(shipment.getShippingAddress().getDecryptedZip());
			
			if(shipment.getShippingAddress().getCountry().equals("US")){
				address.setState(shipment.getShippingAddress().getDecryptedState());
			} else {
				address.setProvince(shipment.getShippingAddress().getDecryptedProvince());
			}
			
			shipbean.setShippingAddress(address);
			client.setPrefshipaddress(address);
			
			if(shipment.getShippingPhone()!=null){
				PhoneBean shipPhone=new PhoneBean();
				shipPhone.setNumber(shipment.getShippingPhone().getDecryptedPhoneNumber());
				shipPhone.setType(shipment.getShippingPhone().getNumberType());
				client.setPrimaryPhone(shipPhone);
			}
			shipbean.setClient(client);
			shipbean.setLost(lost);
			shipbean.setTotalPayment(shipment.getTotalPayment());
			shipbean.setShippingOption(shipment.getShippingOption());
			shipbean.setDeclaredValue(shipment.getDeclaredValue());
			return shipbean;
			
			
		}
		return null;
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
			if(station.getAssociated_airport() != null && station.getAssociated_airport().equalsIgnoreCase(TracingConstants.LF_LFC_COMPANY_ID)){
				continue;
			}
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
	
	//Begin Fedex Logic
	@Override
	public AddressBean validateAddressFedex(LostReportBean bean){
		return FedexUtils.validateAddressFedex(bean.getContact().getPrefshipaddress());
		
	}
	
	@Override
	public List<RateBean> getRatesForAddress(LostReportBean bean){
		LFServiceBean servicebean=new LFServiceBean();
		LFLost lost =servicebean.getLostReport(Long.valueOf(bean.getReportId()));
		float weight=(float) 1.0;
		if(lost.getItem().getFound()!=null && lost.getItem().getFound().getItem()!=null){
			weight=lost.getItem().getFound().getItem().getWeight();
		}
		return FedexUtils.getRates(bean.getContact().getPrefshipaddress(), bean.getDeclaredValue(),weight);
		
	}

}
