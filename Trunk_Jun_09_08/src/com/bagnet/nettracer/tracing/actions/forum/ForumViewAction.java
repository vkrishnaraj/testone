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
import aero.nettracer.fs.model.forum.FsForumThread;
import aero.nettracer.fs.model.transport.v3.forum.FsForumSearchResults;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.forum.ForumSearchForm;
import com.bagnet.nettracer.tracing.forms.forum.ForumViewForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.FileShareUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

import org.apache.struts.action.Action;

public class ForumViewAction extends Action {
	
	private static final Logger logger = Logger.getLogger(ForumViewAction.class);
	
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
		
		if (request.getParameter("back") != null) {
			request.setAttribute("back", "1");
		}
		
		ActionMessages errors = new ActionMessages();

		ForumViewForm fform = (ForumViewForm) form;	
		
		String threadID = null;
		
		Context ctx = null;
		ClaimClientRemote remote = null;
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception ex) {
			logger.error(ex);
		}

		//file save feature
		int itemindex = -1;
		int fileindex = -1;
		StringTokenizer stt = null;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			try {
				if (parameter.indexOf("removeAttachment") > -1) {
					stt = new StringTokenizer(parameter, "_");
					if (stt.hasMoreElements())
						stt.nextToken();
					if (stt.hasMoreElements())
						itemindex = Integer.parseInt(stt.nextToken());
				}
			} catch (Exception removephotoe) {
				// tempering with data, should never happen
			}
			if (parameter.indexOf("uploadAttachment") > -1) {
				fileindex = 0;
			}
		}

		if (remote == null) {
			ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
		} else {
			if (itemindex >= 0) {
				// don't remove photo for now.
				FsAttachment theattachment= (FsAttachment) fform.getNewAttachments().toArray()[itemindex];
				remote.deleteAttachment(theattachment.getId());
				fform.getNewAttachments().remove(theattachment);
				request.setAttribute("upload", Integer.toString(itemindex));
			    return (mapping.findForward(TracingConstants.FRAUD_FORUM_VIEW));
			} else if (fileindex >= 0) {
				// add photo
				request.setAttribute("upload", Integer.toString(fileindex));
				FsAttachment a=FileShareUtils.uploadFile(fform, "post", user, errors, remote);
				if(a!=null){
					fform.getNewAttachments().add(a);
				} else {
					ActionMessage error = new ActionMessage("error.fs.attachment.error");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			    return (mapping.findForward(TracingConstants.FRAUD_FORUM_VIEW));
			} 
		}
		
		if (request.getParameter("linkFile") != null) {
			if (fform.getNewFileID() != null && fform.getNewFileID().matches("^\\d+$")) {
				ClaimBMO bmo = new ClaimBMO();
				FsClaim newFile = bmo.findClaimByID(Long.parseLong(fform.getNewFileID()));
				if (newFile != null) {
					if (newFile.getFile().getSwapId() != 0) {
						boolean alreadyAdded = false;
						for (FsClaim claim : fform.getNewFiles()) {
							if (claim.getId() == newFile.getId()) {
								alreadyAdded = true;
								break;
							}
						}
						if (alreadyAdded) {
							ActionMessage error = new ActionMessage("error.fraud.forum.claim.exist");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							saveMessages(request, errors);
						} else {
							fform.getNewFiles().add(newFile);
						}
					} else {
						ActionMessage error = new ActionMessage("error.fraud.forum.no.fsclaim");
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						saveMessages(request, errors);
					}
				} else {
					ActionMessage error = new ActionMessage("error.fraud.forum.no.claim");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}
			fform.setNewFileID("");
		    return (mapping.findForward(TracingConstants.FRAUD_FORUM_VIEW));
		}
		
		if (request.getParameter("removeFile") != null && !request.getParameter("removeFile").isEmpty()) {
			int fileIndex = Integer.parseInt(request.getParameter("removeFile"));
			FsClaim file = (FsClaim) fform.getNewFiles().toArray()[fileIndex];
			fform.getNewFiles().remove(file);
		    return (mapping.findForward(TracingConstants.FRAUD_FORUM_VIEW));
		}
		
		if (request.getParameter("create") != null && remote != null) {
			FsForumPost newPost = new FsForumPost();
			newPost.setCreateAgent(getAgentInfo(user));
			newPost.setCreateAirline(user.getCompanycode_ID());
			newPost.setCreateDate(DateUtils.convertToGMTDate(new Date()));
			newPost.setLastEdited(DateUtils.convertToGMTDate(new Date()));
			newPost.setTitle(fform.getNewTitle());
			newPost.setText(fform.getNewText());
			fform.getThread().setLastEdited(DateUtils.convertToGMTDate(new Date()));
			fform.getThread().incrementNumPosts();
			if (fform.getNewFiles() != null && fform.getNewFiles().size() > 0) {
				fform.getThread().addNumFiles(fform.getNewFiles().size());
				newPost.setClaims(fform.getNewFiles());
			}
			if (fform.getNewAttachments() != null && fform.getNewAttachments().size() > 0) {
				fform.getThread().addNumAttachments(fform.getNewAttachments().size());
				newPost.setAttachments(fform.getNewAttachments());
				for (FsAttachment attach : newPost.getAttachments()) {
					attach.setPost(newPost);
				}
			}
			newPost.setThread(fform.getThread());
			fform.getThread().getPosts().add(newPost);
			long newThread = remote.saveThread(TransportMapper.map(fform.getThread()));
			if (newThread == -1) {
				return (mapping.findForward(TracingConstants.ERROR_MAIN));
			} else {
				fform.setNewAttachments(new ArrayList<FsAttachment>());
				fform.setNewFiles(new ArrayList<FsClaim>());
				fform.setNewText("");
				fform.setNewTitle("");
				fform.setNewFileID("");
				fform.setNewTagID("");
			}
		}
		
		fform = createForumViewForm(request);
		
		if (request.getParameter("threadId") != null) {
			threadID = request.getParameter("threadId");
			fform.setNewAttachments(new ArrayList<FsAttachment>());
			fform.setNewFiles(new ArrayList<FsClaim>());
			fform.setNewText("");
			fform.setNewTitle("");
			fform.setNewFileID("");
			fform.setNewTagID("");
		} else if (fform.getThread() != null && fform.getThread().getId() != 0) {
			threadID = "" + fform.getThread().getId();
		}
		
		if (threadID == null || !threadID.matches("^\\d+$")) {
			response.sendRedirect("fraud_forum_create.do");
			return null;
		} else {	
			
			FsForumThread thread = null;
			
			if (remote == null) {
				ActionMessage error = new ActionMessage("error.fs.could.not.communicate");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
			} else {
				thread = TransportMapper.map(remote.getThread(Long.parseLong(threadID)));
			}
			
			if (thread != null) {
				thread = setFormats(thread, fform);
				fform.setThread(thread);
			} else {
				response.sendRedirect("fraud_forum_create.do");
				return null;
			}
		}
		
	    return (mapping.findForward(TracingConstants.FRAUD_FORUM_VIEW));
	}
	
	private ForumViewForm createForumViewForm(HttpServletRequest request) {
		ForumViewForm fform = null;
		try {
			HttpSession session = request.getSession();
			fform = (ForumViewForm) session.getAttribute("forumViewForm");

			if (fform == null) {
				fform = new ForumViewForm();
			} 
	
			Agent user = (Agent) session.getAttribute("user");

			fform.set_DATEFORMAT(user.getDateformat().getFormat());
			fform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			fform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			session.setAttribute("forumViewForm", fform);

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
		}
		return fform;
	}
	
	private FsForumThread setFormats(FsForumThread thread, ForumViewForm form) {
		
		if (thread.getPosts() != null && thread.getPosts().size() > 0) {
			for (FsForumPost post:thread.getPosts()) {
				post.set_DATEFORMAT(form.get_DATEFORMAT());
				post.set_TIMEFORMAT(form.get_TIMEFORMAT());
				post.set_TIMEZONE(form.get_TIMEZONE());
			}
		}
		
		thread.set_DATEFORMAT(form.get_DATEFORMAT());
		thread.set_TIMEFORMAT(form.get_TIMEFORMAT());
		thread.set_TIMEZONE(form.get_TIMEZONE());
		
		return thread;
	}
	
	private String getAgentInfo(Agent user) {
		return user.getFirstname() + " " + user.getLastname();
	}
	
}