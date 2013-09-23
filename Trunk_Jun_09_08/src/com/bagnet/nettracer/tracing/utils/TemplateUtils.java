package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
import com.bagnet.nettracer.tracing.forms.templates.TemplateEditForm;
import com.bagnet.nettracer.tracing.forms.templates.TemplateSearchForm;
/**
 * This class is used to provide basic tasks related to Document Template management 
 * (ie: converting between DocumentTemplate and DocmentTemplateForm, etc...).
 * 
 * @author Mike
 *
 */
public class TemplateUtils {
	
	public static void toForm(Template template, TemplateEditForm form) {
		if (template != null) {
			BeanUtils.copyProperties(template, form);
			form.setCommand(TracingConstants.COMMAND_UPDATE);
		} else {
			Date now = DateUtils.convertToGMTDate(new Date());
			form.setCreateDate(now);
			form.setLastUpdated(now);
			form.setId(0);
			form.setActive(false);
			form.setName("");
			form.setDescription("");
			form.setData("");
			form.setCommand(TracingConstants.COMMAND_CREATE);
		}
	}
	
	public static Template fromForm(TemplateEditForm form) {
		Template template = new Template();
		template.setId(form.getId());
		template.setActive(form.isActive());
		template.setName(form.getName());
		template.setDescription(form.getDescription());
		template.setData(form.getData());
		return template;
	}
	
	public static TemplateSearchDTO fromForm(TemplateSearchForm dtsf) {
		TemplateSearchDTO dto = new TemplateSearchDTO();
		dto.setId(dtsf.getId());
		dto.setName(dtsf.getName());
		dto.setDescription(dtsf.getDescription());
		dto.setActive(dtsf.getActive());
		
		if (dtsf.getS_createtime() != null && !dtsf.getS_createtime().isEmpty()) {
			dto.setStartCreateDate(DateUtils.convertToGMTDate(dtsf.getS_createtime(), dtsf.get_DATEFORMAT()));
		}
		
		if (dtsf.getE_createtime() != null && !dtsf.getE_createtime().isEmpty()) {
			dto.setEndCreateDate(DateUtils.convertToGMTDate(dtsf.getE_createtime(), dtsf.get_DATEFORMAT()));
		}
		
		return dto;
	}
	
	public static void resetSearchForm(TemplateSearchForm form) {
		form.setId(0);
		form.setActive(TracingConstants.ACTIVE_SEARCH_BOTH);
		form.setName(null);
		form.setS_createtime(null);
		form.setE_createtime(null);
		form.setCommand(null);
	}
	
	public static TemplateAdapterDTO getTemplateAdapterDTO(Agent user, Template template) {
		TemplateAdapterDTO dto = new TemplateAdapterDTO();
		dto.setAgent(user);
		
		List<TemplateType> types = new ArrayList<TemplateType>();
		for (TemplateTypeMapping mapping: template.getTypes()) {
			types.add(TemplateType.fromOrdinal(mapping.getOrdinal()));
		}
		
		dto.setTypes(types);
		return dto;
	}
	
	public static TemplateAdapterDTO getDummyAdapterDTO(Agent user) {
		
		// dummy incident
		Incident incident = new Incident();
		StringBuilder incidentId = new StringBuilder();
		incidentId.append(user.getCompanycode_ID());
		incidentId.append(user.getStation().getStationcode());
		if (incidentId.length() == 5) {
			incidentId.append("01234567");
		} else {
			incidentId.append("012345678");
		}		
		
		ItemType itemType = new ItemType();
		itemType.setItemType_ID(1);
		
		incident.setIncident_ID(incidentId.toString());
		incident.setItemtype(itemType);
		
		Address incidentAddress = new Address();
		incidentAddress.setAddress1("2675 Paces Ferry Rd.");
		incidentAddress.setAddress2("Suite 240");
		incidentAddress.setCity("Atlanta");
		incidentAddress.setState_ID("GA");
		incidentAddress.setZip("30339");
		Set<Address> incidentAddresses = new LinkedHashSet<Address>();
		incidentAddresses.add(incidentAddress);
		
		Passenger passenger = new Passenger();
		passenger.setFirstname("John");
		passenger.setLastname("Doe");
		passenger.setSalutation(2);
		passenger.setAddresses(incidentAddresses);
		Set<Passenger> incidentPassengers = new LinkedHashSet<Passenger>();
		incidentPassengers.add(passenger);
		
		incident.setPassengers(incidentPassengers);
		
		// dummy claim
		FsAddress claimAddress = new FsAddress();
		claimAddress.setAddress1("2675 Paces Ferry Rd.");
		claimAddress.setAddress2("Suite 240");
		claimAddress.setCity("Atlanta");
		claimAddress.setState("GA");
		claimAddress.setZip("30339");
		Set<FsAddress> claimAddresses = new LinkedHashSet<FsAddress>();
		claimAddresses.add(claimAddress);
		
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setAddresses(claimAddresses);
		Set<Person> claimPassengers = new LinkedHashSet<Person>();
		claimPassengers.add(person);
		
		FsClaim claim = new FsClaim();
		claim.setId(100l);
		claim.setClaimType(1);
		claim.setClaimants(claimPassengers);
		
		
		// dummy found item
		LFAddress lfAddress = new LFAddress();
		lfAddress.setDecryptedAddress1("2675 Paces Ferry Rd.");
		lfAddress.setDecryptedAddress2("Suite 240");
		lfAddress.setDecryptedCity("Atlanta");
		lfAddress.setDecryptedState("GA");
		lfAddress.setDecryptedZip("30339");
		
		LFPhone lfPhone = new LFPhone();
		lfPhone.setDecryptedCountry("1");
		lfPhone.setDecryptedArea("404");
		lfPhone.setDecryptedExchange("555");
		lfPhone.setDecryptedLine("1234");
		Set<LFPhone> phones = new LinkedHashSet<LFPhone>();
		phones.add(lfPhone);
		
		LFPerson lfPerson = new LFPerson();
		lfPerson.setFirstName("Jane");
		lfPerson.setLastName("Doe");
		
		lfPerson.setAddress(lfAddress);
		lfPerson.setPhones(phones);
		
		LFItem item = new LFItem();
		item.setId(42l);
		item.setSubCategory(46);
		item.setColor("Black");
		item.setDescription("Found Item");
		item.setLongDescription("This is an item that we found");
		item.setCaseColor("Blue");
				
		LFFound found = new LFFound();
		found.setItem(item);
		found.setClient(lfPerson);

		List<TemplateType> types = new ArrayList<TemplateType>();
		types.add(TemplateType.INCIDENT);
		types.add(TemplateType.CLAIM);
		types.add(TemplateType.FOUND_ITEM);
		
		TemplateAdapterDTO dto = new TemplateAdapterDTO();
		dto.setTypes(types);
		dto.setAgent(user);
		dto.setIncident(incident);
		dto.setClaim(claim);
		dto.setFound(found);
		
		return dto;
	}
	
}
