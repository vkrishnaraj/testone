package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Label;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.enums.LabelType;
import com.bagnet.nettracer.tracing.service.label.LabelService;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.DateUtils;


/**
 * Implementation of <strong>Action</strong> that is responsible for generating
 * a list of all queued labels that are printable.
 */
public class LabelAction extends Action {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private LabelService labelService = (LabelService) SpringUtils.getBean(TracingConstants.LABEL_SERVICE_BEAN);
	
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

		//ajax call to Generate Label from an Incident.
		String incidentId = StringUtils.trimToNull(request.getParameter("incidentId"));
		if (incidentId != null) {
			short labelsSaved = 0;
			List<Passenger> passengers = null;
			Incident incident = (Incident) session.getAttribute("incidentObj");
			if (incident !=null && incidentId.equalsIgnoreCase(incident.getIncident_ID())
					&& (passengers = incident.getPassenger_list()) != null && !passengers.isEmpty()) {
				String date = DateUtils.formatDate(new Date(), "MM/dd/yy", null, null);
				for (Passenger passenger : passengers) {
					String text = String.format("%s, %s\n%s %s", passenger.getLastname(), passenger.getFirstname(),//
							date, agent.getStation().getStationcode());
					
					Label label = new Label();
					label.setAgent(agent);
					label.setText(text);
					
					long labelId = labelService.save(label);
					if (0 < labelId) {
						labelsSaved++;
					}
					
					break;//first passenger only - per requirements.
				}
			}
			
			if (labelsSaved < 1) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			
			return null;
		}
		
		//menu highlight
		request.setAttribute("highlite", TracingConstants.SYSTEM_COMPONENT_NAME_LABEL_QUEUE);		
		
