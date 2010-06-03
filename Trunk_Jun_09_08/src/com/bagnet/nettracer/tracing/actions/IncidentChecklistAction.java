/*
 * Created on Oct 20, 2009
 * 
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentChecklistBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AuditIncidentChecklist;
import com.bagnet.nettracer.tracing.db.ChecklistTask;
import com.bagnet.nettracer.tracing.db.ChecklistTaskOption;
import com.bagnet.nettracer.tracing.db.ChecklistVersion;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.IncidentChecklist;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.forms.IncidentChecklistForm;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action </strong> that is responsible for managing
 * all incident checklist. 
 * 
 * @author Ian
 */
public class IncidentChecklistAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		//if (!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)) return (mapping
		//		.findForward(TracingConstants.NO_PERMISSION));

		Station agent_station = null;
		if (session.getAttribute("cbroStationID") != null) {
			agent_station = StationBMO.getStation((String) session.getAttribute("cbroStationID"));
		} else {
			agent_station = user.getStation();
		}

		ActionMessages errors = new ActionMessages();
		IncidentChecklistForm theForm = (IncidentChecklistForm) form;
		
		String myIncidentId = request.getParameter("incident_id");
		request.setAttribute("currentIncidentId", myIncidentId);
		
		
		
		//old jsp tricks
		//ChecklistVersion myReady4ViewChecklistVersion = getChecklistForIncident(myIncidentId);
		ChecklistVersion myReady4ViewChecklistVersion = doSaveOrUndoAndReady4View(myIncidentId, user, theForm);
		request.setAttribute("myReady4ViewChecklistVersion", myReady4ViewChecklistVersion);
		
		//test out the struts tags
		List<ChecklistTask> ready4ViewListOfTasks = myReady4ViewChecklistVersion.getChecklistTasks();
		request.setAttribute("ready4ViewListOfTasks", ready4ViewListOfTasks);
		
    	String incident_id =  (String) theForm.getIncident_id();
    	
    	//placeholder for the selected option of each dropdown
    	List<String> selectedOptions = new ArrayList<String>();
    	for (int i=0; i<3; ++i) {
    		selectedOptions.add(null);
    	}
    	theForm.setSelectedOptions(selectedOptions);
    	
