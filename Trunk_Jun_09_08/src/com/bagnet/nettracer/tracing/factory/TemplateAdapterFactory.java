package com.bagnet.nettracer.tracing.factory;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.adapter.impl.TemplateAdapterImpl;
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
	
	private void getIncidentInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getIncident() == null) throw new InsufficientInformationException(Incident.class);
		adapter.setIncidentId(dto.getIncident().getIncident_ID());
		adapter.setIncidentType(dto.getIncident().getItemtype().getDescription());
		getIncidentPassengerInfo(dto, adapter);
		getIncidentAddressInfo(dto, adapter);
	}
	
	private void getClaimInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getClaim() == null) throw new InsufficientInformationException(FsClaim.class);
		adapter.setClaimId(String.valueOf(dto.getClaim().getId()));
		adapter.setClaimType(ClaimDAO.getClaimTypeDescription(dto.getClaim().getClaimType()));
		getClaimPassengerInfo(dto, adapter);
		getClaimAddressInfo(dto, adapter);
	}
	
	private void getIncidentPassengerInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		Passenger passenger = dto.getIncident().getPassenger_list().get(0);
		adapter.setPassengerFirstName(passenger.getFirstname());
		adapter.setPassengerLastName(passenger.getLastname());
	}
	
	private void getClaimPassengerInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		Person person = dto.getClaim().getClaimant();
		adapter.setPassengerFirstName(person.getFirstName());
		adapter.setPassengerLastName(person.getLastName());
	}

	private void getIncidentAddressInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		Address address = dto.getIncident().getPassenger_list().get(0).getAddress(0);
		adapter.setAddressAddress1(address.getAddress1());
		adapter.setAddressAddress2(address.getAddress2());
		adapter.setAddressCity(address.getCity());
		adapter.setAddressState(address.getState());
		adapter.setAddressZip(address.getZip());
	}
	
	private void getClaimAddressInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		FsAddress address = dto.getClaim().getClaimant().getAddress();
		adapter.setAddressAddress1(address.getAddress1());
		adapter.setAddressAddress2(address.getAddress2());
		adapter.setAddressCity(address.getCity());
		adapter.setAddressState(address.getState());
		adapter.setAddressZip(address.getZip());		
	}	
	
	private void getFoundItemInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) throws InsufficientInformationException {
		if (dto.getFound() == null) throw new InsufficientInformationException(LFFound.class);
		LFFound found = dto.getFound();
		adapter.setFoundItemId(String.valueOf(found.getId()));
		adapter.setFoundItemItem(found.getItem().getDescription());
		adapter.setFoundItemDescription(found.getItem().getLongDescription());
		adapter.setFoundItemColor(found.getItem().getColor());
		adapter.setFoundItemCaseColor(found.getItem().getCaseColor());
		getFoundItemPassengerInfo(dto, adapter);
		getFoundItemAddressInfo(dto, adapter);
	}
	
	private void getFoundItemPassengerInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		LFPerson person = dto.getFound().getClient();
		adapter.setPassengerFirstName(person.getFirstName());
		adapter.setPassengerLastName(person.getLastName());
		
		LFPhone[] phones = person.getPhones() != null ? person.getPhones().toArray(new LFPhone[person.getPhones().size()]) : null;
		if (phones != null && phones.length > 0) {
			adapter.setPassengerPhoneNumber(phones[phones.length-1].toString());
		}
	}
	
	private void getFoundItemAddressInfo(TemplateAdapterDTO dto, TemplateAdapter adapter) {
		LFAddress address = dto.getFound().getClient().getAddress();
		adapter.setAddressAddress1(address.getDecryptedAddress1());
		adapter.setAddressAddress2(address.getDecryptedAddress2());
		adapter.setAddressCity(address.getDecryptedCity());
		adapter.setAddressState(address.getDecryptedState());
		adapter.setAddressZip(address.getDecryptedZip());
	}
	
}
