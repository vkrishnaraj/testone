package com.bagnet.nettracer.tracing.factory;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.adapter.impl.TemplateAdapterImpl;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.exceptions.InvalidDocumentTypeException;

/**
 * The TemplateAdapterFactory is a singleton class which creates TemplateAdapter objects for the user 
 * based on the information contained in the TemplateAdapterDTOs submitted by the user. This class 
 * should be the sole source of TemplateAdapter creation throughout the system.
 * @author Mike
 *
 */
public class TemplateAdapterFactory {
	
	private static TemplateAdapterFactory instance = new TemplateAdapterFactory();
	
	private TemplateAdapterFactory() { }
	
	public static DocumentTemplateResult hasRequiredInfo(TemplateAdapterDTO dto) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		boolean success = true;
		for (TemplateType type: dto.getTypes()) {
			if (type == TemplateType.INCIDENT && dto.getIncident() == null) {
				result.addMissingInfo(TracingConstants.getDisplayNameFromClass(Incident.class));
				success = false;
			} else if (type == TemplateType.CLAIM && dto.getClaim() == null) {
				result.addMissingInfo(TracingConstants.getDisplayNameFromClass(FsClaim.class));
				success = false;
			} else if (type == TemplateType.FOUND_ITEM && dto.getFound() == null) {
				result.addMissingInfo(TracingConstants.getDisplayNameFromClass(LFFound.class));
				success = false;
			} else if (type == TemplateType.CLAIM_SETTLEMENT && dto.getExpensePayout() == null) {
				result.addMissingInfo(TracingConstants.getDisplayNameFromClass(ExpensePayout.class));
				success = false;
			} else {
				continue;
			}
		}
		
