package com.bagnet.nettracer.tracing.factory;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.adapter.impl.TemplateAdapterImpl;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
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
	
	public static TemplateAdapter getTemplateAdapter(TemplateAdapterDTO dto) throws InvalidDocumentTypeException,InsufficientInformationException {
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
					instance.getIncidentInfo(dto, adapter);
					instance.getExpenseInfo(dto, adapter);
					break;
				default:
					throw new InvalidDocumentTypeException();
			}
		}
		return adapter;
	}
	
	private TemplateAdapter createAdapter(TemplateAdapterDTO dto) throws InsufficientInformationException {
		TemplateAdapter adapter = new TemplateAdapterImpl();
		getAgentInfo(dto, adapter);
		return adapter;
	}
	
	private void getAgentInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getAgent() == null) throw new InsufficientInformationException(Agent.class);
		adapter.setAgentFirstName(dto.getAgent().getFirstname());
		adapter.setAgentLastName(dto.getAgent().getLastname());
		adapter.setAgentInitials(dto.getAgent().getInitial());
		adapter.setDateFormat(dto.getAgent().getDateformat().getFormat());
	}
	

	private void getExpenseInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
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
		} else {
			adapter.setExpenseTotalAmount(TracingConstants.DECIMALFORMAT.format(0));
		}
	}
	
	private void getIncidentInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getIncident() == null) throw new InsufficientInformationException(Incident.class);
		Incident incident = dto.getIncident();
		if (incident.getIncident_ID() != null)
			adapter.setIncidentId(incident.getIncident_ID());			
		if (incident.getItemtype() != null )
			adapter.setIncidentType(incident.getItemtype().getDescription());			
		if (incident.getPassenger_list().size() != 0 ){
			Passenger p = incident.getPassenger_list().get(0);
			if (p.getFirstname() != null)
				adapter.setIncidentFirstName(p.getFirstname());				
			if (p.getLastname() != null)
				adapter.setIncidentLastName(p.getLastname());				
			if (p.getAddress(0) != null){
				Address a = p.getAddress(0);
				if (a.getAddress1() != null)
					adapter.setIncidentAddress1(a.getAddress1());				
				if (a.getAddress2() != null)
					adapter.setIncidentAddress2(a.getAddress2());				
				if (a.getCity() != null)
					adapter.setIncidentCity(a.getCity());				
				if (a.getState() != null)
					adapter.setIncidentState(a.getState());				
				if (a.getZip() != null)
					adapter.setIncidentZip(a.getZip());				
				if (a.getHomephone() != null)
					adapter.setIncidentHomePhone(a.getHomephone());				
				if (a.getWorkphone() != null)
					adapter.setIncidentBusinessPhone(a.getWorkphone());				
				if (a.getMobile() != null)
					adapter.setIncidentMobilePhone(a.getMobile());				
			}			
		}
	}
	
	private void getClaimInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getClaim() == null) throw new InsufficientInformationException(FsClaim.class);
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
		adapter.setClaimState(a.getState());
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
	
	private void getFoundItemInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getFound() == null) throw new InsufficientInformationException(LFFound.class);
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
		adapter.setFoundItemState(a.getDecryptedState());
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
