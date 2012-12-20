package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class FsForumThread implements aero.nettracer.fs.model.transport.v0.forum.FsForumThread, Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private Date createDate;
	private String createAgent;
	private String createAirline;
	private boolean locked;
	private int numPosts;
	private int numFiles;
	private int numAttachments;
	private Set<FsForumPost> posts;
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
