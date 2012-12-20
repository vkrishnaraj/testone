/*
 * Created on Nov 14, 2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aero.nettracer.fs.model.transport.v3;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.transport.v3.forum.FsForumPost;

@Entity
@Proxy(lazy = false)
public class FsAttachment implements aero.nettracer.fs.model.transport.v0.FsAttachment, Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	private String path;
	private String description;
	private String compCode;

	private FsClaim claim;
	private FsForumPost post;	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FsForumPost getPost() {
		return post;
	}

	public void setPost(FsForumPost post) {
		this.post = post;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

}

