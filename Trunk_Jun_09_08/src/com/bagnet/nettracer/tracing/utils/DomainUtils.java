package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
//import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.documents.templates.TemplateTypeMapping;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
//import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
//import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
//import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
//import com.bagnet.nettracer.tracing.dto.FileDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityRemarkDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
//import com.bagnet.nettracer.tracing.dto.MessageDTO;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.dto.TemplateSearchDTO;
import com.bagnet.nettracer.tracing.enums.TemplateType;
//import com.bagnet.nettracer.tracing.forms.communications.CorrespondenceForm;
import com.bagnet.nettracer.tracing.forms.communications.CustomerCommunicationsForm;
import com.bagnet.nettracer.tracing.forms.communications.CustomerCommunicationsTaskForm;
import com.bagnet.nettracer.tracing.forms.disbursements.DisbursementRejectionForm;
import com.bagnet.nettracer.tracing.forms.disbursements.FraudReviewForm;
import com.bagnet.nettracer.tracing.forms.disbursements.PaymentApprovalForm;
import com.bagnet.nettracer.tracing.forms.disbursements.SupervisorReviewForm;
import com.bagnet.nettracer.tracing.forms.templates.TemplateEditForm;
import com.bagnet.nettracer.tracing.forms.templates.TemplateSearchForm;
/**
 * This class is used to provide basic tasks related to Document Template management 
 * (ie: converting between DocumentTemplate and DocmentTemplateForm, etc...).
 * 
 * @author Mike
 *
 */
public class DomainUtils {
	
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
			form.setTypeAvailableFor(TemplateType.INCIDENT.ordinal());
			form.setCommand(TracingConstants.COMMAND_CREATE);
		}
	}
	
	public static void toForm(Document document, CustomerCommunicationsForm ccf) {
		ccf.setDocumentId(document.getId());
		ccf.setDocumentTitle(document.getTemplate().getName());
		ccf.setData(document.getContent());
	}
	
	public static void toForm(IncidentActivity ia, CustomerCommunicationsForm ccf, Agent user) {
		ccf.setCommand(TracingConstants.COMMAND_UPDATE);
		ccf.setId(ia.getId());

		if (ia.getIncident() != null) {
			ccf.setIncidentId(ia.getIncident().getIncident_ID());
			ccf.setCustCommId(ia.getCustCommId());
		}

		if (ia.getDocument() != null) {
			ccf.setDocumentId(ia.getDocument().getId());
			ccf.setDocumentTitle(ia.getDocument().getTitle());
			ccf.setData(ia.getDocument().getContent());
			ccf.setTemplateId(ia.getDocument().getTemplate().getId());
		}
		
		if (ia.getRemarks() != null && !ia.getRemarks().isEmpty()) {
			ccf.setRemarks(fromRemarks(ia.getRemarks(), user));
		}
		
		if (ia.getExpensePayout() != null) {
			ccf.setExpenseId(ia.getExpensePayout().getExpensepayout_ID());
		}
	}
	
