package com.bagnet.nettracer.tracing.utils;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Billing;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.history.HistoryContainer;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class HistoryUtils {

	public static void AddToHistoryContainer(HttpSession session, String Desc, String ObjectID, String LinkURL, String ObjType, boolean loaded) {
		FoundHistoryObject FHO=new FoundHistoryObject();
		FHO.setDate(Calendar.getInstance().getTime()); 
		FHO.setStatusDesc(Desc);
		FHO.setObjectType(ObjType);
		FHO.setObjectID(ObjectID);
		FHO.setLinkURL(LinkURL);
		HistoryContainer HCL=(HistoryContainer)session.getAttribute("historyContainer");
		
		if(loaded){
			if(HCL.getQueue().isEmpty() || (HCL.get(FHO.getObjectID())==null))
			{	HCL.put(ObjectID, FHO);
				session.setAttribute("historyContainer", HCL);
			}
		}
		else
		{	
			if(FHO.getStatusDesc().contains("Created") || FHO.getStatusDesc().contains("Added"))
			{
				HCL.put(ObjectID+"C"+HCL.getQueue().size(), FHO);
			}
			else if(FHO.getStatusDesc().contains("Saved") || FHO.getStatusDesc().contains("Updated") )
			{
				HCL.put(ObjectID+"U"+HCL.getQueue().size(), FHO);
			}
			else if(FHO.getStatusDesc().contains("Closed"))
			{
				HCL.put(ObjectID+"Cl"+HCL.getQueue().size(), FHO);
			}
			else
			{
				HCL.put(ObjectID+HCL.getQueue().size(), FHO);
			}
			session.setAttribute("historyContainer", HCL);
		}
	}
	public static void AddToHistoryContainer(HttpSession session, String Desc, String ObjectID, String LinkURL, String ObjType, boolean loaded, String actualId) {
		FoundHistoryObject FHO=new FoundHistoryObject();
		FHO.setDate(Calendar.getInstance().getTime()); 
		FHO.setStatusDesc(Desc);
		FHO.setObjectID(ObjectID);
		FHO.setObjectType(ObjType);
		FHO.setActualID(actualId);
		FHO.setLinkURL(LinkURL);
		HistoryContainer HCL=(HistoryContainer)session.getAttribute("historyContainer");
		
		if(loaded){
			if(HCL.getQueue().isEmpty() || (HCL.get(FHO.getObjectID())==null))
			{	HCL.put(ObjectID+HCL.getQueue().size(), FHO);
				session.setAttribute("historyContainer", HCL);
			}
		}
		else
		{
			if(FHO.getStatusDesc().contains("Created") || FHO.getStatusDesc().contains("Added"))
			{
				HCL.put(ObjectID+"C"+HCL.getQueue().size(), FHO);
			}
			else if(FHO.getStatusDesc().contains("Saved") || FHO.getStatusDesc().contains("Updated") )
			{
				HCL.put(ObjectID+"U"+HCL.getQueue().size(), FHO);
			}
			else if(FHO.getStatusDesc().contains("Closed"))
			{
				HCL.put(ObjectID+"Cl"+HCL.getQueue().size(), FHO);
			}
			else
			{
				HCL.put(ObjectID+HCL.getQueue().size(), FHO);
			}
			session.setAttribute("historyContainer", HCL);
		}
	}

}