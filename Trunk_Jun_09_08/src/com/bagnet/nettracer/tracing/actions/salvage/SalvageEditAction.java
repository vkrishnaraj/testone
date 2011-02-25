package com.bagnet.nettracer.tracing.actions.salvage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.SalvageDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.db.salvage.SalvageBox;
import com.bagnet.nettracer.tracing.db.salvage.SalvageItem;
import com.bagnet.nettracer.tracing.db.salvage.SalvageOHDReference;
import com.bagnet.nettracer.tracing.forms.salvage.SalvageEditForm;
import com.bagnet.nettracer.tracing.utils.EmailUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SalvageEditAction extends CheckedAction {

	private static Logger logger = Logger.getLogger(SalvageSearchAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");

		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		SalvageEditForm myForm = (SalvageEditForm) form;
		myForm.setDateFormat(user.getDateformat().getFormat());
		myForm.setTimeFormat(user.getTimeformat().getFormat());

		Salvage salvage = null;
		String salvageId = request.getParameter("salvageId");

		if (request.getParameter("create_new") != null) {

			// If the user is creating a new salvage from scratch, set up
			// basic new salvage object, collections, objects.
			salvage = createNewSalvage();

		} else if (salvageId != null) {

			// If the user is requesting to load an existing salvage
			int sId = Integer.parseInt(salvageId);
			if (sId > 0) {
				salvage = SalvageDAO.loadSalvage(sId);
			}

		} else {

			// Assuming the user is editing the salvage object loaded into
			// session.
			salvage = myForm.getSalvage();

			// HANDLE DELETES
			deleteAnyBoxesOrItems(myForm, request);

			// HANDLE ADDS
			addAnyBoxesOrItems(myForm, request, salvage);


			
			if (request.getParameter("save") != null) {
				// HANDLE SAVE
				SalvageDAO.saveSalvage(salvage);
			} else if (request.getParameter("completeSalvage") != null && request.getParameter("completeSalvage").equals("1")) {
				// generate salvage receipt
				// transmit to ocs
				// HANDLE COMPLETION OF SALVAGE
				ServletContext sc = getServlet().getServletContext();
				String rootPath = sc.getRealPath("/");
				String reportFile = getSalvageReport(request, user, salvage, rootPath);
				if (reportFile != null) {
					EmailUtils.emailSalvageSummary(rootPath + ReportingConstants.REPORT_TMP_PATH + reportFile, user, salvage);
				}
				completeSalvage(salvage, user);
			} else if (request.getParameter("print_report") != null) {
				// HANDLE REPORT (??? LIKELY TO MOVE ???)
				ServletContext sc = getServlet().getServletContext();
				String rootPath = sc.getRealPath("/");
				String reportFile = getSalvageReport(request, user, salvage, rootPath);
				if (reportFile != null) {
					request.setAttribute("reportfile", reportFile);
					request.setAttribute("outputtype", TracingConstants.REPORT_OUTPUT_XLS);
				}
			}

		}

		myForm.setSalvage(salvage);
		return mapping.findForward(TracingConstants.SALVAGE_EDIT);
	}

	private void completeSalvage(Salvage salvage, Agent a) {
		salvage.setStatus(1);
		
		SalvageDAO.saveCompleteSalvage(salvage, a);
	}

	private Salvage createNewSalvage() {
		Salvage salvage;
		salvage = new Salvage();
		LinkedHashSet<SalvageBox> salvageBoxes = new LinkedHashSet<SalvageBox>();
		
		// Create default box
		SalvageBox box = new SalvageBox();
		box.setSalvage(salvage);
		salvageBoxes.add(box);
		
		LinkedHashSet<SalvageItem> salvageItems = new LinkedHashSet<SalvageItem>();
		box.setSalvageItems(salvageItems);
		
		SalvageItem item = new SalvageItem();
		item.setBox(box);
		salvageItems.add(item);
		
		// Create first low-value box
		SalvageBox box2 = new SalvageBox();
		box2.setSalvage(salvage);
		box2.setDisplayId(1);
		box2.setType(SalvageBox.TYPE_LOW_VALUE);
		salvageBoxes.add(box2);
		
		LinkedHashSet<SalvageItem> salvageItems2 = new LinkedHashSet<SalvageItem>();
		box2.setSalvageItems(salvageItems2);
		
		SalvageItem item2 = new SalvageItem();
		item2.setBox(box2);
		salvageItems2.add(item2);
		
		
		LinkedHashSet<SalvageOHDReference> ohdReferences = new LinkedHashSet<SalvageOHDReference>();
		salvage.setSalvageBoxes(salvageBoxes);
		salvage.setOhdReferences(ohdReferences);
		

		return salvage;
	}

	/**
	 * Method is responsible for processing the incoming request and deleting
	 * any boxes or items from the form and salvage object.
	 * 
	 * @param theform
	 * @param request
	 * @return
	 */
	public void deleteAnyBoxesOrItems(SalvageEditForm theform, HttpServletRequest request) {

		if (request.getParameter("delete_these_elements") != null && request.getParameter("delete_these_elements").length() > 0) {
			String deleteTheseElements = request.getParameter("delete_these_elements");
			String[] elements = deleteTheseElements.split(",");

			HashMap<String, ArrayList<Integer>> elementBreakdown = new HashMap();
			HashMap<Integer, ArrayList<Integer>> itemBreakdown = new HashMap();

			// Iterate through elements and sort into types
			for (String element : elements) {
				String[] str = element.split("_");

				// SalvageItems are different because
				// 1. They are nested objects
				// 2. They must be dealt with prior to boxes.
				if (str[0].equals(TracingConstants.JSP_DELETE_SALVAGE_ITEM)) {
					addToIntegerMap(itemBreakdown, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
				} else {
					addToStringMap(elementBreakdown, str[0], str[1]);
				}
			}

			// Delete any items from boxes first (in reverse order)
			for (Integer boxIndex : itemBreakdown.keySet()) {
				ArrayList<Integer> list = itemBreakdown.get(boxIndex);
				Collections.sort(list, Collections.reverseOrder());

				for (int itemIndex : list) {
					SalvageBox box = theform.getSalvageBox(boxIndex);
					SalvageItem itemToRemove = box.getSalvageItem(itemIndex);
					box.getSalvageItems().remove(itemToRemove);
				}
			}

			// Sort elements in the list in descending integer order,
			// then iterate through them to perform action.
			for (String key : elementBreakdown.keySet()) {
				ArrayList<Integer> list = elementBreakdown.get(key);
				Collections.sort(list, Collections.reverseOrder());

				for (int index : list) {

					if (key.equals(TracingConstants.JSP_DELETE_SALVAGE_BOX)) {
						SalvageBox box = theform.getSalvageBox(index);
						theform.getSalvage().getSalvageBoxes().remove(box);
					}
					
					if (key.equals(TracingConstants.JSP_DELETE_SALVAGE_OHD)) {
						SalvageOHDReference ref = theform.getOhdReference(index);
						theform.getSalvage().getOhdReferences().remove(ref);
					}
				}
			}
		}

	}

	public static void addToIntegerMap(HashMap<Integer, ArrayList<Integer>> typeBreakdown, Integer key, Integer value) {
		ArrayList<Integer> internalList = null;

		if (typeBreakdown.containsKey(key)) {
			internalList = typeBreakdown.get(key);
			internalList.add(value);
		} else {
			internalList = new ArrayList();
			internalList.add(value);
			typeBreakdown.put(key, internalList);
		}
	}

	public static void addToStringMap(HashMap<String, ArrayList<Integer>> typeBreakdown, String key, String integer) {
		ArrayList<Integer> internalList = null;

		Integer value = Integer.parseInt(integer);

		if (typeBreakdown.containsKey(key)) {
			internalList = typeBreakdown.get(key);
			internalList.add(value);
		} else {
			internalList = new ArrayList();
			internalList.add(value);
			typeBreakdown.put(key, internalList);
		}
	}

	/**
	 * Method is responsible for adding any boxes or items to the request and/or
	 * salvage object in the form.
	 * 
	 * @param theform
	 * @param request
	 * @return
	 */
	private boolean addAnyBoxesOrItems(SalvageEditForm theform, HttpServletRequest request, Salvage salvage) {

		int numberToAdd = 1;

		if (request.getParameter("addOnhands") != null) {
			String ohdId = request.getParameter("onhand");
			ohdId = StringUtils.fillzero(ohdId.trim());
			OHD ohd = SalvageDAO.loadOhd(ohdId);
			if (ohd != null) {
				SalvageOHDReference ref = new SalvageOHDReference();
				ref.setSalvage(salvage);
				ref.setOhdId(ohd.getOHD_ID());
				salvage.getOhdReferences().add(ref);
			}
			
		}
		
		if (request.getParameter("addBoxes") != null) {
			numberToAdd = TracerUtils.getNumberToAdd(request, "addBoxNum");

			for (int i = 0; i < numberToAdd; ++i) {
				SalvageBox box = new SalvageBox();
				Set<SalvageBox> boxes = theform.getSalvage().getSalvageBoxes();
				box.setSalvage(salvage);
				box.setDisplayId(boxes.size());
				box.setType(SalvageBox.TYPE_LOW_VALUE);
				boxes.add(box);
				
				LinkedHashSet<SalvageItem> salvageItems = new LinkedHashSet<SalvageItem>();
				box.setSalvageItems(salvageItems);
				
				SalvageItem item = new SalvageItem();
				item.setBox(box);
				salvageItems.add(item);
				
			}
		}

		StringTokenizer stt = null;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();

			if (parameter.indexOf("addItems") > -1) {
				String index = parameter.substring(parameter.indexOf("[") + 1, java.lang.Math.min(parameter.indexOf("]"), parameter.length()));
				numberToAdd = TracerUtils.getNumberToAdd(request, "addItemNum[" + index + "]");
				int idx = Integer.parseInt(index);
				for (int i = 0; i < numberToAdd; ++i) {
					SalvageItem item = new SalvageItem();
					Set<SalvageItem> items = theform.getSalvageBox(idx).getSalvageItems();
					item.setBox(theform.getSalvageBox(idx));
					items.add(item);
				}
				return true;
			}
			
			if (parameter.indexOf("addLostFoundButton") > -1) {
				String index = parameter.substring(parameter.indexOf("[") + 1, java.lang.Math.min(parameter.indexOf("]"), parameter.length()));
				int idx = Integer.parseInt(index);
				String lostFoundId = StringUtils.fillzero(request.getParameter("addLostFoundId[" + index +"]").trim());
		
				
				try {
					
					LostAndFoundIncident lf = SalvageDAO.loadLostAndFound(lostFoundId);
					if (lf != null) {
						String desc = lf.getItem_description().trim();
						if (desc != null) {
							desc = desc.substring(0, java.lang.Math.min(255, desc.length()));
							
							SalvageItem item = new SalvageItem();
							Set<SalvageItem> items = theform.getSalvageBox(idx).getSalvageItems();
							item.setBox(theform.getSalvageBox(idx));
							item.setDescription(desc);
							item.setLostAndFoundId(lf.getFile_ref_number());
							items.add(item);
							return true;
						}
					} else {
						logger.error("No lf object found");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				
			}
		}

		return false;
	}
	
	private String getSalvageReport(HttpServletRequest request, Agent user, Salvage salvage, String rootPath) {
		ReportBMO rBmo = new ReportBMO(request);
		return rBmo.createSalvageReport(rootPath, salvage.getSalvageId(), user);
	}

}
