package com.bagnet.nettracer.tracing.actions.communications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * Implementation of <strong>Action</strong> that is responsible for generating
 * a list of all approved documents that are pending print.
 */

public class DocumentPrintCommunicationsAction extends Action {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private Status status = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_PRINT);
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent agent = (Agent) session.getAttribute("user");
		if (agent == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasLinkPermission(String.format("%s.do", mapping.getPath().substring(1)), agent)) {
			return mapping.findForward(TracingConstants.NO_PERMISSION);
		}
		
		//ajax call to preview the generated document
		String generateFile = StringUtils.trimToNull(request.getParameter("generateFile"));
		if (generateFile != null) {
			return previewFile(mapping, agent, generateFile, response);
		}
		
		//menu highlight
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_DOCUMENT_PRINT_QUEUE);

		//the displaytag sort logic
		String sortBy=StringUtils.stripToNull(request.getParameter((new ParamEncoder("incidentActivity")).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		if(sortBy==null) {
			sortBy = StringUtils.stripToNull(request.getParameter("sortBy"));
		}		
		if (!SortParam.isValid(sortBy)) {
			sortBy = null;
		}
		
		request.setAttribute("sortBy", sortBy);
		
		ActionMessages errors = null;
		String delimeterTaskIds = ",";
		if (StringUtils.equalsIgnoreCase("Yes", request.getParameter("confirm_print"))) {
			short totalPublished = 0;
			String[] checkedTaskIds = StringUtils.stripToEmpty(request.getParameter("printedTaskIds")).split(delimeterTaskIds);
			for (short i=0; i < checkedTaskIds.length; i++) {				
				IncidentActivityTask task = incidentActivityService.loadTask(NumberUtils.toLong(checkedTaskIds[i]));				
				boolean success = incidentActivityService.publishTask(task);
				if (success) {
					totalPublished++;
				}
			}
			
			if (checkedTaskIds.length != totalPublished) {
				log.error("IncidentActivity(s) not published: agentId = {}, taskIds = {} and total published = " + totalPublished, agent.getAgent_ID(), request.getParameter("printedTaskIds"));							
			}
			
			if (totalPublished < 1) {
				errors = (errors != null) ? errors : new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("document.print.published.fail"));
			}
		}
			
		List<IncidentActivity> incidentActivities = incidentActivityService.getIncidentActivitiesByTaskStatus(status, sortBy);
		
		//remove inActive incident activity tasks
		List<IncidentActivity> incidentActivitylist = null;
		if (incidentActivities != null && !incidentActivities.isEmpty()) {
			incidentActivitylist = new ArrayList<IncidentActivity>();
			for (IncidentActivity incidentActivity : incidentActivities) {
				List<IncidentActivityTask> tasksList = incidentActivity.getTasks();
				if (tasksList == null || tasksList.isEmpty()) {
					continue;
				}
	
				List<IncidentActivityTask> activeTasks = new ArrayList<IncidentActivityTask>(); 
				for (IncidentActivityTask incidentActivityTask : tasksList) {
					if (incidentActivityTask.isActive()) {
						activeTasks.add(incidentActivityTask);
					}
					
				}
				
				if (!activeTasks.isEmpty()) {
					incidentActivity.setTasks(activeTasks);
					incidentActivitylist.add(incidentActivity);
				}
			}
		}
		
		if (incidentActivitylist == null || incidentActivitylist.isEmpty()) {
			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.document.print.queue");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			}
			
			saveMessages(request, errors);
			
			log.debug("No approved document in the queue.");
			
			return mapping.findForward(TracingConstants.DOCUMENT_PRINT_COMMUNICATIONS);
		}

		if (StringUtils.containsIgnoreCase(request.getParameter(TracingConstants.COMMAND_PRINT), TracingConstants.COMMAND_PRINT)) {
			DocumentTemplateResult result = null;
			ResourceBundle resourceBundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(agent.getCurrentlocale()));
			boolean isPreviewPrint = StringUtils.equalsIgnoreCase(request.getParameter(TracingConstants.COMMAND_PRINT), resourceBundle.getString("document.print.preview"));
			String[] checkedTaskIds = request.getParameterValues("incident_activity_task_id");
			if (checkedTaskIds == null || checkedTaskIds.length < 1) {
				if (!isPreviewPrint) {
					errors = (errors != null) ? errors : new ActionMessages();
					ActionMessage error = new ActionMessage("error.validation.missing.document");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
			} else {					
				long[] taskIds = new long[checkedTaskIds.length]; 
				for (short i = 0; i < taskIds.length; i++) {
					taskIds[i] = NumberUtils.toLong(checkedTaskIds[i]);
				}
				
				Arrays.sort(taskIds);
				
				List<Document> documents = null; 
				StringBuilder printedTaskIds = null; //comma delimited incidentActivity ids
				for (IncidentActivity incidentActivity : incidentActivitylist) {
					List<IncidentActivityTask> tasks = incidentActivity.getTasks();
					if (tasks == null || tasks.isEmpty()) {
						continue;
					}
					
					IncidentActivityTask task = tasks.get(tasks.size() - 1);
					int index = (task == null) ? -1 : Arrays.binarySearch(taskIds, task.getTask_id());
					if (index < 0) {
						continue;
					}
					
					if (documents != null) {
						printedTaskIds.append(delimeterTaskIds);
					} else {
						documents = new ArrayList<Document>();
						printedTaskIds = new StringBuilder();
					}
					
					printedTaskIds.append(task.getTask_id());
					documents.add(incidentActivity.getDocument());
					if (documents.size() == taskIds.length) {
						break;
					}
				}

				result =  generatePdf(agent, documents, request, response);
				if (!isPreviewPrint && result != null && result.isSuccess()) {					
					request.setAttribute("printedTaskIds", printedTaskIds);
				}
			}
			
			if (isPreviewPrint) {				
				return previewFile(mapping, agent, (result == null) ? null :(String) result.getPayload(), response);
			}
			
			return mapping.findForward(TracingConstants.DOCUMENT_PRINT_COMMUNICATIONS);
		}		

		if (errors != null && !errors.isEmpty()) {
			saveMessages(request, errors);
		}
				
		request.setAttribute("incidentActivitylist", incidentActivitylist);
		
		return mapping.findForward(TracingConstants.DOCUMENT_PRINT_COMMUNICATIONS);
	}
	
	private ActionForward previewFile(ActionMapping mapping, Agent agent, String generateFile, HttpServletResponse response) {
		DocumentTemplateResult result = null;
		if (generateFile != null && !generateFile.isEmpty()) {
			DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
			result = documentService.previewFile(agent, generateFile, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP), response);
		}
		
		return (result != null && result.isSuccess()) ? null : mapping.findForward(TracingConstants.FILE_NOT_FOUND);
	}
	
	private DocumentTemplateResult generatePdf(Agent agent, List<Document> documents, HttpServletRequest request, HttpServletResponse response) {
		try {
			ActionMessage error = null;
			DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
			DocumentTemplateResult result = documentService.generatePdf(agent, documents, PropertyBMO.getValue(PropertyBMO.DOCUMENT_LOCATION_TEMP));
			if (documents == null || documents.isEmpty()) {
				error = new ActionMessage("message.nodata");
			} else {
				if(result == null) {
					error = new ActionMessage("message.nodata");
				} else if (!result.isSuccess()) {
						error = new ActionMessage(result.getMessageKey());
				} else {
					request.setAttribute("generateFile", result.getPayload());
				}
			}
			
			if (error != null) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);

				saveMessages(request, errors);
			}
			
			return result;
		} catch (Exception e) {
			log.error("Failed to print document", e);
			return null;
		}
	}
}
