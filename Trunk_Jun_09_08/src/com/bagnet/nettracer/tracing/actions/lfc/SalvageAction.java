package com.bagnet.nettracer.tracing.actions.lfc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.lf.services.LFServiceBean;
import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.db.lf.LFSalvageFound;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.forms.lfc.SalvageForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SalvageAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(SalvageAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		LFUtils.getLists(user, session);

		request.setAttribute("salvage", "1");
		LFServiceBean serviceBean = new LFServiceBean();
		SalvageForm sForm = (SalvageForm) form;
		LFSalvage salvage = sForm.getSalvage();
		List<LFSalvageFound> salvageItems=new ArrayList<LFSalvageFound>();
		if (request.getParameter("createNew") != null) {
			salvage = createSalvage(user);
		} else if (request.getParameter("id") != null) {
			long id = -1;
			try {
				id = Long.valueOf((String) request.getParameter("id"));
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
			}
			
			if (id != -1) {
				salvage = serviceBean.loadSalvage(id);
				salvageItems = serviceBean.loadSalvageFound(id);
			} else {
				ActionMessage error = new ActionMessage("error.invalid.salvage.id");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			
			if (salvage == null) {
				salvage = createSalvage(user);
				ActionMessage error = new ActionMessage("error.unable.to.load.salvage");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
		}

		// generate the receipt
		if (request.getParameter("receipt") != null) {
			String reportfile = null;
			StatReportDTO srDTO = new StatReportDTO();
			ReportBMO rBMO = new ReportBMO(request);
			long id = Long.valueOf((String) request.getParameter("id"));
			srDTO.setStarttime("02/29/2012");
			srDTO.setEndtime("02/29/2012");
			srDTO.setSalvageId(id);
			
			String report = (String) request.getParameter("reportnum");
			String reportnum = (String) request.getParameter("customreportnum");
			if (report != null && Integer.valueOf(report) == ReportingConstants.RPT_20
					&& reportnum!= null && Integer.valueOf(reportnum) == ReportingConstants.RPT_20_CUSTOM_93) {
				srDTO.setReportnum(Integer.valueOf(report));
				srDTO.setCustomreportnum(Integer.valueOf(reportnum));
				reportfile = rBMO.createReport(getServlet().getServletContext().getRealPath("/"), srDTO, user);
				request.setAttribute("reportfile", reportfile);
			}
			response.sendRedirect("reporting?outputtype=2&reportfile=" + reportfile);
			return null;
		}
		
		request.removeAttribute("found");
		String divId = (String) request.getParameter("divId");
		request.setAttribute("divId", divId);
		String addItem = (String) request.getParameter("addItem");
		String removeItem = (String) request.getParameter("removeItem");
		String newBoxId = (String) request.getParameter("newBoxId");
		String prevBoxId = (String) request.getParameter("previousBoxId");
		String updateItem = (String) request.getParameter("boxUpdateFound");

		if (addItem != null && !addItem.isEmpty()) {
			
			String barcode = "";
			LFFound found = null;
			boolean success = true;
			try {
				barcode = (String) request.getParameter("addItem");
				found = serviceBean.getFoundItemByBarcode(barcode);
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
			}
			
			if (found == null) {
				success = false;
				request.setAttribute("message", resources.getString("error.unable.add.item") + ": " + barcode);
			} else if (found.getSalvage() != null) {
				success = false;
				if (found.getSalvage().getId() == salvage.getId()) {
					request.setAttribute("message", resources.getString("lf.salvage.item") + ": " + found.getBarcode() + " " + resources.getString("error.item.already.in.this.salvage"));
				} else {
					request.setAttribute("message", resources.getString("lf.salvage.item") + ": " + found.getBarcode() + " " + 
							resources.getString("error.item.already.salvaged") + ": " + "<a href='lf_salvage.do?id=" + 
							found.getSalvage().getId() + "' >" + found.getSalvage().getId() + "</a> ");
				}
			} else {
				Calendar salvageCutoff = Calendar.getInstance();
				salvageCutoff.setTimeInMillis(System.currentTimeMillis());

				int salvageDays;
				Subcompany subcomp=SubCompanyDAO.loadSubcompany(found.getCompanyId());
				if (found.getItem().getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) {
					if(subcomp!=null){
						salvageDays = subcomp.getSalvage_High();
					} else {
						salvageDays = PropertyBMO.getValueAsInt("lf.high.value.salvage.days");
					}
				} else {
					if(subcomp!=null){
						salvageDays = subcomp.getSalvage_Low();
					} else {
						salvageDays = PropertyBMO.getValueAsInt("lf.low.value.salvage.days");
					}
				}
				
				if(newBoxId!=null && !newBoxId.isEmpty()) {
					found.setSalvageBoxId(newBoxId);
					serviceBean.saveOrUpdateFoundItem(found, user);
					request.setAttribute("lastBoxId", newBoxId);
				}
				
				salvageCutoff.add(Calendar.DAY_OF_YEAR, (-1 * salvageDays));
				
				Calendar rxDate = Calendar.getInstance();
				rxDate.setTime(found.getReceivedDate());
				if (salvageCutoff.before(rxDate)) {
					success = false;
					request.setAttribute("message", resources.getString("lf.salvage.item") + ": " + 
							"<a href=\'create_found_item.do?barcode=" + found.getBarcode() + "\' >" + found.getBarcode() + "</a> " + 
							resources.getString("lf.salvage.was.received.on") + ": " + found.getDisReceivedDate() + " " + 
							resources.getString("lf.salvage.and.cannot.be.salvaged.before") + ": " + salvageDays + " " + 
							resources.getString("lf.salvage.days"));
				}
			}
			
			if (success) {
				request.setAttribute("found", found);
				SalvageManager.getQueue().put(new SalvageItemContainer(salvage, found, user, true));
				
			}

			return mapping.findForward(TracingConstants.AJAX_SALVAGE_ITEMS);
		} else if (removeItem != null && !removeItem.isEmpty()) {
			
			boolean success = true;
			try {
				String barcode = (String) request.getParameter("removeItem");
				LFFound found = serviceBean.getFoundItemByBarcode(barcode);

				if (found == null) {
					success = false;
				} else {
					found.setStatusId(TracingConstants.LF_STATUS_OPEN);
					int dispositionId;
					if (found.getItem().getDeliveryRejected()) {
						dispositionId = TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED;
					} else {
						dispositionId = TracingConstants.LF_DISPOSITION_OTHER;
					}
					found.getItem().setDispositionId(dispositionId);
					found.setSalvage(null);
					
					if (found.getItem().getLost() != null) {
						LFLost lost = found.getItem().getLost();
						lost.setStatusId(TracingConstants.LF_STATUS_OPEN);
						lost.getItem().setDispositionId(dispositionId);
						serviceBean.saveOrUpdateLostReport(lost, user);
					}
					
					serviceBean.saveOrUpdateFoundItem(found, user);
					salvage = serviceBean.loadSalvage(salvage.getId());
					salvageItems = serviceBean.loadSalvageFound(salvage.getId());
					request.setAttribute("lastBoxId",salvageItems.get(salvageItems.size()-1).getSalvageBoxId());
					request.setAttribute("found", found);
				}
				
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
				success = false;
			} finally {
				if (!success) {
					request.setAttribute("message", resources.getString("error.unable.remove.item") + ": " + removeItem);
				}
			}
			
			return mapping.findForward(TracingConstants.AJAX_LF_BLANK);
		} else if (updateItem != null && !updateItem.isEmpty() && prevBoxId!=null && !prevBoxId.isEmpty()) {
			
			boolean success = true;
			try {
				LFFound found = serviceBean.getFoundItemByBarcode(updateItem);

				if (found == null) {
					success = false;
				} else {
					found.setSalvageBoxId(prevBoxId);
					serviceBean.saveOrUpdateFoundItem(found, user);
					salvage = serviceBean.loadSalvage(salvage.getId());
					salvageItems = serviceBean.loadSalvageFound(salvage.getId());
					request.setAttribute("salvagefounds", salvageItems);
					request.setAttribute("lastBoxId",salvageItems.get(salvageItems.size()-1).getSalvageBoxId());
					request.setAttribute("found", found);
				}
				
			} catch (NumberFormatException nfe) {
				logger.error(nfe, nfe);
				success = false;
			} finally {
				if (!success) {
					request.setAttribute("message", resources.getString("error.unable.update.item") + ": " + updateItem);
				}
			}
			
			return mapping.findForward(TracingConstants.AJAX_LF_BLANK);
		} else if (request.getParameter("save") != null) {
			boolean validParameters = false;
			try {
				long saveID = Long.parseLong(request.getParameter("saveID"));
				int statusId = Integer.parseInt(request.getParameter("statusID"));
				if (TracingConstants.LF_STATUS_OPEN == statusId || TracingConstants.LF_STATUS_CLOSED == statusId) {
					validParameters = true;
					if (saveID == 0) { // First save needs to create new.
						salvage = createSalvage(user);
					} else {
						salvage = serviceBean.loadSalvage(saveID);
						salvage.setStatusId(statusId);
					}
					saveSalvage(serviceBean, salvage, request, errors);
					salvage = serviceBean.loadSalvage(salvage.getId());
					salvageItems = serviceBean.loadSalvageFound(salvage.getId());
				}
			} catch (NumberFormatException nfe) {
				logger.error("SaveID or StatusID is improperly formatted but passed the conditionals: " + nfe);
			}
			
			if (!validParameters) {
				ActionMessage error = new ActionMessage("error.error500.desc");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward(TracingConstants.ERROR_MAIN);
			}
		} 
		
		if (salvage.getId() == 0) {
			ActionMessage error = new ActionMessage("message.save.salvage.to.add.items");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		}
		
		sForm.setSalvage(salvage);
		request.setAttribute("salvagefounds", salvageItems);
		if(salvageItems!=null && salvageItems.size()>0){
			request.setAttribute("lastBoxId",salvageItems.get(salvageItems.size()-1).getSalvageBoxId());
		}
		return mapping.findForward(TracingConstants.LFC_SALVAGE);
	}
	
	private LFSalvage createSalvage(Agent user) {
		LFSalvage salvage = new LFSalvage();
		salvage.setCreatedDate(new Date());
		salvage.setAgent(user);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_OPEN);
		salvage.setStatus(status);
		salvage.setLocation(user.getStation());
		salvage.setItems(new LinkedHashSet<LFFound>());
		return salvage;
	}
	
	private boolean saveSalvage(LFServiceBean serviceBean, LFSalvage salvage, HttpServletRequest request, ActionMessages errors) {
		boolean success = serviceBean.saveSalvage(salvage); 
		ActionMessage message;
		if (success) {
			message = new ActionMessage("message.salvage.saved");
		} else {
			message = new ActionMessage("message.salvage.save.failed");
		}
		errors.add(ActionMessages.GLOBAL_MESSAGE, message);
		saveMessages(request, errors);
		return success;
	}
	
}