//    	List<String> mySelectedOptions = theForm.getSelectedOptions();

		
		return mapping.findForward(TracingConstants.INCIDENT_CHECKLIST);
	}
	
	
	
	//main method - to package the Incident Checklist related data for view
	private ChecklistVersion getChecklistForIncident(String incidentId) {
		ChecklistVersion result = null;
		
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			
			//(1) get incident checklist version from this incident
			Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
			long myIncidentChecklistVersion_id = incident.getChecklist_version();
			
			//(2) load default the object graph for this version
			ChecklistVersion defaultChecklistVersion = 
				IncidentChecklistBMO.getChecklistVersionById(myIncidentChecklistVersion_id, sess);
			
			//(3) get incident_checklist for this incident - this tells what has been done so far
			List<IncidentChecklist> checklistCurrentStatus = 
				IncidentChecklistBMO.getIncidentChecklistByIncidentId(incident.getIncident_ID(), sess);
			
			//(4) use what's in (3) to modify (2) - result is to be pushed to view
			result = updateIncidentChecklistVersion(defaultChecklistVersion, checklistCurrentStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	//new main method II - 
	private ChecklistVersion doSaveOrUndoAndReady4View(String incidentId, Agent agent, IncidentChecklistForm theForm) {
		ChecklistVersion result = null;
		
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			
			//(1) and (2)
			Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
			ChecklistVersion defaultChecklistVersion = getDefaultChecklistVersion(incidentId, sess);
			
			if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST, agent)) {
				//do save or update here - if requested
				String myAction = theForm.getAction();
				if(myAction != null && myAction.equalsIgnoreCase("save")) {
					List<String> selected = theForm.getSelectedOptions();
					for (String sel: selected) {
						if (sel != null && sel.length() > 0) {
							long longSel = Long.parseLong(sel);
							IncidentChecklist myIncidentChecklist = new IncidentChecklist();
							AuditIncidentChecklist myAuditIncidentChecklist = new AuditIncidentChecklist();
							myAuditIncidentChecklist.setDescription("marked saved");
							
							myIncidentChecklist.setIncident(incident);
							myAuditIncidentChecklist.setIncident(incident);
							
							myIncidentChecklist.setAgent(agent);
							myAuditIncidentChecklist.setAgent(agent);
							
							Date myDay = TracerDateTime.getGMTDate();
							myIncidentChecklist.setTimestamp(myDay);
							myAuditIncidentChecklist.setTimestamp(myDay);
							// GET THE OPTION USING SEL AS ID
							List<ChecklistTask> myDefaultListOfTasks = defaultChecklistVersion.getChecklistTasks();
							for(ChecklistTask myTask : myDefaultListOfTasks) {
								List<ChecklistTaskOption> myListOfOptions = myTask.getChecklistTaskOptions();
								for(ChecklistTaskOption myOption : myListOfOptions) {
									String myOptionId = "" + myOption.getId();
									if(myOptionId.equals(sel)) {
										myIncidentChecklist.setChecklistTaskOption(myOption);
										myIncidentChecklist.setChecklistTask(myTask);
										myAuditIncidentChecklist.setChecklistTaskOption(myOption);
										myAuditIncidentChecklist.setChecklistTask(myTask);
									}
								}
							}
							
							// TODO: Copy Properties
							//BeanUtils.copyProperties(dest, orig)
							
							// NEED TO CREATE INCIDENT_CHECKLIST ENTRY
							// SAVE INCIDENT_CHECKLIST
							HibernateUtils.save(myIncidentChecklist, sess);
							// SAVE AUDIT_INCIDENT_CHECKLIST
							HibernateUtils.save(myAuditIncidentChecklist, sess);
						}
					}
					
				} else if(myAction != null && myAction.equalsIgnoreCase("undo")) {
					// GET INCIDENT_CHECKLIST
					String myUndoReference = theForm.getUndoReference();
					if(myUndoReference != null && myUndoReference.length()>0) {
						long incidentChecklistId = Long.parseLong(myUndoReference);
						Object goneIncidentChecklist = sess.load(IncidentChecklist.class, incidentChecklistId);
						// SAVE AUDIT_INCIDENT_CHECKLIST (THAT SAYS IT WAS DELETED)
						IncidentChecklist myGoneIncidentChecklist = (IncidentChecklist) goneIncidentChecklist;
						AuditIncidentChecklist myAuditIncidentChecklist = new AuditIncidentChecklist();
						myAuditIncidentChecklist.setDescription("marked undone and deleted");
						myAuditIncidentChecklist.setIncident(myGoneIncidentChecklist.getIncident());
						myAuditIncidentChecklist.setAgent(myGoneIncidentChecklist.getAgent());
						myAuditIncidentChecklist.setChecklistTaskOption(myGoneIncidentChecklist.getChecklistTaskOption());
						myAuditIncidentChecklist.setChecklistTask(myGoneIncidentChecklist.getChecklistTask());
						Date myDay = TracerDateTime.getGMTDate();
						myAuditIncidentChecklist.setTimestamp(myDay);
						
						HibernateUtils.save(myAuditIncidentChecklist, sess);
						
						// DELETE INCIDENT_CHECKLIST
						HibernateUtils.delete(goneIncidentChecklist, sess);
					}
				}
				
			}
			theForm.setAction("");
			
			//(3) get incident_checklist for this incident - this tells what has been done so far
			List<IncidentChecklist> checklistCurrentStatus = getChecklistCurrentStatus(incidentId, sess);
			
			//(4) use what's in (3) to modify (2) - result is to be pushed to view
			result = updateIncidentChecklistVersion(defaultChecklistVersion, checklistCurrentStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	//new main method II - step (1) and (2) - 
	private ChecklistVersion getDefaultChecklistVersion(String incidentId, Session sess) {
		ChecklistVersion result = null;
		
			
		//(1) get incident checklist version from this incident
		Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
		long myIncidentChecklistVersion_id = incident.getChecklist_version();
			
		//(2) load default the object graph for this version
		ChecklistVersion defaultChecklistVersion = 
			IncidentChecklistBMO.getChecklistVersionById(myIncidentChecklistVersion_id, sess);
			
		result = defaultChecklistVersion;

		
		return result;
	}
	
	//new main method II - step (3) - 
	private List<IncidentChecklist> getChecklistCurrentStatus(String incidentId, Session sess) {
		List<IncidentChecklist> result = null;
		
			
		//(3) get incident_checklist for this incident - this tells what has been done so far
		List<IncidentChecklist> checklistCurrentStatus = 
			IncidentChecklistBMO.getIncidentChecklistByIncidentId(incidentId, sess);
			
			result = checklistCurrentStatus;
		
		return result;
	}
	
	//(4) use what's in List<IncidentChecklist> to modify default ChecklistVersion
	private ChecklistVersion updateIncidentChecklistVersion(ChecklistVersion defaultChecklistVersion, 
															List<IncidentChecklist> checklistCurrentStatus) {
		ChecklistVersion result = null;
		//loop through all checklistTasks for this version and use ChecklistTask.snapshotData 
		//transient field to hold the current status information for each task -  
		List<ChecklistTask> myDefaultListOfTasks = defaultChecklistVersion.getChecklistTasks();
		if(myDefaultListOfTasks != null) {
			Iterator<ChecklistTask> it=myDefaultListOfTasks.iterator();
			while(it.hasNext()){
				ChecklistTask myTask = (ChecklistTask)it.next();
				//loop through the checklistCurrentStatus for any match
				if(checklistCurrentStatus != null) {
					Iterator<IncidentChecklist> itCurrentStatus = checklistCurrentStatus.iterator();
					while(itCurrentStatus.hasNext()) {
						IncidentChecklist currentStatus = (IncidentChecklist) itCurrentStatus.next();
						
						if( myTask.getId() == currentStatus.getChecklistTask().getId()) {
							myTask.setSnapshotData(currentStatus);
						}
					}
				}
			}
		}
		//defaultChecklistVersion.setChecklistTasks(myDefaultListOfTasks);
		result = defaultChecklistVersion;
		
		return result;
	}
	

}