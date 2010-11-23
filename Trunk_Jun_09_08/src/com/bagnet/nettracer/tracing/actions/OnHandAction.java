package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.bagnet.clients.us.SharesIntegrationWrapper;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendOhd;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.ImageUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TaskUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Implementation of <strong>Action </strong> that is responsible for
 * adding/viewing/modifying on-hand reports.
 * 
 * @author Ankur Gupta
 */
public class OnHandAction extends CheckedAction {
    private static final String FAULT_STATION_LIST = "faultStationList";
	private static Logger logger = Logger.getLogger(OnHandAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // check session/user
													// validity
		boolean rohd = false;
		TracerUtils.checkSession(session);
		Agent user = (Agent) session.getAttribute("user");
		if(user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		OnHandForm theform = (OnHandForm) form;
		ActionMessages errors = new ActionMessages();

		if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
				&& !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_OH, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(request.getParameter("historical_report") != null && request.getParameter("historical_report").length() > 0) {
			request.setAttribute("outputtype", "0");
			return (mapping.findForward(TracingConstants.OHD_HISTORICAL));
		}
		else if(request.getParameter("viewhistoryreport") != null
				&& request.getParameter("viewhistoryreport").length() > 0) {
			String reportfile = getReportFile(theform, user, request);

			if(reportfile == null || reportfile.equals("")) {
				if(request.getParameter("outputtype").equalsIgnoreCase("5")) {	//Teletype
					ActionMessage error = new ActionMessage("");
					String teletypeAddress = request.getParameter("teletypeAddress");
					if(teletypeAddress == null || teletypeAddress.equals("")) {
						error = new ActionMessage("message.no.teletype.address.provided");
					} else {
						error = new ActionMessage("message.send.teletype.info");
					}
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} else {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}
			else {
				request.setAttribute("reportfile", reportfile);
			}

			return mapping.findForward(TracingConstants.OHD_HISTORICAL);
		}
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}


		BagService bs = new BagService();	

		// Status pertaining to the on hand file
		request.setAttribute("onhand", "1");

		request.setAttribute(FAULT_STATION_LIST, createFaultStationList(theform, user));
		request.setAttribute("lossCodes", createLossCodeList(theform, user));

		if (request.getParameter("cancelFwd") != null && request.getParameter("ohd_ID") != null) {
			OHDUtils.cancelForward(request.getParameter("ohd_ID"), user);
		}
		
		// add new remark box -- set new remark with current gmt time
		if(request.getParameter("addremark") != null) {
			Remark r = theform.getRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("remark", "1");
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add new itinerary box
		else if(request.getParameter("additinerary") != null) {
			theform.getItinerary(theform.getItinerarylist().size());
			request.setAttribute("itinerary", "1");
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add a new baggage item
		else if(request.getParameter("additem") != null) {
			theform.getItem(theform.getItemlist().size());
			request.setAttribute("item", "1");
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// add a new passenger
		else if(request.getParameter("addPassenger") != null) {
			request.setAttribute("passenger", "1");
			theform.getPassenger(theform.getPassengerList().size());
			if(request.getParameter("express") != null)
				return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}// upload a photo
		else if(request.getParameter("uploadPhoto") != null) {
			request.setAttribute("upload", "1");

			// Save the file in the local directory.
			Hashtable files = theform.getMultipartRequestHandler().getFileElements();
			FormFile theFile = (FormFile) files.get("theFile1");
			if(theFile != null && theFile.getFileSize() > 0) {
				String st = Long.toString((new Date()).getTime());
				String lead = "";
				if(theform.getOhd_id() != null && theform.getOhd_id().length() > 0)
					lead = theform.getOhd_id();

				// now make subfolder with year and month
				Calendar cal = new GregorianCalendar();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);

				// compute the folder name
				String folder = user.getStation().getCompany().getCompanyCode_ID() + "/" + year + "/" + month + "/"
						+ day + "/";
				;

				// paths to be stored in the db
				String fileName = theFile.getFileName();
				String picpath = folder + lead + "_" + st + "_" + fileName;
				String thumbpath = folder + lead + "_" + st + "_thumb_" + fileName;

				boolean uploadresult = ImageUtils.doUpload(theFile, user, folder, picpath, thumbpath);
				if(!uploadresult) {
					ActionMessage error = new ActionMessage("error.uploadfile");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
					return (mapping.findForward(TracingConstants.OHD_MAIN));
				}
				else {
					// add the image to the DB.
					OHD_Photo photo = new OHD_Photo();
					photo.setPicpath(picpath);
					photo.setThumbpath(thumbpath);
					photo.setFileName(fileName);
					theform.getPhotoList().add(photo);
				}
			}
			else {
				// upload file errors.
				ActionMessage error = new ActionMessage("error.uploadfile");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			}
			return (mapping.findForward(TracingConstants.OHD_MAIN));
		}		// save temporary.
		else if((request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))
				|| request.getParameter("savetracing") != null || request.getParameter("savetowt") != null || request.getParameter("amendtowt") != null) {
			// key to determine whether the action is add new, close, or update
			int saveActionType = ADD_NEW_RECORD;
			
			OHD oDTO = null;
			if(theform.getOhd_id() != null) {
				oDTO = bs.findOHDByID(theform.getOhd_id());
				//TODO: somewhere it needs to be set to close type
				if (oDTO != null) {
					saveActionType = UPDATE_RECORD;
				}
			}
				
			if(oDTO == null) {
				oDTO = new OHD();
				oDTO.setAgent(user);
			}
			if(oDTO.getFaultstation_ID() != 0) {
				addFaultStation((List<Station>) request.getAttribute(FAULT_STATION_LIST), oDTO.getFaultstation_ID());
			}
			if(oDTO.getLoss_code() != 0) {
				addLossCode((List<Company_specific_irregularity_code>) request.getAttribute("lossCodes"), oDTO.getLoss_code());
			}
			Status s = new Status();

			// Do a temporary saving..Matching is not initiated.
			if(request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))
				s.setStatus_ID(TracingConstants.OHD_STATUS_TEMP);
			else {
				//file status of null indicates new file so we have to set status
				//to "open" or "early bag"
				if(theform.getStatus() == null) {
					if(theform.getEarlyBag() != null && theform.getEarlyBag()) {
						s.setStatus_ID(TracingConstants.OHD_STATUS_EARLY_BAG);
						oDTO.setEarlyBag(true);
					}
					else {
						s.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);
					}
				}
				else {
					s.setStatus_ID(theform.getStatus().getStatus_ID());
					//earlybag indicator is tightly coupled with status so we set it here instead of
					//in insertOnHand function below
					if(theform.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_EARLY_BAG) {
						oDTO.setEarlyBag(true);
					}
				}
			}
			oDTO.setStatus(s);
			ArrayList list = new ArrayList();
			// Mass on hand entry by adding multiple bag tag numbers.
			if(request.getParameter("mass") != null) {
				list.add("mass");
			}
			if(request.getParameter("savetowt") != null && oDTO.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_OPEN) {
				rohd = false;
			}
			else if(request.getParameter("savetracing") != null 
					|| request.getParameter("savetowt") != null  
					|| request.getParameter("amendtowt") != null
					|| (request.getParameter("savetemp") != null && !request.getParameter("savetemp").equals(""))) {

				//in all situations we save the nettracer copy of the file
				rohd = bs.insertOnHand(oDTO, theform, list, user, theform.getFound_station(), theform.getFound_company(), theform.getWtFile());
				
				//they did a save of any kind and the status is now closed, we need to close the WT file
				if(oDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED) {

					saveActionType = CLOSE_RECORD;
				}
				
				if(oDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED && oDTO.getWtFile() != null && !oDTO.getWtFile().getWt_status().equals(WTStatus.CLOSED)) {
					WtqOhdAction wtq = new WtqCloseOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
				else if (request.getParameter("savetracing") != null) {
					if(oDTO.getWtFile() != null && !oDTO.getWtFile().getWt_status().equals(WTStatus.CLOSED) && 
							user.getStation().getCompany().getVariable().isAuto_wt_amend()) {
						WtqOhdAction wtq = new WtqAmendOhd();
						wtq.setAgent(user);
						wtq.setCreatedate(TracerDateTime.getGMTDate());
						wtq.setOhd(oDTO);
						WorldTracerQueueUtils.createOnlyQueue(wtq);
					}
				}
				//they hit the savetowt file, we need to create a WT OHD
				else if(request.getParameter("savetowt") != null) {
					WtqOhdAction wtq = new WtqCreateOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
				
				else if(request.getParameter("amendtowt") != null && oDTO.getWtFile() != null && !oDTO.getWtFile().getWt_status().equals(WTStatus.CLOSED )) {
					WtqOhdAction wtq = new WtqAmendOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
			}
			if(rohd) {

				request.setAttribute("ohd_ID", oDTO.getOHD_ID());
				if(request.getParameter("mass") != null) {
					request.setAttribute("ohdidlist", list);
					return mapping.findForward(TracingConstants.MASS_ON_HAND_SUCCESS);
				}
				else {
					//logger.error(">>>>>>>>>saveActionType (1-addnew; 2-close; 3-update) : " + saveActionType);
					if (saveActionType == UPDATE_RECORD) {
						return (mapping.findForward(TracingConstants.UPDATE_ON_HAND_SUCCESS));
					} else if (saveActionType == CLOSE_RECORD) {
						return (mapping.findForward(TracingConstants.CONFIRM_CLOSE_ON_HAND_SUCCESS));
						
					} else {
						return (mapping.findForward(TracingConstants.INSERT_ON_HAND_SUCCESS));
					}
					
				}
			}
			else {
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}
		}
		else {
			boolean foundAddress = false;
			boolean deletePassenger = false;
			boolean deleteAddress = false;
			boolean deleteBagItin = false;
			boolean deleteItem = false;
			boolean deletePhoto = false;
			boolean deleteRemark = false;

			// This technique is employed to get the []['nd] reference
			String index = "0";
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if(parameter.indexOf("[") != -1) {
					index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));

					// Add a new address within a passenger
					if(parameter.indexOf("addAddress") != -1) {
						foundAddress = true;
						break;
					}// delete passenger is clicked
					else if(parameter.indexOf("deletePassenger") != -1) {
						deletePassenger = true;
						break;
					}// delete address is clicked
					else if(parameter.indexOf("deleteAddress") != -1) {
						deleteAddress = true;
						break;
					}// delete bag is clicked
					else if(parameter.indexOf("deleteBag") != -1) {
						deleteBagItin = true;
						break;
					}// delete bag item is clicked.
					else if(parameter.indexOf("deleteItem") != -1) {
						deleteItem = true;
						break;
					}// delete photo is clicked
					else if(parameter.indexOf("deletePhoto") != -1) {
						deletePhoto = true;
						break;
					}// delete remark is clicked.
					else if(parameter.indexOf("deleteRemark") != -1) {
						deleteRemark = true;
						break;
					}
				}
			}

			// add address
			if(foundAddress) {
				OHD_Address address = new OHD_Address();
				OHD_Passenger passenger = theform.getPassenger(Integer.parseInt(index));
				passenger.addAddress(address);
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete passenger
			else if(deletePassenger) {
				request.setAttribute("passenger", "1");
				List passengerList = theform.getPassengerList();
				if(passengerList != null) {
					passengerList.remove(Integer.parseInt(index));
				}
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete address
			else if(deleteAddress) {
				request.setAttribute("passenger", "1");
				theform.removeAddress(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete bag
			else if(deleteBagItin) {
				request.setAttribute("itinerary", "1");
				List itnList = theform.getItinerarylist();
				if(itnList != null)
					itnList.remove(Integer.parseInt(index));
				if(request.getParameter("express") != null)
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete bag-item
			else if(deleteItem) {
				request.setAttribute("item", "1");
				List itemList = theform.getItemlist();
				if(itemList != null)
					itemList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete photo
			else if(deletePhoto) {
				request.setAttribute("upload", "1");
				List photoList = theform.getPhotoList();
				if(photoList != null)
					photoList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}// delete remark
			else if(deleteRemark) {
				request.setAttribute("remark", "1");
				List remarkList = theform.getRemarklist();
				if(remarkList != null)
					remarkList.remove(Integer.parseInt(index));
				return (mapping.findForward(TracingConstants.OHD_MAIN));
			}
			else if (request.getParameter("wt_id") != null && request.getParameter("wt_id").length() == 10) {
				OHD foundohd = WorldTracerUtils.findOHDByWTID(request.getParameter("wt_id"));
				
				if (foundohd == null) {
					logger.info("About to import OHD from WT...");
					WorldTracerService wts = SpringUtils.getWorldTracerService();
					
					try {
						wts.getWtConnector().initialize();
						foundohd = wts.getOhdforOhd(request.getParameter("wt_id"), WTStatus.ACTIVE, user, WorldTracerWebService.getBasicDto(session));
						
						if (foundohd == null) {
							errors = new ActionMessages();
							ActionMessage error = new ActionMessage("error.wt_nostation");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						}
					}
					catch (WorldTracerRecordNotFoundException ex) {
						errors = new ActionMessages();
						ActionMessage error = new ActionMessage("error.wt.no.ohd");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
					catch (WorldTracerException ex) {
						logger.error("Error", ex);
						errors = new ActionMessages();
						ActionMessage error = new ActionMessage("error.wt_nostation");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					} finally {
						wts.getWtConnector().logout();
					}

				}
				if(errors.size() > 0) {
					return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
				}

				TracerUtils.populateOnHand(theform, request);
				bs.fillTheOhdForm(foundohd, theform, user, request);
				addFaultStation((List<Station>) request.getAttribute(FAULT_STATION_LIST), foundohd.getFaultstation_ID());
				addLossCode((List<Company_specific_irregularity_code>) request.getAttribute("lossCodes"), foundohd.getLoss_code());
				theform.setFoundDate(TracerDateTime.getGMTDate());
				theform.setFoundTime(TracerDateTime.getGMTDate());
				session.setAttribute("OnHandForm", theform);
				
				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG, user)) {
					theform.setReadonly(0);
				}
				
				return mapping.findForward(TracingConstants.OHD_MAIN);
				
			}
			else {
				String onhand_id = request.getParameter("ohd_ID");
				if(onhand_id == null || onhand_id.trim().length() < 1) {
					onhand_id = request.getParameter("hidden_ohd_id");
				}
				if(onhand_id != null && onhand_id.length() > 0) {
					// if OHD_ID exists; load exisiting on-hand
					theform.setAgent(user);
					OHD ohd;
					if((ohd = bs.findOnHand(onhand_id, theform, user, request)) == null) {
						ActionMessage error = new ActionMessage("error.no.onhandreport");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
						return (mapping.findForward(TracingConstants.SEARCH_ONHAND));
					}
					if(request.getParameter("wtq_reinstate") != null && request.getParameter("wtq_reinstate").trim().length() > 0) {
						WtqReinstateOhd wtq = new WtqReinstateOhd();
						wtq.setAgent(user);
						wtq.setOhd(ohd);
						WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					}
					if(request.getParameter("wtq_suspend") != null && request.getParameter("wtq_suspend").trim().length() > 0) {
						WtqSuspendOhd wtq = new WtqSuspendOhd();
						wtq.setAgent(user);
						wtq.setOhd(ohd);
						WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					}
					// need to find out if there are any pending world tracer actions
					// for this incident
					boolean result = true;
					if(request.getParameter("wtq_pending_cancel") != null && request.getParameter("wtq_pending_cancel").trim().length() > 0) {
						long wtq_id = Long.parseLong(request.getParameter("wtq_pending_cancel"));
						try {
							result = WorldTracerQueueUtils.cancelPendingAction(wtq_id);
							if(result) {
								request.setAttribute("ohd_id", ohd.getOHD_ID());
								return (mapping.findForward(TracingConstants.OHD_MAIN));
							}
						} catch(Exception ex) {
							errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.wt_cancel_pending"));	
						}
					}
					WorldTracerQueue pendingAction = WorldTracerQueueUtils.findPendingOhdAction(onhand_id);
					if(pendingAction != null) {
						if(pendingAction instanceof WtqCreateOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CREATE);
						}
						else if(pendingAction instanceof WtqAmendOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_AMEND);
						}
						else if(pendingAction instanceof WtqSuspendOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_SUSPEND);
						}
						else if(pendingAction instanceof WtqReinstateOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_REINSTATE);
						}
						else if(pendingAction instanceof WtqCloseOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_CLOSE);
						}
						else if(pendingAction instanceof WtqFwdOhd) {
							request.setAttribute("pendingWtAction", TracingConstants.WT_PENDING_FOH);
						}
						request.setAttribute("wtq_pending_id", pendingAction.getWt_queue_id());
					}
				}
				else {
					// Create a new on-hand entry
					TracerUtils.populateOnHand(theform, request);
					// Begin Prepopulate
					
					ArrayList alerrors = new ArrayList();
					if (request.getParameter("doprepopulate") != null && request.getParameter("doprepopulate").length() > 0) {
						alerrors.addAll(SpringUtils.getReservationIntegration().populateOhdForm(request, theform));

						if (alerrors.size() > 0) {
							for (int i=0;i<alerrors.size();i++) {
								ActionMessage error = new ActionMessage((String)alerrors.get(i));
								errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							}
							saveMessages(request, errors);
							request.setAttribute("prepopulate", new Integer("1"));
						}
					} else if (SpringUtils.getReservationIntegration().isPopulateOhdFormOn() && request.getParameter("skip_prepopulate") == null && request.getParameter("wt_af_id") == null) {
						request.setAttribute("prepopulate",new Integer("1"));
					}
				}

				if(request.getParameter("express") != null) {
					return mapping.findForward(TracingConstants.OHD_QUICK_MAIN);
				}
				else if(request.getParameter("mass") != null)
					return mapping.findForward(TracingConstants.OHD_MASS_MAIN);

				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG, user)) {
					if(user.getStation().getCompany().getCompanyCode_ID().equals(theform.getHolding_company())
							&& user.getStation().getStationcode().equals(theform.getHolding_station())) {
						theform.setReadonly(0);
						return mapping.findForward(TracingConstants.OHD_MAIN);
					}
					else {
						theform.setReadonly(1);
					}
				}
				else {
					theform.setReadonly(1);
				}

				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_OH, user))
					theform.setAllow_remark_update(1);

				return mapping.findForward(TracingConstants.OHD_MAIN);

			}
		}
	}

	private void addLossCode(List<Company_specific_irregularity_code> codeList, int loss_code) {
		boolean addLossCode = false;

		if (loss_code > 0) {
			addLossCode = true;
			for (Company_specific_irregularity_code code : codeList) {
				if (code.getCode_id() == loss_code) {
					addLossCode = false;
					break;
				}
			}
		}
		if (addLossCode) {
			codeList.add(LossCodeBMO.getCode(loss_code));
		}
		
	}

	private void addFaultStation(List<Station> stationList, int faultstation_ID) {
		boolean addStation = false;
		
		if(faultstation_ID > 0) {
			addStation = true;
			for(Station st: stationList) {
				if(st.getStation_ID() == faultstation_ID) {
					addStation = false;
					break;
				}
			}
		}
		if(addStation){
			stationList.add(StationBMO.getStation(faultstation_ID));
		}
	}

	private List<Company_specific_irregularity_code> createLossCodeList(OnHandForm theform, Agent user) {
		List<Company_specific_irregularity_code> result = LossCodeBMO.getCompanyCodes(user.getStation().getCompany().getCompanyCode_ID(), TracingConstants.OHD,  true, user);
		
		boolean addFormCode = false;
		if (theform.getLoss_code() > 0) {
			addFormCode = true;
			for (Company_specific_irregularity_code code : result) {
				if (code.getCode_id() == theform.getLoss_code()) {
					addFormCode = false;
					break;
				}
			}
		
		}
		if(addFormCode) {
			result.add(LossCodeBMO.getCode(theform.getLoss_code()));
		}
		return result;
	}

	private List<Station> createFaultStationList(OnHandForm theform, Agent user) {
		List<Station> result = new ArrayList<Station>();
		if(UserPermissions.hasLimitedSavePermissionByType(user, TracingConstants.OHD)) {
			String stations = PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_OHD_ADDSTATIONS);
			if(stations != null) {
				List<String> foo = Arrays.asList(stations.split(","));
				result.addAll(StationBMO.getStationListByCode(foo, user.getCompanycode_ID()));
				
			}
			if(!result.contains(user.getStation())) {
				result.add(user.getStation());
			}
			return result;
		}
		else {
			result.addAll(TracerUtils.getStationList(user.getCompanycode_ID()));
		}
		
		boolean addUserStation = true;
		boolean addFormStation = theform.getFaultstation_ID() > 0;
		for(Station foo : result) {
			if(foo.getStation_ID() == user.getStation().getStation_ID()) {
				addUserStation = false;
			}
			if(theform.getFaultstation_ID() == foo.getStation_ID()) {
				addFormStation = false;
			}
			if(!addFormStation && !addUserStation) break;
		}
		if(addFormStation) result.add(StationBMO.getStation(theform.getFaultstation_ID()));
		if(addUserStation) result.add(user.getStation());
		
		return result;
	}

	private String getReportFile(OnHandForm theform, Agent user, HttpServletRequest request) {

		// need the historical report for this report.
		String ohd_id = theform.getOhd_id();

		ServletContext sc = getServlet().getServletContext();
		String reportpath = sc.getRealPath("/");

		HashMap selections = new HashMap();

		if(request.getParameter("all") != null) {
			request.setAttribute("all", "1");
			selections.put("passenger", "true");
			selections.put("itinerary", "true");
			selections.put("inventory", "true");
			selections.put("remarks", "true");
			selections.put("photos", "true");
			selections.put("messages", "true");
			selections.put("tasks", "true");
			selections.put("forward", "true");
			selections.put("request", "true");
			selections.put("matches", "true");

		}
		else {
			request.setAttribute("all", "0");
			if(request.getParameter("passenger") != null) {
				request.setAttribute("passenger", "1");
				selections.put("passenger", "true");
			}
			else {
				selections.put("passenger", null);
			}

			if(request.getParameter("itinerary") != null) {
				request.setAttribute("itinerary", "1");
				selections.put("itinerary", "true");
			}
			else
				selections.put("itinerary", null);

			if(request.getParameter("inventory") != null) {
				request.setAttribute("inventory", "1");
				selections.put("inventory", "true");
			}
			else
				selections.put("inventory", null);

			if(request.getParameter("remarks") != null) {
				request.setAttribute("remarks", "1");
				selections.put("remarks", "true");
			}
			else
				selections.put("remarks", null);

			if(request.getParameter("photos") != null) {
				request.setAttribute("photos", "1");
				selections.put("photos", "true");
			}
			else
				selections.put("photos", null);

			if(request.getParameter("messages") != null) {
				request.setAttribute("messages", "1");
				selections.put("messages", "true");
			}
			else
				selections.put("messages", null);

			if(request.getParameter("tasks") != null) {
				request.setAttribute("tasks", "1");
				selections.put("tasks", "true");
			}
			else
				selections.put("tasks", null);

			if(request.getParameter("forward") != null) {
				request.setAttribute("forward", "1");
				selections.put("forward", "true");
			}
			else
				selections.put("forward", null);

			if(request.getParameter("request") != null) {
				request.setAttribute("request", "1");
				selections.put("request", "true");
			}
			else
				selections.put("request", null);

			if(request.getParameter("matches") != null) {
				request.setAttribute("matches", "1");
				selections.put("matches", "true");
			}
			else
				selections.put("matches", null);
		}
		int type = Integer.parseInt(request.getParameter("outputtype"));

		request.setAttribute("outputtype", "" + type);

		String reportfile = createReport(type, sc, theform.getOhd_id(), user, selections, request);

		return reportfile;
	}

	private static String createReport(int type, ServletContext sc, String ohd_id, Agent user, HashMap selections,
			HttpServletRequest request) {

		OnHandForm form = new OnHandForm();
		BagService bs = new BagService();
		try {
			bs.findOnHand(ohd_id, form, user, request);
		}
		catch (Exception e) {
		}

		try {
			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("airline", format("" + form.getAgent().getCompanycode_ID()));
			parameters.put("createdate", format("" + form.getDispFoundTime()));
			parameters.put("file_reference", format("" + form.getOhd_id()));
			parameters.put("holding_company", format("" + form.getHolding_company()));
			parameters.put("found_at_station", format("" + form.getFound_station()));
			parameters.put("status", format("" + form.getStatus().getTextDescription(user)));
			
			form.getMatched_incident();
			form.getClosingRemark(2);
			
			
			if(form.getXDesc1() > 0) {
				parameters.put("ede1", format("" + TracerUtils.getXdescelement(form.getXDesc1()).getTextDescription(user)));
			}
			else {
				parameters.put("ede1", "");
			}
			if(form.getXDesc2() > 0) {
				parameters.put("ede2", format("" + TracerUtils.getXdescelement(form.getXDesc2()).getTextDescription(user)));
			}
			else {
				parameters.put("ede2", "");
			}
			if(form.getXDesc3() > 0) {
				parameters.put("ede3", format("" + TracerUtils.getXdescelement(form.getXDesc3()).getTextDescription(user)));
			}
			else {
				parameters.put("ede3", "");
			}
			parameters.put("bag_arrive_date", format("" + form.getDispBagArriveDate()));
			parameters.put("found_at_company", format("" + form.getFound_company()));
			parameters.put("airline_membership", format("" + form.getCompanycode_ID()));
			parameters.put("membership_num", format("" + form.getMembershipnum()));
			parameters.put("membershipstatus", format("" + form.getMembershipstatus()));
			parameters.put("airline_membership", format("" + form.getMembershipnum()));
			parameters.put("color", format("" + form.getBagColor()));
			if(form.getManufacturer_ID() > 0) {
				parameters.put("manufacturer", format(""
						+ TracerUtils.getManufacturer(form.getManufacturer_ID()).getDescription()));
			}
			else {
				parameters.put("manufacturer", "");
			}
			parameters.put("record_locator", format("" + form.getPnr()));
			parameters.put("membership_num", format("" + form.getMembershipnum()));
			parameters.put("type", format("" + form.getBagType()));
			parameters.put("storage_location", format("" + form.getStorage_location()));
			parameters.put("date_closed", format("" + form.getDispCloseDate()));
			parameters.put("agent", format("" + form.getAgent_initials()));
			parameters.put("bag_tag_number", format("" + form.getBagTagNumber()));
			parameters.put("holding_station", format("" + form.getHolding_station()));

			if(selections.get("passenger") != null) {
				List passengers = form.getPassengerList();
				if(passengers != null && passengers.size() > 0) {
					parameters
							.put("passengerReport", ReportBMO.getCompiledReport("ohd_passenger", sc.getRealPath("/")));
					parameters.put("addressReport", ReportBMO.getCompiledReport("ohd_address", sc.getRealPath("/")));
					parameters.put("passenger", new JRBeanCollectionDataSource(passengers));
				}
				else
					parameters.put("passenger", null);
			}
			else
				parameters.put("passenger", null);

			if(selections.get("itinerary") != null) {
				List itineraries = form.getItinerarylist();
				if(itineraries != null && itineraries.size() > 0) {
					parameters
							.put("itineraryReport", ReportBMO.getCompiledReport("ohd_itinerary", sc.getRealPath("/")));
					parameters.put("itinerary", new JRBeanCollectionDataSource(itineraries));
				}
				else
					parameters.put("itinerary", null);
			}
			else
				parameters.put("itinerary", null);

			if(selections.get("inventory") != null) {
				List inventories = form.getItemlist();  //List<Item>
				if(inventories != null && inventories.size() > 0) {
					parameters
							.put("inventoryReport", ReportBMO.getCompiledReport("ohd_inventory", sc.getRealPath("/")));
					parameters.put("inventory", new JRBeanCollectionDataSource(inventories));
				}
				else {
					parameters.put("inventory", null);
				}
			}
			else
				parameters.put("inventory", null);

			if(selections.get("remarks") != null) {
				List remarks = form.getRemarklist();
				if(remarks != null && remarks.size() > 0) {
					parameters.put("remarkReport", ReportBMO.getCompiledReport("remarks", sc.getRealPath("/")));
					parameters.put("remarks", new JRBeanCollectionDataSource(remarks));
				}
				else
					parameters.put("remarks", null);

			}
			else
				parameters.put("remarks", null);

			if(selections.get("photos") != null) {
				List photos = form.getPhotoList();
				if(photos != null && photos.size() > 0) {
					for(Iterator i = photos.iterator(); i.hasNext();) {
						OHD_Photo photo = (OHD_Photo) i.next();
						photo.setThumbpath(TracerProperties.get("image_store")
								+ photo.getThumbpath());
					}
					parameters.put("photoReport", ReportBMO.getCompiledReport("ohd_photos", sc.getRealPath("/")));
					parameters.put("photos", new JRBeanCollectionDataSource(photos));
				}
				else
					parameters.put("photos", null);
			}
			else
				parameters.put("photos", null);

			if(selections.get("messages") != null) {
				List messages = MessageUtils.getReportMessages(ohd_id, 0);
				if(messages != null && messages.size() > 0) {
					for(Iterator i = messages.iterator(); i.hasNext();) {
						Message msg = (Message) i.next();
						msg.set_DATEFORMAT(user.getDateformat().getFormat());
						msg.set_TIMEFORMAT(user.getTimeformat().getFormat());
						msg.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					parameters.put("messageReport", ReportBMO.getCompiledReport("messages", sc.getRealPath("/")));
					parameters.put("messages", new JRBeanCollectionDataSource(messages));
				}
				else {
					parameters.put("messages", null);
				}
			}
			else {
				parameters.put("messages", null);
			}

			if(selections.get("tasks") != null) {
				List tasks = TaskUtils.getFileTasks(ohd_id, 0);
				if(tasks != null && tasks.size() > 0) {
					for(Iterator i = tasks.iterator(); i.hasNext();) {
						Task task = (Task) i.next();
						task.set_DATEFORMAT(user.getDateformat().getFormat());
						task.set_TIMEFORMAT(user.getTimeformat().getFormat());
						task.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					parameters.put("taskReport", ReportBMO.getCompiledReport("tasks", sc.getRealPath("/")));
					parameters.put("tasks", new JRBeanCollectionDataSource(tasks));
				}
				else {
					parameters.put("tasks", null);
				}
			}
			else {
				parameters.put("tasks", null);
			}

			if(selections.get("forward") != null) {
				List forwardLog = OHDUtils.getForwardMessages(form.getOhd_id());
				if(forwardLog != null && forwardLog.size() > 0) {
					for(Iterator i = forwardLog.iterator(); i.hasNext();) {
						OHD_Log log = (OHD_Log) i.next();
						log.set_DATEFORMAT(user.getDateformat().getFormat());
						log.set_TIMEFORMAT(user.getTimeformat().getFormat());
						log.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));

						Set e = log.getItinerary();

						if(e != null && e.size() > 0) {
							for(Iterator iter = e.iterator(); iter.hasNext();) {
								OHD_Log_Itinerary itinerary = (OHD_Log_Itinerary) iter.next();
								itinerary.set_DATEFORMAT(user.getDateformat().getFormat());
								itinerary.set_TIMEFORMAT(user.getTimeformat().getFormat());
							}
						}
					}
					parameters.put("forwardItineraryReport", ReportBMO.getCompiledReport("forward_itinerary", sc
							.getRealPath("/")));
					parameters.put("forwardReport", ReportBMO.getCompiledReport("ohd_forward", sc.getRealPath("/")));
					parameters.put("forward", new JRBeanCollectionDataSource(forwardLog));
				}
				else {
					parameters.put("forward", null);
				}
			}
			else
				parameters.put("forward", null);

			if(selections.get("request") != null) {
				List requestList = OHDUtils.getOHDRequests(form.getOhd_id());
				if(requestList != null && requestList.size() > 0) {
					for(Iterator iter = requestList.iterator(); iter.hasNext();) {
						OHDRequest req = (OHDRequest) iter.next();
						req.set_DATEFORMAT(user.getDateformat().getFormat());
						req.set_TIMEFORMAT(user.getTimeformat().getFormat());
						req.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone())
								.getTimezone()));
					}
					// request reports
					parameters.put("requestReport", ReportBMO.getCompiledReport("ohd_request", sc.getRealPath("/")));
					parameters.put("request", new JRBeanCollectionDataSource(requestList));
				}
				else {
					parameters.put("request", null);
				}
			}
			else {
				parameters.put("request", null);
			}

			if(selections.get("matches") != null) {
				List matches = MatchUtils.getMatchWithOHD(form.getOhd_id(), true);
				if(matches != null && matches.size() > 0) {
					parameters.put("match_detail_report", ReportBMO.getCompiledReport("match_detail_report", sc
							.getRealPath("/")));
					parameters.put("matchReport", ReportBMO.getCompiledReport("match_report", sc.getRealPath("/")));
					parameters.put("matches", new JRBeanCollectionDataSource(matches));
				}
				else {
					parameters.put("matches", null);
				}
			}
			else
				parameters.put("matches", null);

			if (type == 5) {	//Teletype so no regular reports
				String myTeletypeHistoricalReport = buildTeletypeStyleHistoricalReport(parameters);
				SharesIntegrationWrapper iw = new SharesIntegrationWrapper();
				String teletypeAddress = request.getParameter("teletypeAddress");
				String myLabel = "Onhand " + form.getOhd_id() + " ";
				iw.sendTelexBySlice(myTeletypeHistoricalReport, teletypeAddress, myLabel);
				
				return null;
			} else {
				List t = new ArrayList();
				t.add("");
				ReportBMO bmo = new ReportBMO(request);
				return bmo.getReportFile(t, parameters, "onhand_history", sc.getRealPath("/"), type);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String format(String input) {
		if(input == null || input.equals("null"))
			return "";
		else
			return input;
	}
	
	private static String buildTeletypeStyleHistoricalReport(Map parameters) {
		StringBuilder historicalReport = new StringBuilder();
		historicalReport.append(newline);
		
		ResourceBundle resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		
		historicalReport.append("-- " + resourceBundle.getString("header.bag_info") + " --");
		historicalReport.append(newline);
		
		//general section
		historicalReport
			.append(resourceBundle.getString("header.file") + ": ")
			.append(parameters.get("file_reference") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.status") + ": ")
			.append(parameters.get("status") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.found.station") + ": ")
			.append(parameters.get("found_at_company") + tab)
			.append(parameters.get("found_at_station") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.holding.station") + ": ")
			.append(parameters.get("holding_company") + tab)
			.append(parameters.get("holding_station") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.found_date_time") + ": ")
			.append(parameters.get("createdate") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.bag_arrived_date") + ": ")
			.append(parameters.get("bag_arrive_date") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.bag_tag_number") + ": ")
			.append(parameters.get("bag_tag_number") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.file_close_date") + ": ")
			.append(parameters.get("date_closed") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.airline_membership") + ": ")
			.append(parameters.get("airline_membership") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.membership_number") + ": ")
			.append(parameters.get("membership_num") + newline);		
		historicalReport
			.append(resourceBundle.getString("colname.color") + ": ")
			.append(parameters.get("color") + newline);	
		historicalReport
			.append(resourceBundle.getString("colname.membership_status") + ": ")
			.append(parameters.get("membershipstatus") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.pnr") + ": ")
			.append(parameters.get("record_locator") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.storage_location") + ": ")
			.append(parameters.get("storage_location") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.x_desc") + ": ")
			.append(parameters.get("ede1") + newline)
			.append(parameters.get("ede2") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.x_desc") + ": ")
			.append(parameters.get("ede3") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.bagtype") + ": ")
			.append(parameters.get("type") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.agent_initials") + ": ")
			.append(parameters.get("agent") + newline);
		historicalReport
			.append(resourceBundle.getString("colname.manufacturer") + ": ")
			.append(parameters.get("manufacturer") + newline);
		
		//ma_passenger section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.passenger_info") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource passengerDS = (JRBeanCollectionDataSource) parameters.get("passenger");
		if (passengerDS != null) {
			List<OHD_Passenger> myPassengers = (List<OHD_Passenger>) passengerDS.getData();
			if (myPassengers != null & myPassengers.size() >= 1) {
				for (OHD_Passenger pax : myPassengers) {
					historicalReport
						.append(resourceBundle.getString("colname.pass_name"))
						.append(newline)
						.append(indent + resourceBundle.getString("colname.last_name") + ": ")
						.append(pax.getLastname() + newline)
						.append(indent + resourceBundle.getString("colname.first_name") + ": ")
						.append(pax.getFirstname() + newline)
						.append(indent + resourceBundle.getString("middle") + ": ")
						.append(pax.getMiddlename() + newline);
					
					//pax addresses
					historicalReport.append(newline);
					JRBeanCollectionDataSource addressDS = (JRBeanCollectionDataSource) pax.getAddressesForReport();
					List<OHD_Address> myAddresses = (List<OHD_Address>) addressDS.getData();
					if (myAddresses != null & myAddresses.size() >= 1) {
						for (OHD_Address address : myAddresses) {
							historicalReport
								.append(indent + resourceBundle.getString("colname.street_addr1") + ": ")
								.append(address.getAddress1() + newline)
								.append(indent + resourceBundle.getString("colname.street_addr2") + ": ")
								.append(address.getAddress2() + newline)
								.append(indent + resourceBundle.getString("colname.city") + ": ")
								.append(address.getCity() + newline)
								.append(indent + resourceBundle.getString("colname.state") + ": ")
								.append(address.getState() + newline)
								.append(indent + resourceBundle.getString("colname.province") + ": ")
								.append(address.getProvince() + newline)
								.append(indent + resourceBundle.getString("colname.country") + ": ")
								.append(address.getCountry() + newline)
								.append(indent + resourceBundle.getString("colname.zip") + ": ")
								.append(address.getZip() + newline)
								.append(indent + resourceBundle.getString("colname.business_ph") + ": ")
								.append(address.getWorkphone() + newline)
								.append(indent + resourceBundle.getString("colname.home_ph") + ": ")
								.append(address.getHomephone() + newline)
								.append(indent + resourceBundle.getString("colname.alt_ph") + ": ")
								.append(address.getAltphone() + newline)
								.append(indent + resourceBundle.getString("colname.mobile_ph") + ": ")
								.append(address.getMobile() + newline)
								.append(indent + resourceBundle.getString("colname.pager_ph") + ": ")
								.append(address.getPager() + newline)
								.append(indent + resourceBundle.getString("colname.email") + ": ")
								.append(address.getEmail() + newline);
						}
					}
				}
			}
		}
		
		//bag_itinerary section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.bag_itinerary") + " --");
		historicalReport.append(newline);		
		JRBeanCollectionDataSource bagItineraryDS = (JRBeanCollectionDataSource) parameters.get("itinerary");
		if (bagItineraryDS != null) {
			List<OHD_Itinerary> bagItineraries = (List<OHD_Itinerary>) bagItineraryDS.getData();
			if (bagItineraries != null & bagItineraries.size() >= 1) {
				for (OHD_Itinerary bagItinerary : bagItineraries) {
					historicalReport
						.append(resourceBundle.getString("colname.fromto") + ": ")
						.append(bagItinerary.getLegfrom() + tab + "/" + bagItinerary.getLegto() + newline)
						.append(resourceBundle.getString("colname.flightnum") + ": ")
						.append(bagItinerary.getAirline() + tab + bagItinerary.getFlightnum() + newline)
						.append(resourceBundle.getString("colname.departdate") + ": ")
						.append(bagItinerary.getDisdepartdate() + newline)
						.append(resourceBundle.getString("colname.arrdate") + ": ")
						.append(bagItinerary.getDisarrivedate() + newline)
						
						
						.append(resourceBundle.getString("colname.schdeptime") + ": ")	
						.append(bagItinerary.getDisschdeparttime() + newline)
						.append(resourceBundle.getString("colname.actdeptime") + ": ")
						.append(bagItinerary.getDisscharrivetime() + newline)
						.append(resourceBundle.getString("colname.scharrtime") + ": ") 
						.append(bagItinerary.getDisactarrivetime() + newline)						
						.append(resourceBundle.getString("colname.actarrtime") + ": ")
						.append(bagItinerary.getDisactarrivetime() + newline);
					
					
				}
			}
		}
		
		//central baggage inventory section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.central_baggage_inventory") + " --");
		historicalReport.append(newline);		
		JRBeanCollectionDataSource centralBagInventoryDS = (JRBeanCollectionDataSource) parameters.get("inventory");
		if (centralBagInventoryDS != null) {
			List<OHD_Inventory> items = (List<OHD_Inventory>) centralBagInventoryDS.getData();
			if (items != null & items.size() >= 1) {
				for (OHD_Inventory item : items) {
					historicalReport					
						.append(resourceBundle.getString("colname.category") + ": ")
						.append(item.getCategory() + newline)
						.append(resourceBundle.getString("colname.description") + ": ")
						.append(item.getDescription() + newline);
				}
			}
		}		
		
		//remarks section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.remarks") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource remarksDS = (JRBeanCollectionDataSource) parameters.get("remarks");
		List<Remark> remarks = (List<Remark>) remarksDS.getData();
		if (remarks != null & remarks.size() >= 1) {
			for (Remark remark : remarks) {
				historicalReport
					.append(resourceBundle.getString("colname.date_time") + ": ")
					.append(remark.getDispcreatetime() + newline)
					.append(resourceBundle.getString("colname.station") + ": ")
					.append(remark.getAgentStation() + newline)
					.append(resourceBundle.getString("colname.agent") + ": ")
					.append(remark.getAgentUsername() + newline)
					.append(remark.getRemarktext() + newline);
				historicalReport.append(newline);
			}
		}
		
		//photos section

		//matches section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.matches") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource matchesDS = (JRBeanCollectionDataSource) parameters.get("matches");
		if (matchesDS != null) {
			List<Match> matches = (List<Match>) matchesDS.getData();
			if (matches != null & matches.size() >= 1) {
				for (Match match : matches) {
					historicalReport
						.append(resourceBundle.getString("header.match_category") + ": ")
						.append(match.getReportCategory() + newline)
						.append(resourceBundle.getString("header.file") + ": ")
						.append(match.getReportNumber() + newline)
						.append(resourceBundle.getString("header.match_date") + ": ")
						.append(match.getDispdate() + newline)
						.append(resourceBundle.getString("header.ohd") + ": ")
						.append(match.getOhdNumber() + newline)
						.append(resourceBundle.getString("header.match_percent") + ": ")
						.append(match.getReportPercentage() + newline);
					
					historicalReport.append(newline);	
					
					//matching details section
					historicalReport.append(indent + "-- " + resourceBundle.getString("header.match_elements") + " --");
					historicalReport.append(newline);
					List matchDetails = new ArrayList(match.getDetails());
					
					if (matchDetails != null) {
						List<Match_Detail> matcheDetails = (List<Match_Detail>) matchDetails;
						if (matcheDetails != null & matcheDetails.size() >= 1) {
							historicalReport
							.append(indent + rightPad(resourceBundle.getString("colname.item"), 20) + tab)
							.append(indent + leftPad(resourceBundle.getString("colname.percentage"), 11) + tab)
							.append(indent + leftPad(resourceBundle.getString("colname.mbr_info"), 25) + tab)
							.append(indent + leftPad(resourceBundle.getString("colname.ohd_info"), 25) + newline);
						
							for (Match_Detail matchDetail : matcheDetails) {
								historicalReport
									.append(indent + rightPad(resourceBundle.getString(matchDetail.getItem()), 20) + tab)
									.append(indent + leftPad(matchDetail.getReportPercentage(), 11) + tab)
									.append(indent + leftPad(matchDetail.getMbr_info(), 25) + tab)
									.append(indent + leftPad(matchDetail.getOhd_info(), 25) + newline);
								historicalReport.append(newline);	
							}
						}				
					} else {
						historicalReport.append("N/A" + newline);
					}
				}
			}				
		} else {
			historicalReport.append("N/A" + newline);
		}	
		
		//request section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.requests") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource requestsDS = (JRBeanCollectionDataSource) parameters.get("request");
		if (requestsDS != null) {
			List<OHDRequest> requests = (List<OHDRequest>) requestsDS.getData();
			if (requests != null & requests.size() >= 1) {
				for (OHDRequest request : requests) {
					historicalReport
						.append(resourceBundle.getString("colname.requesting_agent") + ": ")
						.append(request.getRequestee() + newline)
						.append(resourceBundle.getString("header.company") + ": ")
						.append(request.getRequestingCompany() + newline)
						.append(resourceBundle.getString("header.station") + ": ")
						.append(request.getRequestingStation() + newline)
						.append(resourceBundle.getString("colname.date_time") + ": ")
						.append(request.getTimeRequested() + newline)
						.append(resourceBundle.getString("header.status") + ": ")
						.append(request.getRequestStatus() + newline)
						.append(resourceBundle.getString("colname.requestReason") + ": ")
						.append(request.getReason() + newline);
					
					historicalReport.append(newline);				
				}
			}				
		} else {
			historicalReport.append("N/A" + newline);
		}
		
		//forward section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.forward_log") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource forwardDS = (JRBeanCollectionDataSource) parameters.get("forward");
		if (forwardDS != null) {
			List<OHD_Log> forwards = (List<OHD_Log>) forwardDS.getData();
			if (forwards != null & forwards.size() >= 1) {
				for (OHD_Log forward : forwards) {
					historicalReport
						.append(resourceBundle.getString("colname.destination_company") + ": ")
						.append(forward.getDestCompany() + newline)
						.append(resourceBundle.getString("colname.station") + ": ")
						.append(forward.getDestStation() + newline)
						.append(resourceBundle.getString("colname.expedite_number") + ": ")
						.append(forward.getExpeditenum() + newline)
						.append(resourceBundle.getString("colname.date_time") + ": ")
						.append(forward.getDispForwardTime() + newline);
					
					historicalReport.append(newline);	
					
					//forward - bag itinerary section
					historicalReport.append(newline);
					historicalReport.append(indent + "-- " + resourceBundle.getString("header.bag_itinerary") + " --");
					historicalReport.append(newline);
					Set<OHD_Log_Itinerary> ohdLogItineraries = (Set<OHD_Log_Itinerary>) forward.getItinerary();
					if (ohdLogItineraries != null & ohdLogItineraries.size() >=1) {
						for (OHD_Log_Itinerary ohdLogItinerary : ohdLogItineraries) {
							historicalReport
								.append(indent + resourceBundle.getString("colname.fromto") + ": ")
								.append(ohdLogItinerary.getLegfrom() + tab + "/" + ohdLogItinerary.getLegto() + newline)
								.append(indent + resourceBundle.getString("colname.flightnum") + ": ")
								.append(ohdLogItinerary.getAirline() + tab + "/" + ohdLogItinerary.getFlightnum() + newline)
								.append(indent + resourceBundle.getString("colname.departdate") + ": ")
								.append(ohdLogItinerary.getDisdepartdate() + newline)
								.append(indent + resourceBundle.getString("colname.arrdate") + ": ")
								.append(ohdLogItinerary.getDisarrivedate() + newline)
								.append(indent + resourceBundle.getString("colname.schdeptime") + ": ")
								.append(ohdLogItinerary.getDisschdeparttime() + newline)
								.append(indent + resourceBundle.getString("colname.actdeptime") + ": ")
								.append(ohdLogItinerary.getDisscharrivetime() + newline);
						}
					}
					
					//forward - message section
					historicalReport.append(newline);
					historicalReport
						.append(indent + resourceBundle.getString("colname.message") + ": " + newline)
						.append(forward.getMessage() + newline);
				}
			}				
		} else {
			historicalReport.append("N/A" + newline);
		}
		
		//tasks section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.tasks") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource tasksDS = (JRBeanCollectionDataSource) parameters.get("tasks");
		if (tasksDS != null) {
			List<Task> tasks = (List<Task>) tasksDS.getData();
			if (tasks != null & tasks.size() >= 1) {
				for (Task task : tasks) {
					historicalReport
						.append(resourceBundle.getString("header.tsk_desc") + ": ")
						.append(task.getDescription() + newline)
						.append(resourceBundle.getString("header.tsk_created_by") + ": ")
						.append(task.getCreatedBy() + newline)
						.append(resourceBundle.getString("header.tsk_status") + ": ")
						.append(task.getStatusString() + newline)
						.append(resourceBundle.getString("header.tsk_priority") + ": ")
						.append(task.getPriorityString() + newline)
						.append(resourceBundle.getString("header.tsk_station") + ": ")
						.append(task.getStationString() + newline)
						.append(resourceBundle.getString("header.tsk_due_date") + ": ")
						.append(task.getDispduedate() + newline)
						.append(resourceBundle.getString("header.tsk_due_time") + ": ")
						.append(task.getDispduetime() + newline)
						.append(resourceBundle.getString("header.tsk_reminder_date") + ": ")
						.append(task.getDispreminderdate() + newline)
						.append(resourceBundle.getString("header.tsk_reminder_time") + ": ")
						.append(task.getDispremindertime() + newline)
						.append(resourceBundle.getString("header.tsk_remarks") + ": ")
						.append(task.getRemarks() + newline);
				}
			} else {
				historicalReport.append("N/A" + newline);
			}				
		} else {
			historicalReport.append("N/A" + newline);
		}
		
		//messages section
		historicalReport.append(newline);
		historicalReport.append("-- " + resourceBundle.getString("header.messages") + " --");
		historicalReport.append(newline);
		JRBeanCollectionDataSource messagesDS = (JRBeanCollectionDataSource) parameters.get("messages");
		if (messagesDS != null) {
			List<Message> messages = (List<Message>) messagesDS.getData();
			if (messages != null & messages.size() >= 1) {
				for (Message message : messages) {
					historicalReport
					.append(resourceBundle.getString("header.subject") + ": ")
					.append(message.getSubject() + newline)
					.append(resourceBundle.getString("colname.agent") + ": ")
					.append(message.getCreatedBy() + newline)
					.append(resourceBundle.getString("colname.date") + ": ")
					.append(message.getDisp_send_date() + newline)
					.append(resourceBundle.getString("header.station") + ": ")
					.append(message.getStationString() + newline)
					.append(resourceBundle.getString("header.to") + ": ")
					.append(message.getMessageTo() + newline)
					.append(resourceBundle.getString("header.message") + ": ")
					.append(message.getMessage() + newline);
				}
			} else {
				historicalReport.append("N/A" + newline);
			}
		} else {
			historicalReport.append("N/A" + newline);
		}
		
		
		String phraseToReplace = ": null" + newline;
		String newPhrase = ": " + newline;
		String result = org.apache.commons.lang.StringUtils.replace(historicalReport.toString(), phraseToReplace, newPhrase);
		//Strip out asterisks
		//result = org.apache.commons.lang.StringUtils.replace(result, "*", " ");
		
		//logger.info(">>>>capacity : " + historicalReport.capacity());
		//logger.info(result);
		
		return result;
	}

}