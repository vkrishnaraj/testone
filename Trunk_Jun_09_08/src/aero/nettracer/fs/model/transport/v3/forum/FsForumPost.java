package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import aero.nettracer.fs.model.transport.v3.FsAttachment;
import aero.nettracer.fs.model.transport.v3.FsClaim;

public class FsForumPost implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private String text;
	private Date createDate;
	private String createAgent;
	private String createAirline;
	private Date lastEdited;
	private FsForumPost parent;
	private FsForumThread thread;
	private List<FsClaim> claims;
	private List<FsAttachment> attachments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateAgent() {
		return createAgent;
	}

	public void setCreateAgent(String createAgent) {
		this.createAgent = createAgent;
	}

	public String getCreateAirline() {
		return createAirline;
	}

	public void setCreateAirline(String createAirline) {
		this.createAirline = createAirline;
	}

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}

	public FsForumPost getParent() {
		return parent;
	}

	public void setParent(FsForumPost parent) {
		this.parent = parent;
	}

	public FsForumThread getThread() {
		return thread;
	}

	public void setThread(FsForumThread thread) {
		this.thread = thread;
	}

	public List<FsClaim> getClaims() {
		return claims;
	}

	public void setClaims(List<FsClaim> claims) {
		this.claims = claims;
	}

	public List<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FsAttachment> attachments) {
		this.attachments = attachments;
	}

}
