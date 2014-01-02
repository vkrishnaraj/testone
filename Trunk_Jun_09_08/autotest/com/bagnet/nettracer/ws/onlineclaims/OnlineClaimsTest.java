package com.bagnet.nettracer.ws.onlineclaims;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser;
import com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.LoadClaim;
import com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.File;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Message;
import com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger;

public class OnlineClaimsTest {

	public static OnlineClaimsServiceImplementation service;

	public static String username = "onlineclaims";
	public static String password = "B651kLN5";
	public static String companycode = "WN";

	public static String stationcode = "LZ";
	public static String altStationcode = "BDL";

	public OnlineClaimsTest() {
		service = new OnlineClaimsServiceImplementation();
	}

	@Test
	public void testAuthAdminUser(){
		AuthAdminUserDocument aaud=AuthAdminUserDocument.Factory.newInstance();
		AuthAdminUser aau=aaud.addNewAuthAdminUser();
		aau.setPassword("SLtop1731!");
		aau.setUsername("ntadmin");
		NtAuth na=aau.addNewAuth();
		na.setPassword(password);
		na.setUsername(username);
		AuthAdminUserResponseDocument aaurd=service.authAdminUser(aaud);
		assertTrue(aaurd.getAuthAdminUserResponse().getReturn());
	}
	
	@Test
	public void testSaveClaim(){

    	SaveClaimDocument scd=SaveClaimDocument.Factory.newInstance();
		SaveClaim sc=scd.addNewSaveClaim();
		
		NtAuth nta=sc.addNewAuth();
		nta.setPassword(password);
		nta.setUsername(username);
		
		Claim cl=sc.addNewClaim();
		Passenger p=cl.addNewPassenger();
		p.setLastName("LASTNAME");
		p.setFirstName("FIRSTNAME");
		
		Message m=cl.addNewMessages();
		m.setDateCreated(Calendar.getInstance());
		m.setMessage("Test Message");
		m.setPublish(true);
		m.setUsername("Test User");
		
		File f=cl.addNewFile();
		f.setFilename("TESTPATH.txt");
		f.setPath("/testDirect");
		f.setPublish(true);
		
		
		BagService bs=new BagService();

    	SearchIncidentForm form=new SearchIncidentForm();
    	GeneralServiceBean bean = new GeneralServiceBean();
		Agent auto = bean.getAgent("ntadmin", TracerProperties.get("wt.company.code"));
    	@SuppressWarnings("unchecked")
		List<Incident> list=bs.findIncident(form, auto, 0, 0, false);
    	String incId="";
    	if(list!=null && list.size()>0){
    		Incident inc=(Incident)list.get(0);
    		incId=inc.getIncident_ID();
    	
    		sc.setIncidentId(incId);
			if(inc!=null && inc.getPassenger_list()!=null && inc.getPassenger_list().size()>0){
				 com.bagnet.nettracer.tracing.db.Passenger pas=inc.getPassenger_list().get(0);
				 sc.setName(pas.getLastname());
			}
			if(inc.getOc_claim_id()>0){
				cl.setClaimId(inc.getOc_claim_id());
			}
    	}
		
		SaveClaimResponseDocument doc=service.saveClaim(scd);
		assertTrue(doc.getSaveClaimResponse().getReturn());
	}

	@Test
	public void testLoadClaim(){
		GeneralServiceBean bean = new GeneralServiceBean();
		Agent auto = bean.getAgent("ntadmin", TracerProperties.get("wt.company.code"));
		LoadClaimDocument lcd=LoadClaimDocument.Factory.newInstance();
		LoadClaim lc=lcd.addNewLoadClaim();
		
		NtAuth nta=lc.addNewAuth();
		nta.setPassword(password);
		nta.setUsername(username);
		
		lc.setFName("FIRSTNAME");
		lc.setName("LASTNAME");
		

		BagService bs=new BagService();

    	SearchIncidentForm form=new SearchIncidentForm();
    	@SuppressWarnings("unchecked")
		List<Incident> list=bs.findIncident(form, auto, 0, 0, false);
    	Incident inc=null;
    	String incId="";
    	if(list!=null && list.size()>0){
    		inc=(Incident) list.get(0);
    		incId=inc.getIncident_ID();
    		lc.setIncidentId(incId);

			if(inc!=null && inc.getPassenger_list()!=null && inc.getPassenger_list().size()>0){
				 com.bagnet.nettracer.tracing.db.Passenger pas=inc.getPassenger_list().get(0);
	    		lc.setFName(pas.getFirstname());
	    		lc.setName(pas.getLastname());
			}
    	}
		
		LoadClaimResponseDocument doc=service.loadClaim(lcd);
		System.out.println(doc.toString());

		assertTrue(doc.getLoadClaimResponse().getReturn().getClaimId()>0);
	}
	
	
}