//	Unrelated to NT-740
//	public static void toForm(IncidentActivity ia, CorrespondenceForm cf, Agent user) {
//		cf.setCommand(TracingConstants.COMMAND_UPDATE);
//		cf.setId(ia.getId());
//		OnlineClaim c=null;
//		if (ia.getIncident() != null) {
//			cf.setIncidentId(ia.getIncident().getIncident_ID());
//			OnlineClaimsDao dao = new OnlineClaimsDao();
//			c = dao.getOnlineClaim(ia.getIncident().getIncident_ID());
//		}
//		
//		if(c!=null){
//			cf.setClaimId(c.getClaimId());
//			if (c.getMessages() != null && !c.getMessages().isEmpty()) {
//				cf.setMessages(fromMessages(c.getMessages(), user));
//			}
//
//			if (c.getFile() != null && !c.getFile().isEmpty()) {
//				cf.setFiles(fromFiles(c.getFile(), user));
//			}
//		}
//		
//	}
	
	public static Template fromForm(TemplateEditForm form) {
		Template template = new Template();
		template.setId(form.getId());
		template.setActive(form.isActive());
		template.setName(form.getName());
		template.setDescription(form.getDescription());
		template.setData(form.getData());
		template.setTypeAvailableFor(form.getTypeAvailableFor());
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
	
	public static IncidentActivityTaskSearchDTO fromForm(CustomerCommunicationsTaskForm cctf) {
		IncidentActivityTaskSearchDTO dto = new IncidentActivityTaskSearchDTO();
		dto.setActive(cctf.isActive());
		return dto;
	}

	public static IncidentActivityTaskSearchDTO fromForm(DisbursementRejectionForm dtf) {
		IncidentActivityTaskSearchDTO dto = new IncidentActivityTaskSearchDTO();
		dto.setActive(dtf.isActive());
		return dto;
	}

	public static IncidentActivityTaskSearchDTO fromForm(PaymentApprovalForm paf) {
		IncidentActivityTaskSearchDTO dto = new IncidentActivityTaskSearchDTO();
		dto.setActive(paf.isActive());
		return dto;
	}

	public static IncidentActivityTaskSearchDTO fromForm(SupervisorReviewForm srf) {
		IncidentActivityTaskSearchDTO dto = new IncidentActivityTaskSearchDTO();
		dto.setActive(srf.isActive());
		return dto;
	}

	public static IncidentActivityTaskSearchDTO fromForm(FraudReviewForm frf) {
		IncidentActivityTaskSearchDTO dto = new IncidentActivityTaskSearchDTO();
		dto.setActive(frf.isActive());
		return dto;
	}
	
	public static IncidentActivity fromForm(CustomerCommunicationsForm ccf, Agent user) {
		IncidentActivity ia = new IncidentActivity();
		
		Incident incident = new Incident();
		incident.setIncident_ID(ccf.getIncidentId());
		
		Template template = new Template();
		template.setId(ccf.getTemplateId());
		ExpensePayout ep=null;
		if(ccf.getExpenseId()>0){
			ep=new ExpensePayout();
			ep.setExpensepayout_ID(ccf.getExpenseId());
		}
		
		Document document = new Document();
		document.setId(ccf.getDocumentId());
		document.setTitle(ccf.getDocumentTitle());
		document.setContent(ccf.getData());
		document.setFileName(ccf.getFileName());
		document.setTemplate(template);
		
		ia.setId(ccf.getId());
		ia.setCustCommId(ccf.getCustCommId());
		ia.setIncident(incident);
		ia.setDocument(document);
		if(ep!=null){
			ia.setExpensePayout(ep);
			ia.setActivity(new Activity(TracingConstants.CREATE_SETTLEMENT_ACTIVITY));
		} else {
			ia.setActivity(new Activity(TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION));
		}
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		ia.setDescription(bundle.getString("customer.communication.outgoing") + ": " + ia.getDocument().getTitle());
		
		return ia;
	}
	
	public static void resetSearchForm(TemplateSearchForm form) {
		form.setId(0);
		form.setActive(TracingConstants.ACTIVE_SEARCH_BOTH);
		form.setName(null);
		form.setS_createtime(null);
		form.setE_createtime(null);
		form.setCommand(null);
	}
	
	public static void resetSearchForm(CustomerCommunicationsTaskForm form) {
		form.setS_createtime(null);
		form.setE_createtime(null);
		form.setCommand(null);
	}
	
	public static IncidentActivity createIncidentActivity(Incident incident, Activity activity, Agent user) {
		IncidentActivity ia = new IncidentActivity();
		ia.setIncident(incident);
		ia.setActivity(activity);
		ia.setAgent(user);
		ia.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		ia.setDescription(activity.getDescription());
		return ia;
	}
	
	public static IncidentActivityTask createIncidentActivityTask(IncidentActivity incidentActivity, Status withStatus) {
		IncidentActivityTask iat = new IncidentActivityTask();
		iat.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		iat.setIncidentActivity(incidentActivity);
		iat.setStatus(withStatus);
		iat.setActive(true);
		return iat;
	}
	
	public static IncidentActivityTaskDTO fromIncidentActivityTask(IncidentActivityTask task) {
		IncidentActivityTaskDTO dto = new IncidentActivityTaskDTO();
		dto.setAgent(task.getAssigned_agent().getUsername());
		dto.setDescription(task.getDescription());
		dto.setId(task.getTask_id());
		dto.setIncidentActivityId(task.getIncidentActivity().getId());
		dto.setIncidentId(task.getIncidentActivity().getIncident().getIncident_ID());
		return dto;
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
		
		TemplateAdapterDTO dto = new TemplateAdapterDTO();
		dto.setAgent(user);
		dto.setIncident(getDummyIncident(user));
		dto.setClaim(getDummyClaim());
		dto.setFound(getDummyFoundItem());

		List<TemplateType> types = new ArrayList<TemplateType>();
		types.add(TemplateType.INCIDENT);
		types.add(TemplateType.CLAIM);
		types.add(TemplateType.FOUND_ITEM);
		
		dto.setTypes(types);
		
		return dto;
	}
	
	private static List<IncidentActivityRemarkDTO> fromRemarks(List<IncidentActivityRemark> remarks, Agent user) {
		List<IncidentActivityRemarkDTO> dtos = new ArrayList<IncidentActivityRemarkDTO>();
		TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
		for (IncidentActivityRemark remark: remarks) {
			IncidentActivityRemarkDTO dto = new IncidentActivityRemarkDTO();
			dto.set_DATEFORMAT(user.getDateformat().getFormat());
			dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
			dto.set_TIMEZONE(timeZone);
			dto.setAgent(remark.getAgent().getUsername());
			dto.setCreateDate(remark.getCreateDate());
			dto.setRemarkText(remark.getRemarkText());
			dtos.add(dto);
		}
		return dtos;
	}
	
//	Unrelated to NT-740
//	private static List<MessageDTO> fromMessages(Set<OCMessage> messages, Agent user) {
//		List<MessageDTO> dtos = new ArrayList<MessageDTO>();
//		TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
//		for (OCMessage message: messages) {
//			MessageDTO dto = new MessageDTO();
//			dto.set_DATEFORMAT(user.getDateformat().getFormat());
//			dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
//			dto.set_TIMEZONE(timeZone);
//			dto.setUsername(message.getUsername());
//			dto.setDispCreateDate(DateUtils.formatDate(message.getDateCreated(), dto.get_DATEFORMAT(), null, timeZone));
//			dto.setIncidentId(message.getClaim().getIncident().getIncident_ID());
//			dto.setMessageText(message.getMessage());
//			dtos.add(dto);
//		}
//		return dtos;
//	}
//
//	private static List<FileDTO> fromFiles(Set<OCFile> files, Agent user) {
//		List<FileDTO> dtos = new ArrayList<FileDTO>();
//		TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
//		for (OCFile file: files) {
//			FileDTO dto = new FileDTO();
//			dto.set_DATEFORMAT(user.getDateformat().getFormat());
//			dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
//			dto.set_TIMEZONE(timeZone);
//			dto.setFilename(file.getFilename());
//			dto.setId(file.getId());
//			dto.setPath(file.getPath());
//			dtos.add(dto);
//		}
//		return dtos;
//	}
	
	private static LFFound getDummyFoundItem() {
		// dummy found item
		LFAddress lfAddress = new LFAddress();
		lfAddress.setDecryptedAddress1("2675 Paces Ferry Rd.");
		lfAddress.setDecryptedAddress2("Suite 240");
		lfAddress.setDecryptedCity("Atlanta");
		lfAddress.setDecryptedState("GA");
		lfAddress.setDecryptedZip("30339");
		
		LFPhone home = new LFPhone();
		home.setNumberType(LFPhone.HOME);
		home.setDecryptedCountry("1");
		home.setDecryptedArea("404");
		home.setDecryptedExchange("555");
		home.setDecryptedLine("1234");
		
		LFPhone business = new LFPhone();
		business.setNumberType(LFPhone.WORK);
		business.setDecryptedCountry("1");
		business.setDecryptedArea("404");
		business.setDecryptedExchange("555");
		business.setDecryptedLine("1234");
		
		LFPhone mobile = new LFPhone();
		mobile.setNumberType(LFPhone.MOBILE);
		mobile.setDecryptedCountry("1");
		mobile.setDecryptedArea("404");
		mobile.setDecryptedExchange("555");
		mobile.setDecryptedLine("1234");
		
		Set<LFPhone> phones = new LinkedHashSet<LFPhone>();
		phones.add(home);
		phones.add(business);
		phones.add(mobile);
		
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
		return found;
	}

	private static FsClaim getDummyClaim() {
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
		
		LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();
		Phone home = new Phone();
		home.setType(Phone.HOME);
		home.setPhoneNumber("404 555-1234");

		Phone business = new Phone();
		business.setType(Phone.HOME);
		business.setPhoneNumber("404 555-1234");
		
		Phone mobile = new Phone();
		mobile.setType(Phone.MOBILE);
		mobile.setPhoneNumber("404 555-1234");
		
		phones.add(home);
		phones.add(business);
		phones.add(mobile);
		
		person.setPhones(phones);
		
		Set<Person> claimPassengers = new LinkedHashSet<Person>();
		claimPassengers.add(person);
		
		FsClaim claim = new FsClaim();
		claim.setId(100l);
		claim.setClaimType(1);
		claim.setClaimants(claimPassengers);
		return claim;
	}

	private static Incident getDummyIncident(Agent user) {
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
		incidentAddress.setHomephone("404 555-1234");
		incidentAddress.setWorkphone("404 555-1234");
		incidentAddress.setMobile("404 555-1234");
		
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
		return incident;
	}
	
}
