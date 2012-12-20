/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions.forum;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.Attachment;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.forum.FsForumPost;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v3.forum.FsForumSearchResults;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.forum.ForumClaimForm;
import com.bagnet.nettracer.tracing.forms.forum.ForumViewForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.FileShareUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public class ForumClaimAction extends CheckedAction {
	
	private static final Logger logger = Logger.getLogger(ForumCreateAction.class);
	
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
		
		boolean isNtUser = PropertyBMO.isTrue("nt.user");
		boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
		
		if (request.getParameter("back") != null) {
			request.setAttribute("back", "1");
		}
		
		ActionMessages errors = new ActionMessages();

		ForumClaimForm fform = (ForumClaimForm) form;

		fform = createForumClaimForm(request);
		
		String claimID = null;
		
		if (request.getParameter("claimId") != null) {
			claimID = request.getParameter("claimId");
		}
		
		if (claimID == null || !claimID.matches("^\\d+$")) {
			response.sendRedirect("fraud_forum_create.do");
			return null;
		}
		
		Context ctx = null;
		ClaimClientRemote remote = null;
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception ex) {
			logger.error(ex);
		}

		if (remote == null) {
			ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			FsClaim fsClaim=null;
			fsClaim = TransportMapper.map(remote.getClaim(Long.parseLong(claimID)));
			List<FsClaim> matchClaims=new ArrayList();
			if(fsClaim!=null && fsClaim.getId() != 0)
			{	
				session.setAttribute("claimstatuslist", session
						.getAttribute("claimstatuslist") != null ? session
						.getAttribute("claimstatuslist") : TracerUtils.getStatusList(TracingConstants.TABLE_CLAIM, user.getCurrentlocale()));
					matchClaims.add(fsClaim);
				request.setAttribute("matchClaims", (List<FsClaim>)matchClaims);
			}
		}

	    return (mapping.findForward(TracingConstants.FRAUD_FORUM_CLAIM));
	}
	
	private ForumClaimForm createForumClaimForm(HttpServletRequest request) {
		ForumClaimForm fform = null;
		try {
			HttpSession session = request.getSession();
			fform = (ForumClaimForm) session.getAttribute("forumClaimForm");

			if (fform == null) {
				fform = new ForumClaimForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			fform.set_DATEFORMAT(user.getDateformat().getFormat());
			fform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			fform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			session.setAttribute("forumClaimForm", fform);

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
		}
		return fform;
	}
	
}