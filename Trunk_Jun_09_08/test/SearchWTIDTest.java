

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import aero.nettracer.general.services.GeneralServiceBean;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;


public class SearchWTIDTest {
    
	@SuppressWarnings({ "static-access", "rawtypes" })
    @Test
    public void searchWTIdTestInc() {
    	GeneralServiceBean bean = new GeneralServiceBean();
		Agent auto = bean.getAgent("ntadmin", TracerProperties.get("wt.company.code"));

    	SearchIncidentForm form=new SearchIncidentForm();
    	Calendar c=new GregorianCalendar();
    	Date now=c.getTime();
    	c.add(Calendar.DATE, -3);
    	Date earlier=c.getTime();

    	String earlierDate=DateUtils.formatDate(earlier, auto.getDateformat().getFormat(), null, null);
    	form.setS_createtime(earlierDate);
    	String todayDate=DateUtils.formatDate(now, auto.getDateformat().getFormat(), null, null);
    	form.setE_createtime(todayDate);

    	BagService bs=new BagService();
    	List list=bs.findIncident(form, auto, 0, 0, false);
    	Incident inc=null;
    	String incId="";
    	if(list!=null && list.size()>0){
    		inc=(Incident) list.get(0);
    		incId=inc.getIncident_ID();
    	}
    	WorldTracerFile wtf=new WorldTracerFile();
    	wtf.setWt_id("YYCWS0001");
    	inc.setWtFile(wtf);
    	try {
    		IncidentBMO ibmo=new IncidentBMO();
			ibmo.insertIncident(false, inc, null, auto);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StaleStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	form=new SearchIncidentForm();
    	form.setWt_id("YYCWS%");
    	list=bs.findIncident(form, auto, 0, 0, false);

		boolean hasIncId=false;
    	if(list.size()>0){
    		for(int i=0;i<list.size();i++){
    			Incident loopInc=(Incident)list.get(i);
    			if(incId.equals(loopInc.getIncident_ID())){
    				hasIncId=true;
    				break;
    	    		
    			}
    		}
    	}
    	if(hasIncId){
    		System.out.println("World Tracer search for Incidents returned result");
    	}
    	else {
    		System.out.println("World Tracer search for Incidents failed to returned result");
    	}
    	

    	list=bs.customQuery(form, auto, 0, 0, false, "1");
    	 hasIncId=false;
     	if(list.size()>0){
     		for(int i=0;i<list.size();i++){
     			Incident loopInc=(Incident)list.get(i);
     			if(incId.equals(loopInc.getIncident_ID())){
     				hasIncId=true;
     				break;
     	    		
     			}
     		}
     	}
     	if(hasIncId){
     		System.out.println("World Tracer search for Incidents returned result");
     	}
     	else {
     		System.out.println("World Tracer search for Incidents failed to returned result");
     	}
    }
    
	@SuppressWarnings("static-access")
    @Test
    public void searchWTIdTestOhd() {
    	GeneralServiceBean bean = new GeneralServiceBean();
		Agent auto = bean.getAgent("ntadmin", TracerProperties.get("wt.company.code"));

    	SearchIncidentForm form=new SearchIncidentForm();
    	Calendar c=new GregorianCalendar();
    	Date now=c.getTime();
    	c.add(Calendar.DATE, -3);
    	Date earlier=c.getTime();

    	String earlierDate=DateUtils.formatDate(earlier, auto.getDateformat().getFormat(), null, null);
    	form.setS_createtime(earlierDate);
    	String todayDate=DateUtils.formatDate(now, auto.getDateformat().getFormat(), null, null);
    	form.setE_createtime(todayDate);

    	BagService bs=new BagService();
    	List list=bs.findOnHandBagsBySearchCriteria(form, auto, 0, 0, false,false);
    	OHD ohd=null;
    	String ohdId="";
    	if(list!=null && list.size()>0){
    		ohd=(OHD) list.get(0);
    		ohdId=ohd.getOHD_ID();
    	}
    	WorldTracerFile wtf=new WorldTracerFile();
    	wtf.setWt_id("YYCWS0001");
    	ohd.setWtFile(wtf);
    	try {
    		OhdBMO obmo=new OhdBMO();
			obmo.insertOHD(ohd, auto);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	form=new SearchIncidentForm();
    	form.setWt_id("YYCWS%");
    	list=bs.findOnHandBagsBySearchCriteria(form, auto, 0, 0, false,false);

		boolean hasOhdId=false;
    	if(list.size()>0){
    		for(int i=0;i<list.size();i++){
    			OHD loopOhd=(OHD)list.get(i);
    			if(ohdId.equals(loopOhd.getOHD_ID())){
    				hasOhdId=true;
    				break;
    	    		
    			}
    		}
    	}
    	if(hasOhdId){
    		System.out.println("World Tracer search for OHD returned result");
    	}
    	else {
    		System.out.println("World Tracer search for Ohds failed to returned result");
    	}
    	list=bs.customQuery(form, auto, 0, 0, false, "5");
    	hasOhdId=false;
    	if(list.size()>0){
    		for(int i=0;i<list.size();i++){
    			OHD loopOhd=(OHD)list.get(i);
    			if(ohdId.equals(loopOhd.getOHD_ID())){
    				hasOhdId=true;
    				break;
    	    		
    			}
    		}
    	}
    	if(hasOhdId){
    		System.out.println("World Tracer search for OHD returned result");
    	}
    	else {
    		System.out.println("World Tracer search for OHD failed to returned result");
    	}
    	
    }
    
    
 }