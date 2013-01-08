package com.bagnet.nettracer.tracing.forms.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.forum.FsForumPost_Claim;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThread;

public final class ForumViewForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;

	private FsForumThread thread = new FsForumThread();
	private List<FsForumTag> tags = new ArrayList<FsForumTag>();
	
	private String createDateDisp = "";
	
	private String newTitle = "";
	private String newText = "";
	private String newFileID = "";
	private String newTagID = "";
	private List<FsForumPost_Claim> newFiles = new ArrayList<FsForumPost_Claim>();
	private List<FsAttachment> newAttachments = new ArrayList<FsAttachment>();
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	public FsForumThread getThread() {
		return thread;
	}

	public void setThread(FsForumThread thread) {
		this.thread = thread;
	}

	public List<FsForumTag> getTags() {
		return tags;
	}

	public void setTags(List<FsForumTag> tags) {
		this.tags = tags;
	}

	public String getCreateDateDisp() {
		createDateDisp = "";
		if (thread != null) {
			createDateDisp = DateUtils.formatDate(thread.getCreateDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}

	public void setCreateDateDisp(String createDateDisp) {
		this.createDateDisp = createDateDisp;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	public String getNewText() {
		return newText;
	}

	public void setNewText(String newText) {
		this.newText = newText;
	}

	public List<FsForumPost_Claim> getNewFiles() {
		return newFiles;
	}

	public void setNewFiles(List<FsForumPost_Claim> newFiles) {
		this.newFiles = newFiles;
	}

	public List<FsAttachment> getNewAttachments() {
		return newAttachments;
	}

	public void setNewAttachments(List<FsAttachment> newAttachments) {
		this.newAttachments = newAttachments;
	}

	public String getNewFileID() {
		return newFileID;
	}

	public void setNewFileID(String newFileID) {
		this.newFileID = newFileID;
	}

	public String getNewTagID() {
		return newTagID;
	}

	public void setNewTagID(String newTagID) {
		this.newTagID = newTagID;
	}
	
}