package aero.nettracer.fs.model.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="FsForumThread")
public class FsForumThread implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;

	@Column(length=100)
	private String title;
	
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(length=50)
	private String createAgent;

	@Column(length=2)
	private String createAirline;
	
	private boolean locked;

	private int numPosts;

	private int numFiles;

	private int numAttachments;
	
	@OneToMany(mappedBy="thread", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<FsForumPost> posts;
	
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(name = "FsForumThread_Tag", joinColumns = { @JoinColumn(name = "thread_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<FsForumTag> tags;


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

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getNumPosts() {
		return numPosts;
	}

	public void setNumPosts(int numPosts) {
		this.numPosts = numPosts;
	}

	public int getNumFiles() {
		return numFiles;
	}

	public void setNumFiles(int numFiles) {
		this.numFiles = numFiles;
	}

	public int getNumAttachments() {
		return numAttachments;
	}

	public void setNumAttachments(int numAttachments) {
		this.numAttachments = numAttachments;
	}

	public Set<FsForumPost> getPosts() {
		return posts;
	}

	public void setPosts(Set<FsForumPost> posts) {
		this.posts = posts;
	}

	public Set<FsForumTag> getTags() {
		return tags;
	}

	public void setTags(Set<FsForumTag> tags) {
		this.tags = tags;
	}

}
