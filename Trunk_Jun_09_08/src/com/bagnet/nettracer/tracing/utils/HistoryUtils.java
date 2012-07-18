package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Billing;
import com.bagnet.nettracer.tracing.forms.QuickSearchForm;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryContainer;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class HistoryUtils {

	public static void AddToHistoryContainer(HttpSession session, HistoryObject HO, String actualId) {
		HistoryContainer HCL=(HistoryContainer)session.getAttribute("historyContainer");
		
		if(HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_LOAD))
		{
			if(HCL.getQueue().isEmpty() || (HCL.get(HO.getObjectID())==null))
			{	
				HCL.put(HO.getObjectID(), HO);
			}
		}
		else if(HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_CREATE) || HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_ADD))
		{
			HCL.put(HO.getObjectID()+"C"+HCL.getQueue().size(), HO);
		}
		else if(HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_SAVE) || HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_UPDATE) )
		{
			HCL.put(HO.getObjectID()+"U"+HCL.getQueue().size(), HO);
		}
		else if(HO.getStatusDesc().contains(TracingConstants.HIST_DESCRIPTION_CLOSE))
		{
			HCL.put(HO.getObjectID()+"Cl"+HCL.getQueue().size(), HO);
		}
		else
		{
			HCL.put(HO.getObjectID()+HCL.getQueue().size(), HO);
		}
	}
	
	public static void getHistoryContainer(QuickSearchForm theForm,HttpSession session)
	{
		HistoryContainer hcl =(HistoryContainer)session.getAttribute("historyContainer");
		ArrayList<HistoryObject> Hlist=hcl.getRevNewestItems(10);
				
		theForm.setHistCon(Hlist);
	}
}