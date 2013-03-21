package com.bagnet.nettracer.tracing.actions;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CustomerViewableCommentBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PaxCommunicationBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.PaxCommunication;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.PaxCommunication.PaxCommunicationStatus;
import com.bagnet.nettracer.tracing.db.audit.Audit_CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.EmailParser;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;

import org.apache.log4j.Logger;
import java.net.URL;
import javax.mail.internet.InternetAddress;
import com.bagnet.nettracer.email.HtmlEmail;
import org.apache.struts.util.MessageResources;
import java.util.Locale;

public class PaxCommunicationAction extends Action {
	private static Logger logger = Logger.getLogger(PaxCommunicationAction.class);
	
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) 
    		throws Exception {

		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		MessageResources messages = MessageResources
										.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		
		List<PaxCommunication> historicalPaxCommunicationsStatusNewEnteredByPax;
		Object myHistoricalNewPaxCommentListObjFromSession = session.getAttribute("historicalNewPaxCommentList");
		if (myHistoricalNewPaxCommentListObjFromSession != null) {
			historicalPaxCommunicationsStatusNewEnteredByPax = 
				(List<PaxCommunication>) session.getAttribute("historicalNewPaxCommentList");
		} else {
			historicalPaxCommunicationsStatusNewEnteredByPax = new ArrayList<PaxCommunication>();
		}
		
		//check null

		Agent user = (Agent) session.getAttribute("user");
		if(user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		String myAgentHasEnteredANewMessage = messages.getMessage(new Locale(user.getCurrentlocale()), "message.agent.has.entered.new");
		
		//sending email related config
		ServletContext sc = getServlet().getServletContext();
		String realpath = sc.getRealPath("/");
		boolean isSendEmail2PaxSuccess = true;
    	
    	DynaActionForm theForm = (DynaActionForm) form;
    	String incident_ID =  (String) theForm.get("incident_id");
    	
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Incident inc = IncidentBMO.getIncidentByID(incident_ID, sess);
			
			boolean  hasSysCompPaxCommunicationPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION, user);
			boolean  hasSysCompPaxCommunicationReadOnlyPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION_READ_ONLY, user);
			
			if(!UserPermissions.hasLinkPermission(mapping.getPath().substring(1) + ".do", user)
					&& !hasSysCompPaxCommunicationPermission && !hasSysCompPaxCommunicationReadOnlyPermission){
				return (mapping.findForward(TracingConstants.NO_PERMISSION));			
			}
	
			
	
			
			if(hasSysCompPaxCommunicationReadOnlyPermission
					&& (!hasSysCompPaxCommunicationPermission)) {
				theForm.set("readOnly", "true");
			}
			
			//find out if we will display the radio buttons
			//if YES, display radio buttons
			String isThereNewPaxComment = "NO";
			boolean isThereNewPaxCommunications = PaxCommunicationBMO.isThereNewPaxCommunications(incident_ID, sess);
			if(isThereNewPaxCommunications) {
				isThereNewPaxComment = "YES";
			}
			
			//find out if pax email is on file, if not alert agent about this
			//because it compromises this feature
			String ifPaxEmailNotOnFile = "YES";
			String myToemail = getPaxEmailAddress(inc);
			if(!myToemail.equals("")) {
				ifPaxEmailNotOnFile = "NO";
			}
			
			if (request.getParameter("save") != null) {
				
				String newAgentComment = "" + theForm.get("agentNewComment");
				newAgentComment = newAgentComment.trim();
				
				String agentActionType = "" + theForm.getString("agentActionType");
				String addNewAgentCommentSuccess = "NO";
				String acknowledgeNewPaxCommentSuccess = "NO";  //remove this later
				
				String myNewStatus = "Respond";
				//old code block starts
				//comments not empty, should save except when acknowledge
				if(!newAgentComment.equalsIgnoreCase("")) {  //insert a new row in db + set the status of historical new guest messages
					PaxCommunication newPaxCommunication = new PaxCommunication();
					newPaxCommunication.setComment(newAgentComment);
					newPaxCommunication.setIncident(inc);
					newPaxCommunication.setAgent(user);
					//newPaxCommunication.setAcknowledge_agent(user);
					newPaxCommunication.setStatus(PaxCommunicationStatus.NEW);
					java.util.Date myDate = TracerDateTime.getGMTDate();
					newPaxCommunication.setCreatedate(myDate);
					//newPaxCommunication.setAcknowledge_timestamp(myDate);
					HibernateUtils.save(newPaxCommunication, sess);
					
					// add a new entry in Remarks section
					Remark r = new Remark();
					r.setAgent(user);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					//r.setRemarktext("An agent has entered a new message.");
					r.setRemarktext(myAgentHasEnteredANewMessage);
					r.setIncident(inc);
					// Add a remark to the incident to complete the two-way reference.
					Set remarks = inc.getRemarks();
					remarks.add(r);
					//save inc and audit inc here
					HibernateUtils.save(inc, sess);  //this does not do all of what we want
					// check if audit is enabled for this company....
					Transaction transaction = sess.beginTransaction();  //persist audit incident
					Incident iDTO = inc;
					if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation()
							.getCompany().getVariable().getAudit_lost_delayed() == 1)
							|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent()
									.getStation().getCompany().getVariable().getAudit_damaged() == 1)
							|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent()
									.getStation().getCompany().getVariable().getAudit_missing_articles() == 1)) {
						Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, user);
				
						if (audit_dto != null) {
							transaction = sess.beginTransaction();
							sess.save(audit_dto);
							transaction.commit();
						}
					}
					
					addNewAgentCommentSuccess = "YES";
					//send email here
					isSendEmail2PaxSuccess = sendEmail2Pax(user, inc, realpath, newAgentComment);
					logger.warn("Was email sent successful? " + isSendEmail2PaxSuccess);
				} else {
					myNewStatus = "Acknowledge";
				}
				
				//update status for the historical guest comments with new status
				boolean isUpdateSuccess = PaxCommunicationBMO.updateNEWPaxCommunicationStatus(historicalPaxCommunicationsStatusNewEnteredByPax, myNewStatus, user);
				if(isUpdateSuccess) {
					acknowledgeNewPaxCommentSuccess = "YES";
					isThereNewPaxComment = "NO";
				}
				
				theForm.set("addNewAgentCommentSuccess", addNewAgentCommentSuccess);
			}
			
			//List<PaxCommunication> myPaxCommunications = PaxCommunicationBMO.getPaxCommunication(incident_ID, sess);
			List<PaxCommunication> myPaxCommunications = PaxCommunicationBMO.getPaxCommunication(incident_ID, null);
			
			//List<PaxCommunication> historicalPaxCommunicationsStatusNewEnteredByPax = new ArrayList<PaxCommunication>();
			// is it necessary to clear() here?
			historicalPaxCommunicationsStatusNewEnteredByPax.clear();
			
			StringBuffer sbMyPaxCommunications = new StringBuffer("");
			if(myPaxCommunications == null) {
				
			} else {
				Iterator<PaxCommunication> it = myPaxCommunications.listIterator();
				while (it.hasNext()) {
					PaxCommunication myPaxCommunication = (PaxCommunication) it.next();
					myPaxCommunication.set_DATEFORMAT(user.getDateformat().getFormat());
					myPaxCommunication.set_TIMEFORMAT(user.getTimeformat().getFormat());
					TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone()).getTimezone());
					myPaxCommunication.set_TIMEZONE(tz);
					String sEnteredBy = "Guest";
					
					String sAcknowledgedBy = "";
					Agent myAgent = myPaxCommunication.getAgent();
					if(myAgent != null) { // it must be agent comment
						sEnteredBy = myAgent.getUsername();
					} else { // it must be pax comment
						Agent myAcknowledgeAgent = myPaxCommunication.getAcknowledge_agent();
						PaxCommunicationStatus myStatus = myPaxCommunication.getStatus();
						if(myAcknowledgeAgent != null) {
							//PaxCommunicationStatus myStatus = myPaxCommunication.getStatus();
							switch(myStatus) {
								case ACKNOWLEDGED: 
									sAcknowledgedBy = "----Comment acknowledged by ";
									sAcknowledgedBy += myAcknowledgeAgent.getUsername();
									sAcknowledgedBy += " on " + myPaxCommunication.getDispAcknowledgedDate();
									break;
								case RESPONDED:
									sAcknowledgedBy = "----Comment responded to by ";
									sAcknowledgedBy += myAcknowledgeAgent.getUsername();
									sAcknowledgedBy += " on " + myPaxCommunication.getDispAcknowledgedDate();
							}
						}
						//put together the historicalPaxCommunicationsStatusNewEnteredByPax
						//we know here that this is a pax comment - next search for NEW status
						if(myStatus.equals(PaxCommunicationStatus.NEW)) {
							historicalPaxCommunicationsStatusNewEnteredByPax.add(myPaxCommunication);
						}
					}
					
					sbMyPaxCommunications.append(myPaxCommunication.getDispDate() + " - " + sEnteredBy + "\n");
					sbMyPaxCommunications.append(myPaxCommunication.getComment() + "\n");
					if(!sAcknowledgedBy.equals("")) {
						sbMyPaxCommunications.append(sAcknowledgedBy + "\n");
					}
					sbMyPaxCommunications.append("\n----------------------------------\n\n");
				}
			}
	
			// POPULATE THE FORM
			//theForm.set("incident_id", inc.getIncident_ID());
			theForm.set("incident_id", incident_ID);
			theForm.set("text", sbMyPaxCommunications.toString());
			theForm.set("agentNewComment", "");
			theForm.set("agentActionType", "Acknowledge");
			theForm.set("isThereNewPaxComment", isThereNewPaxComment);
			theForm.set("ifPaxEmailNotOnFile", ifPaxEmailNotOnFile);
			
			if (!UserPermissions.hasIncidentSavePermission(user, inc)) {
				theForm.set("readOnly", "true");
			}  	
	    	
			session.setAttribute("historicalNewPaxCommentList", historicalPaxCommunicationsStatusNewEnteredByPax);
	    	return (mapping.findForward(TracingConstants.PAX_COMMUNICATION));
		} finally {
			sess.close();
		}
    }
    
    private String getPaxEmailAddress(Incident incident) {
    	String result = "";
    	if(incident != null) {
    		List<Passenger> myPassengerList = incident.getPassenger_list();
    		Passenger pax;
    		if (myPassengerList != null && myPassengerList.size() > 0) {
    			pax = (Passenger) myPassengerList.get(0);
    			if(pax != null) {
    				Address myAddress = pax.getAddress(0);
    				if(myAddress != null) {
    					result = "" + myAddress.getEmail();
    				}
    			}
    		}  
    	}  	
    	return result;
    }
    
    private boolean sendEmail2Pax(Agent agent, Incident incident, String realpath, String newAgentComment) {
    	boolean isSendSuccess = true;
    	
    	String passname = "";
    	String languageOfChoice = "";
    	
		String configpath = realpath + "/WEB-INF/classes/";
		String imagepath = realpath + "/deployment/main/images/nettracer/";
		
		// send email to customer if sending email is set to 1
		// get email
		String toemail = null;
		List<Passenger> myPassengerList = incident.getPassenger_list();
		Passenger pax;
		if (myPassengerList != null && myPassengerList.size() > 0) {
			pax = (Passenger) myPassengerList.get(0);
			if(pax != null) {
				passname = pax.getFirstname();
				Address myAddress = pax.getAddress(0);
				if(myAddress != null) {
					toemail = myAddress.getEmail();
				}
			}
		}
		
		languageOfChoice = incident.getLanguage();   // not default language
		if (languageOfChoice == null) {
			languageOfChoice = "en";
		}
		
		if(toemail != null && toemail.length() > 0) {

			// send email
			try {
				HtmlEmail he = new HtmlEmail();
				he.setHostName(agent.getStation().getCompany().getVariable().getEmail_host());
				he.setSmtpPort(agent.getStation().getCompany().getVariable().getEmail_port());
				he.setFrom(agent.getStation().getCompany().getVariable().getEmail_from());
				
				String currentLocale = agent.getCurrentlocale();
				
				ArrayList toList = new ArrayList();
				toList.add(new InternetAddress(toemail));

				he.setTo(toList);
				String bcc = agent.getStation().getCompany().getVariable().getBlindEmail();
				if(bcc != null && bcc.trim().length() > 0) {
					List<InternetAddress> bccList = new ArrayList<InternetAddress>();
					bccList.add(new InternetAddress(bcc));
					he.setBcc(bccList);
				}
				//he.setSubject("New comment regarding your incident by an agent: " + agent.getStation().getCompany());
				he.setSubject("RE: New comment regarding incident by an agent.");

				MessageResources messages = MessageResources
						.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
				
				
				String htmlFileName = "pax_comm_email.html";
				String tmpHtmlFileName = null;
				boolean embedImage = true;
				
				HashMap h = new HashMap();
				h.put("PASS_NAME", passname);
				
			
				
				embedImage = !TracerProperties.isTrue(agent.getCompanycode_ID(),TracerProperties.EMAIL_REPORT_LD_DISABLE_IMAGE);
				//h.putAll(LostDelayReceipt.getParameters(theform, null, null, theform.getAgent(), "lostdelay.email.title"));
				he.setSubject(messages.getMessage(new Locale(languageOfChoice), "pax.communication.email.subject", messages.getMessage(new Locale(languageOfChoice), "email.mishandled")));
			
				if (tmpHtmlFileName != null) {
					htmlFileName = tmpHtmlFileName;
				}

				if (languageOfChoice != null && languageOfChoice.length() > 0) {
					if (!languageOfChoice.equalsIgnoreCase("en")) {
						htmlFileName = htmlFileName.replaceFirst("\\.html$", "_" + languageOfChoice + ".html");
					}
				}

				h.put("CLAIM_NUMBER", incident.getIncident_ID());
				h.put("NEW_MESSAGE_CONTENT", newAgentComment);
				h.put("AIRLINE", incident.getStationcreated().getCompany().getCompanydesc());

				
				// set embedded images
				if (embedImage) {
					String img1 = he.embed(new URL("file:/" + imagepath + TracingConstants.BANNER_IMAGE),
							TracingConstants.BANNER_IMAGE);
					h.put("BANNER_IMAGE", img1);
				}
					
				String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);
				
				if(msg != null) {
					he.setHtmlMsg(msg);
					he.setCharset("UTF-8");
					he.send();
				}
				else {
					isSendSuccess = false;
					logger.warn("Unable to send email because email HTML file was not parsed.");
				}

			}
			catch (Exception maile) {
				isSendSuccess = false;
				logger.error("Unable to send mail due to smtp error: ", maile);
			}

		}

    	return isSendSuccess;
    }
}