		String action = null;
		ActionMessages errors = null;
		String delimeterLabelIds = ",";
		if (StringUtils.equalsIgnoreCase("Add", request.getParameter(TracingConstants.COMMAND_CREATE))) {
			errors = (errors != null) ? errors : new ActionMessages();
			String text = StringUtils.stripToEmpty((String) PropertyUtils.getSimpleProperty(form, "label")).replaceAll("\r\n", "\n");
			if (text.isEmpty()) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.missingRequired"));
			} else {
				Label label = new Label();
				label.setAgent(agent);
				label.setText(text);
				
				long labelId = labelService.save(label);
				if (0 < labelId) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("label.created.success"));
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.failed.to.save"));
				}
			}
		} else if (StringUtils.equalsIgnoreCase("Save", request.getParameter(TracingConstants.COMMAND_UPDATE))) {
			errors = (errors != null) ? errors : new ActionMessages();
			long labelId = NumberUtils.toLong(request.getParameter("label_id"));
			String text = StringUtils.stripToEmpty((String) PropertyUtils.getSimpleProperty(form, "label")).replaceAll("\r\n", "\n");
			if (text.isEmpty()) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.missingRequired"));
				saveMessages(request, errors);
				
				request.setAttribute("label", labelService.load(labelId));		
				return mapping.findForward(TracingConstants.LABEL_LIST);
			} else {
				Label label = new Label();
				label.setId(labelId);
				label.setText(text);
				
				boolean success = labelService.update(label);
				if (success) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("label.edit.success"));
				} else {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalid.label.id"));
				}
			}
		} else if (StringUtils.equalsIgnoreCase("Yes", request.getParameter("confirm_print"))) {
			short totalDeleted = 0;
			String[] checkedLabelIds = StringUtils.stripToEmpty(request.getParameter("printedLabelIds")).split(delimeterLabelIds);
			for (short i=0; i < checkedLabelIds.length; i++) {
				boolean success = labelService.delete(agent.getAgent_ID(), NumberUtils.toInt(checkedLabelIds[i]));				
				if (success) {
					totalDeleted++;
				}
			}
			
			if (checkedLabelIds.length != totalDeleted) {
				log.error("Label(s) not deleted: agentId = {}, labelIds = {} and total deleted = " + totalDeleted, agent.getAgent_ID(), request.getParameter("printedLabelIds"));							
			}
			
			if (totalDeleted < 1) {
				errors = (errors != null) ? errors : new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("label.delete.purge"));
			}
		} else if ((action = StringUtils.stripToNull(request.getParameter("action"))) != null) {			
			boolean success = false;
			long labelId = NumberUtils.toLong(request.getParameter("label_id"));
			errors = (errors != null) ? errors : new ActionMessages();
			if (StringUtils.equals(action, TracingConstants.COMMAND_EDIT)) {
				if (!StringUtils.equalsIgnoreCase("Save", request.getParameter(TracingConstants.COMMAND_UPDATE))) {
					Label label = labelService.load(labelId);
					if (label == null) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalid.label.id"));
						saveMessages(request, errors);
					}

					request.setAttribute("label", label);		
					return mapping.findForward(TracingConstants.LABEL_LIST);
				}
			} else if (StringUtils.equals(action, TracingConstants.COMMAND_DELETE)) {
				success = labelService.delete(agent.getAgent_ID(), labelId);
				if (success) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("label.delete.success"));
				}
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalid.action"));
			}

			if (!success && errors.isEmpty()) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.invalid.label.id"));
			}
		}
		
		List<Label> labelList = labelService.getLabels(agent.getAgent_ID());
		if (labelList == null || labelList.isEmpty()) {
			int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));

			Integer currpage = NumberUtils.toInt(request.getParameter("currpage"));
			request.setAttribute("currpage", currpage.toString());

			if (errors == null) {
				errors = new ActionMessages();
				ActionMessage error = new ActionMessage("error.label.queue");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			}
			
			saveMessages(request, errors);
			
			return mapping.findForward(TracingConstants.LABEL_LIST);
		} 
		
		if (StringUtils.equalsIgnoreCase(TracingConstants.COMMAND_PRINT, request.getParameter(TracingConstants.COMMAND_PRINT))) {
			int startingPosition = NumberUtils.toInt(request.getParameter("starting_position"));
			if (startingPosition < 1 || TracingConstants.LABELS_PER_PAGE < startingPosition) {
				errors = (errors != null) ? errors : new ActionMessages();				
				ActionMessage error = new ActionMessage("errors.range", (0 < startingPosition) ? startingPosition : request.getParameter("starting_position"), 1, TracingConstants.LABELS_PER_PAGE);
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			} else {
				String[] checkedLabelIds = request.getParameterValues("label_id");
				if (checkedLabelIds == null || checkedLabelIds.length < 1) {
					errors = (errors != null) ? errors : new ActionMessages();
					ActionMessage error = new ActionMessage("error.validation.missingLabel");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				} else {					
					long[] labelIds = new long[checkedLabelIds.length]; 
					for (short i = 0; i < labelIds.length; i++) {
						labelIds[i] = NumberUtils.toLong(checkedLabelIds[i]);
					}
					
					Arrays.sort(labelIds);
					
					List<Label> labelsToPrint = null; //comma delimited label ids
					StringBuilder printedLabelIds = null;
					for (Label label : labelList) {
						int index = Arrays.binarySearch(labelIds, label.getId());
						if (index < 0) {
							continue;
						}
						
						if (labelsToPrint != null) {
							printedLabelIds.append(delimeterLabelIds);
						} else {
							labelsToPrint = new ArrayList<Label>();
							printedLabelIds = new StringBuilder();
						}
						
						printedLabelIds.append(label.getId());
						labelsToPrint.add(label);
						if (labelsToPrint.size() == labelIds.length) {
							break;
						}
					}					
	
					//only 5660 series implemented at this time
					String labelName = (labelsToPrint == null) ? null : LabelType._5660.getName();
					String reportfile = (labelsToPrint == null) ? null : StringUtils.stripToNull(printReport(labelsToPrint, request, labelName, startingPosition));
					if(reportfile != null) {
						request.setAttribute("reportfile", reportfile);
						request.setAttribute("printedLabelIds", printedLabelIds);
						
						return mapping.findForward(TracingConstants.LABEL_LIST);
					}
					
					errors = (errors != null) ? errors : new ActionMessages();
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
			}
		}
		
		// get row count
		Integer rowcount = labelList.size();
		request.setAttribute("rowcount", rowcount.toString());
		
		Integer rowsperpage = TracerUtils.manageRowsPerPage(rowcount.toString(), TracingConstants.ROWS_SEARCH_PAGES, session);
		request.setAttribute("rowsperpage", rowsperpage.toString());

		Integer currpage = NumberUtils.toInt(request.getParameter("currpage"));
		if (StringUtils.equals("1", request.getParameter("nextpage"))) {
			currpage++;
		}
		if (StringUtils.equals("1", request.getParameter("prevpage"))) {
			currpage--;
		}
		request.setAttribute("currpage", currpage.toString());
		
		// find out total pages
		int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);
		if (totalpages <= currpage) {
			currpage = 0;
			request.setAttribute("currpage", "0");
		}

		//find the paginated on hand bags
		if (currpage + 1 == totalpages) {
			request.setAttribute("end", "1");
		}
		
		if (totalpages > 1) {
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < totalpages; i++) {
				al.add(Integer.toString(i));
			}
			request.setAttribute("pages", al);
		}
		/** ************ end of pagination ************* */
		
		if (errors != null && !errors.isEmpty()) {
			saveMessages(request, errors);
		}

		request.setAttribute("labellist", labelList);
		
		return mapping.findForward(TracingConstants.LABEL_LIST);
	}
	
	private String printReport(List<Label> labelsToPrint, HttpServletRequest request, String labelName, int startingPosition) {
		try {
			if (labelsToPrint == null || labelsToPrint.isEmpty()) {
				throw new IllegalArgumentException("No labels to be printed");
			}

			if (startingPosition < 1 || TracingConstants.LABELS_PER_PAGE < startingPosition) {
				throw new IllegalArgumentException(String.format("%s is not in the range %s through %s", startingPosition, 1, TracingConstants.LABELS_PER_PAGE));
			}
			
			LabelType labelType = LabelType.fromName(labelName);			
			if (labelType.getTemplate() == LabelType.UNSUPPORTED.getTemplate()) {
				throw new IllegalArgumentException(String.format("'%s' label is not supported", labelName));
			}
			
			//current support PDF, with value = 0 
			int outputtype = NumberUtils.toInt(request.getParameter("outputtype"));
			request.setAttribute("outputtype", String.valueOf(outputtype));
			
			//move to the starting positing.
			List<Label> printableLabels = new ArrayList<Label>();
			for (short i = 1; i < startingPosition && startingPosition <= TracingConstants.LABELS_PER_PAGE; i++) {
				printableLabels.add(null);
			}
			
			printableLabels.addAll(labelsToPrint);
			
			ReportBMO reportBMO = new ReportBMO(request);	
			Map<String, Object> parameters = new HashMap<String, Object>();
			String rootpath = getServlet().getServletContext().getRealPath("/");
			String reportname = String.format("avery_label_%d", labelType.getTemplate());
			return reportBMO.getReportFile(printableLabels, parameters, reportname, rootpath, outputtype);
		} catch (Exception e) {
			log.error("Failed to print label - id = {} and starting position = " + startingPosition, labelName, e);
			return null;
		}
	}
}
