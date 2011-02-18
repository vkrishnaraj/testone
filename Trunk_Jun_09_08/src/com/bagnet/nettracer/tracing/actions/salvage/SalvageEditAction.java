package com.bagnet.nettracer.tracing.actions.salvage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.SalvageDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.forms.salvage.SalvageEditForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class SalvageEditAction extends CheckedAction {
	
	private static Logger logger = Logger.getLogger(SalvageSearchAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
		
		
		Salvage salvage = new Salvage();
		
		
		String salvageId = request.getParameter("salvageId");
		if (salvageId != null) {
			int sId = Integer.parseInt(salvageId);
			if (sId > 0) {
				salvage = SalvageDAO.loadSalvage(sId);
			}
		}
		
		myForm.setSalvage(salvage);
		return mapping.findForward(TracingConstants.SALVAGE_EDIT);
	}
	
//	public static boolean actionDelete(SalvageEditForm theform, HttpServletRequest request) {
//		
//		if (request.getParameter("delete_these_elements") != null && request.getParameter("delete_these_elements").length() > 0) {
//			String deleteTheseElements =  request.getParameter("delete_these_elements");
//			String[] elements = deleteTheseElements.split(",");
//			
//			HashMap<String, ArrayList<Integer>> elementBreakdown = new HashMap();
//			HashMap<Integer, ArrayList<Integer>> itemBreakdown = new HashMap();
//			
//			// Iterate through elements and sort into types
//			for (String element: elements) {
//				String[] str = element.split("_");
//
//				// Treat inventory different because
//				// 1.  They are nested objects
//				// 2.  They must be dealt with prior to items.
//				if (str[0].equals(TracingConstants.JSP_DELETE_SALVAGE_ITEM)) {
//					addToIntegerMap(itemBreakdown, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
//				}else {
//					addToStringMap(elementBreakdown, str[0], str[1]);
//				}
//			}
//			
//			// Deal with Inventory
//			for (Integer itemIndex: itemBreakdown.keySet()) {
//				ArrayList<Integer> list = itemBreakdown.get(itemIndex);				
//				Collections.sort(list, Collections.reverseOrder());
//				
//				for (int inventoryIndex: list) {
//					Item_Inventory ii = (Item_Inventory) theform.getItem(itemIndex, -1).getInventorylist().get(inventoryIndex);
//					theform.getSalvage().getItem(itemIndex, -1).getInventorylist().remove(inventoryIndex);
//				}
//			}
//			
//			// Sort elements in the list in descending integer order,
//			// then iterate through them to perform action.
//			for (String key: elementBreakdown.keySet()) {
//				ArrayList<Integer> list = elementBreakdown.get(key);				
//				Collections.sort(list, Collections.reverseOrder());
//				
//				for (int index: list) {
//					
//					if(key.equals(TracingConstants.JSP_DELETE_ITINERARY)) {
//						List itinerarylist = theform.getItinerarylist();
//						if (itinerarylist != null)
//							itinerarylist.remove(index);
//					} 
//
//					if(key.equals(TracingConstants.JSP_DELETE_CLAIMCHECK)) {
//						List claimchecklist = theform.getClaimchecklist();
//						if (claimchecklist != null)
//							claimchecklist.remove(index);
//					} 
//
//					if(key.equals(TracingConstants.JSP_DELETE_ITEM)) {
//						List itemList = theform.getItemlist();
//						if (itemList != null)
//							itemList.remove(index);
//					}
//					
//					if(key.equals(TracingConstants.JSP_DELETE_PAX)) {
//						List passList = theform.getPassengerlist();
//						if (passList != null)
//							passList.remove(index);
//					}
//					
//					if(key.equals(TracingConstants.JSP_DELETE_ARTICLE)) {
//						List articleList = theform.getArticlelist();
//						if (articleList != null)
//							articleList.remove(index);
//					} 		
//				}
//			}
//		}
//		
//		boolean deleteRemark = false;
//
//		// when adding or deleting from the page,
//		// email_customer checkbox needs to be set to 0 if user unchecked it
//		if (request.getParameter("email_customer") == null)
//			theform.setEmail_customer(0);
//
//		String index = "0";
//		Enumeration e = request.getParameterNames();
//		while (e.hasMoreElements()) {
//			String parameter = (String) e.nextElement();
//			if (parameter.indexOf("[") != -1) {
//				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
//					if (parameter.indexOf("deleteRemark") != -1) {
//					deleteRemark = true;
//					break;
//				}
//			}
//		}
//		if (deleteRemark) {
//			List remarkList = theform.getRemarklist();
//			if (remarkList != null)
//				remarkList.remove(Integer.parseInt(index));
//			request.setAttribute("remark", Integer.toString(theform.getRemarklist().size() - 1));
//			return true;
//		}
//		return false;
//	}

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

}
