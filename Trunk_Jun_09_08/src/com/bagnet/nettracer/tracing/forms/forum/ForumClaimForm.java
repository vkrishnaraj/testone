package com.bagnet.nettracer.tracing.forms.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThread;

public final class ForumClaimForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
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
	
}