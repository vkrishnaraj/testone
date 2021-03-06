package aero.nettracer.fs.model.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.FsAttachment;

@Entity
@Proxy(lazy = false)
@Table(name="FsForumPost")
public class FsForumPost implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;

	@Column(length=100)
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String text;

	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(length=50)
	private String createAgent;

	@Column(length=2)
	private String createAirline;

	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEdited;

    @ManyToOne
    @JoinColumn(name="parent_id")
	private FsForumPost parent;
	
    @ManyToOne
    @JoinColumn(name="thread_id")
	private FsForumThread thread;

	@OneToMany(mappedBy="key.post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "claim_id")
	@Fetch(FetchMode.SELECT)
    private List<FsForumPost_Claim> claims;

	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
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

	public List<FsForumPost_Claim> getClaims() {
		return claims;
	}

	public void setClaims(List<FsForumPost_Claim> claims) {
		this.claims = claims;
	}

	public List<FsAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FsAttachment> attachments) {
		this.attachments = attachments;
	}

}
