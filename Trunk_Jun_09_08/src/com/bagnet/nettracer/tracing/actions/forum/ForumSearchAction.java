/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.transport.TransportUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.forum.FsForumSearch;
import aero.nettracer.fs.model.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v3.forum.FsForumSearchResults;
import aero.nettracer.fs.model.transport.v3.forum.FsForumThreadInfo;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.forum.ForumSearchForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class ForumSearchAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(ForumSearchAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		if(!manageToken(request)) {
			return (mapping.findForward(TracingConstants.INVALID_TOKEN));
		}
		
		if (request.getParameter("back") != null) {
			request.setAttribute("back", "1");
		}
		
		ActionMessages errors = new ActionMessages();

		ForumSearchForm fform = (ForumSearchForm) form;

		int rowsperpage = TracingConstants.FORUM_ROWS_PER_PAGE;
		int tag_currpage = 0;
		int thread_currpage = 0;
		
		tag_currpage = fform.getTag_currpage() != null ? Integer.parseInt(fform.getTag_currpage()) : 0;
		if (fform.getTag_nextpage() != null && fform.getTag_nextpage().equals("1")) {
			tag_currpage++;
		}

		if (fform.getTag_prevpage() != null && fform.getTag_prevpage().equals("1")) {
			tag_currpage--;
		}
		
		thread_currpage = fform.getThread_currpage() != null ? Integer.parseInt(fform.getThread_currpage()) : 0;
		if (fform.getThread_nextpage() != null && fform.getThread_nextpage().equals("1")) {
			thread_currpage++;
		}

		if (fform.getThread_prevpage() != null && fform.getThread_prevpage().equals("1")) {
			thread_currpage--;
		}		
		
		Context ctx = null;
		ClaimClientRemote remote = null;
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception ex) {
			logger.error(ex);
		}
		
		FsForumSearchResults tagResults = null;
		FsForumSearchResults threadResults = null;
		
		if (remote == null) {
			ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			tagResults = (FsForumSearchResults) remote.getTags(tag_currpage);
			threadResults = (FsForumSearchResults) remote.getThreads(TransportMapper.map(fform.getSearchInfo()), thread_currpage);
		}
		
		if (tagResults != null && threadResults != null) {
			logger.error("GOT THREAD AND TAG RESULTS");
			int tag_totalpages = (int) Math.ceil((double) tagResults.getTotal() / (double) rowsperpage);
			int thread_totalpages = (int) Math.ceil((double) threadResults.getTotal() / (double) rowsperpage);
				
			if (tag_totalpages <= tag_currpage) {
				tag_currpage = 0;
				request.setAttribute("tag_currpage", "0");
			}
	
			if (thread_totalpages <= thread_currpage) {
				thread_currpage = 0;
				request.setAttribute("thread_currpage", "0");
			}
			
			boolean tag_end = tag_currpage + 1 == tag_totalpages && tag_totalpages > 1;
			if (tag_end) {
				request.setAttribute("tag_end", "1"); }
			
			boolean thread_end = thread_currpage + 1 == thread_totalpages && thread_totalpages > 1;
			if (thread_end) {
				request.setAttribute("thread_end", "1"); }
			
			if (tag_totalpages > 1) {
				ArrayList<String> tag_al = new ArrayList<String>();
				for (int j = 0; j < tag_totalpages; j++) {
					tag_al.add(Integer.toString(j));
				}
				request.setAttribute("tag_pages", tag_al);
			}
			
			if (thread_totalpages > 1) {
				ArrayList<String> thread_al = new ArrayList<String>();
				for (int j = 0; j < thread_totalpages; j++) {
					thread_al.add(Integer.toString(j));
				}
				request.setAttribute("thread_pages", thread_al);
			}

			if (!thread_end && threadResults.getThreads().size() == 1) {
				long id = ((FsForumThreadInfo) threadResults.getThreads().iterator().next()).getId();
				response.sendRedirect("fraud_forum_view.do?threadId=" + id);
				return null;
			}
			
			request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
			request.setAttribute("thread_currpage", "" + thread_currpage);
			request.setAttribute("tag_currpage", "" + tag_currpage);
			
			fform = createForumSearchForm(request);
			
			fform.setTag_currpage("" + tag_currpage);
			fform.setThread_currpage("" + thread_currpage);
			fform.setTags(TransportMapper.mapTags(tagResults.getTags()));
			fform.setThreads(TransportMapper.mapThreads(threadResults.getThreads()));

			request.setAttribute("tag_resultList", fform.getTags());
			request.setAttribute("thread_resultList", fform.getThreads());
		} else {
			logger.error("TAG OR THREAD ERROR");
		}
		
		/***************** end pagination *****************/
		
	    return (mapping.findForward(TracingConstants.FRAUD_FORUM_SEARCH));
	}
	
	private ForumSearchForm createForumSearchForm(HttpServletRequest request) {
		ForumSearchForm fform = null;
		try {
			HttpSession session = request.getSession();
			fform = (ForumSearchForm) session.getAttribute("forumSearchForm");

			if (fform == null) {
				fform = new ForumSearchForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			fform.set_DATEFORMAT(user.getDateformat().getFormat());
			fform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			fform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			session.setAttribute("forumSearchForm", fform);

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
		}
		return fform;
	}
	
}