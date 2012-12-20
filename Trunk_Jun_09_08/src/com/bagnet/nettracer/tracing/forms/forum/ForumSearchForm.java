package com.bagnet.nettracer.tracing.forms.forum;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.forum.FsForumSearch;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThreadInfo;

public final class ForumSearchForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;

	private FsForumSearch searchInfo = new FsForumSearch();
	private List<FsForumTag> tags = new ArrayList<FsForumTag>();
	private List<FsForumThreadInfo> threads = new ArrayList<FsForumThreadInfo>();
	
	private String start = "";
	private String end = "";

	private String tag_currpage;
	private String tag_nextpage;
	private String tag_prevpage;

	private String thread_currpage;
	private String thread_nextpage;
	private String thread_prevpage;
	
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

	public FsForumSearch getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(FsForumSearch searchInfo) {
		this.searchInfo = searchInfo;
	}

	public List<FsForumTag> getTags() {
		return tags;
	}

	public void setTags(List<FsForumTag> tags) {
		this.tags = tags;
	}

	public List<FsForumThreadInfo> getThreads() {
		return threads;
	}

	public void setThreads(List<FsForumThreadInfo> threads) {
		this.threads = threads;
	}

	public String getTag_currpage() {
		return tag_currpage;
	}

	public void setTag_currpage(String tag_currpage) {
		this.tag_currpage = tag_currpage;
	}

	public String getTag_nextpage() {
		return tag_nextpage;
	}

	public void setTag_nextpage(String tag_nextpage) {
		this.tag_nextpage = tag_nextpage;
	}

	public String getTag_prevpage() {
		return tag_prevpage;
	}

	public void setTag_prevpage(String tag_prevpage) {
		this.tag_prevpage = tag_prevpage;
	}

	public String getThread_currpage() {
		return thread_currpage;
	}

	public void setThread_currpage(String thread_currpage) {
		this.thread_currpage = thread_currpage;
	}

	public String getThread_nextpage() {
		return thread_nextpage;
	}

	public void setThread_nextpage(String thread_nextpage) {
		this.thread_nextpage = thread_nextpage;
	}

	public String getThread_prevpage() {
		return thread_prevpage;
	}

	public void setThread_prevpage(String thread_prevpage) {
		this.thread_prevpage = thread_prevpage;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		searchInfo.setStartDate(DateUtils.convertToDate(start, _DATEFORMAT, null));
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
		searchInfo.setEndDate(DateUtils.convertToDate(end, _DATEFORMAT, null));
	}

}