		result.setSuccess(success);
		return result;
	}
	
	public static TemplateAdapter getTemplateAdapter(TemplateAdapterDTO dto) throws InvalidDocumentTypeException {
		TemplateAdapter adapter = instance.createAdapter(dto);
		for (TemplateType type: dto.getTypes()) {
			switch(type) {
				case STATIC:
				case INVALID:
					break;
				case INCIDENT:
					instance.getIncidentInfo(dto, adapter);
					break;
				case CLAIM:
					instance.getClaimInfo(dto, adapter);
					break;
				case FOUND_ITEM:
					instance.getFoundItemInfo(dto, adapter);
					break;
				case CLAIM_SETTLEMENT:
					instance.getExpenseInfo(dto, adapter);
					break;
				default:
					throw new InvalidDocumentTypeException();
			}
		}
		return adapter;
	}
	
	private TemplateAdapter createAdapter(TemplateAdapterDTO dto) {
		TemplateAdapter adapter = new TemplateAdapterImpl();
		getAgentInfo(dto, adapter);
		return adapter;
	}
	
	private void getAgentInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		if (dto.getAgent() != null) {
			adapter.setAgentFirstName(dto.getAgent().getFirstname());
			adapter.setAgentLastName(dto.getAgent().getLastname());
			adapter.setAgentInitials(dto.getAgent().getInitial());
			adapter.setDateFormat(ReportingConstants.WRITTEN_DATE_FORMAT);
		}
	}
	

	private void getExpenseInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		if(dto.getExpensePayout()!=null){
			double total=0;
			if(dto.getExpensePayout().getCheckamt()>0){
				total+=dto.getExpensePayout().getCheckamt();
			}
			if(dto.getExpensePayout().getVoucheramt()>0){
				total+=dto.getExpensePayout().getVoucheramt();
			}
			if(dto.getExpensePayout().getCreditCardRefund()>0){
				total+=dto.getExpensePayout().getCreditCardRefund();
			}
			adapter.setExpenseTotalAmount(TracingConstants.DECIMALFORMAT.format(total));
		}
	}
	
	private void getIncidentInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		if (dto.getIncident() == null) return;
		Incident incident = dto.getIncident();
			adapter.setIncidentId(incident.getIncident_ID());			
		if (incident.getItemtype() != null )
			adapter.setIncidentType(incident.getItemtype().getDescription());			
		if (incident.getPassenger_list().size() != 0 ){
			Passenger p = incident.getPassenger_list().get(0);
				adapter.setIncidentFirstName(p.getFirstname());				
				adapter.setIncidentLastName(p.getLastname());		
				adapter.setIncidentSalutation(p.getSalutationdesc());
			if (p.getAddress(0) != null){
				Address a = p.getAddress(0);
				adapter.setIncidentAddress1(a.getAddress1());				
				adapter.setIncidentAddress2(a.getAddress2());				
				adapter.setIncidentCity(a.getCity());				
				adapter.setIncidentCountry(a.getCountry());
				if (!a.getCountrycode_ID().equals("US")){
					adapter.setIncidentState(a.getProvince());
				} else if (a.getState() != null){ 
					adapter.setIncidentState(a.getState());
				}
				adapter.setIncidentProvince(a.getProvince());
				adapter.setIncidentZip(a.getZip());				
				adapter.setIncidentHomePhone(a.getHomephone());				
				adapter.setIncidentBusinessPhone(a.getWorkphone());				
				adapter.setIncidentMobilePhone(a.getMobile());				
			}			
		}
	}
	
	private void getClaimInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		if (dto.getClaim() == null) return;
		
		FsClaim claim = dto.getClaim();
		
		adapter.setClaimId(String.valueOf(claim.getId()));
		adapter.setClaimType(ClaimDAO.getClaimTypeDescription(claim.getClaimType()));
		
		Person p = claim.getClaimant();
		adapter.setClaimFirstName(p.getFirstName());
		adapter.setClaimLastName(p.getLastName());
		
		FsAddress a = p.getAddress();
		adapter.setClaimAddress1(a.getAddress1());
		adapter.setClaimAddress2(a.getAddress2());
		adapter.setClaimCity(a.getCity());
		if(!a.getCountry().equals("US")){
			adapter.setClaimState(a.getProvince());
		} else {
			adapter.setClaimState(a.getState());
		}

		adapter.setClaimProvince(a.getProvince());
		adapter.setClaimCountry(a.getCountryName());
		adapter.setClaimZip(a.getZip());
		
		for (Phone phone: p.getPhones()) {
			switch(phone.getType()) {
				case Phone.HOME:
					adapter.setClaimHomePhone(phone.getPhoneNumber());
					break;
				case Phone.WORK:
					adapter.setClaimBusinessPhone(phone.getPhoneNumber());
					break;
				case Phone.MOBILE:
					adapter.setClaimMobilePhone(phone.getPhoneNumber());
					break;
				default:
					continue;
			}
		}
		
	}
	
	private void getFoundItemInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		if (dto.getFound() == null) return;
		LFFound found = dto.getFound();
		adapter.setFoundItemId(String.valueOf(found.getId()));
		adapter.setFoundItemItem(found.getItem().getDescription());
		adapter.setFoundItemDescription(found.getItem().getLongDescription());
		adapter.setFoundItemColor(found.getItem().getColor());
		adapter.setFoundItemCaseColor(found.getItem().getCaseColor());

		LFPerson p = found.getClient();
		adapter.setFoundItemFirstName(p.getFirstName());
		adapter.setFoundItemLastName(p.getLastName());
		
		LFAddress a = p.getAddress();
		adapter.setFoundItemAddress1(a.getDecryptedAddress1());
		adapter.setFoundItemAddress2(a.getDecryptedAddress2());
		adapter.setFoundItemCity(a.getDecryptedCity());
		if(a.getCountry()!=null){
			adapter.setFoundItemCountry(a.getCountryName());
			if(!a.getCountry().equals("US")){
				adapter.setFoundItemState(a.getDecryptedProvince());
			} else {
				adapter.setFoundItemState(a.getDecryptedState());
			}
		}
		adapter.setFoundItemProvince(a.getDecryptedProvince());
		adapter.setFoundItemZip(a.getDecryptedZip());
		
		for (LFPhone phone: p.getPhones()) {
			switch(phone.getNumberType()) {
				case LFPhone.HOME:
					adapter.setFoundItemHomePhone(phone.toString());
					break;
				case LFPhone.WORK:
					adapter.setFoundItemBusinessPhone(phone.toString());
					break;
				case LFPhone.MOBILE:
					adapter.setFoundItemMobilePhone(phone.toString());
					break;
				default:
					continue;
			}
		}
	
	}
	
}
