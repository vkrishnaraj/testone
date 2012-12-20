package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.forum.FsForumPost;

@Entity
@Proxy(lazy = false)
public class FsAttachment implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue
	private int id;
	private String path;
	private String description;
	private String compCode;

	@ManyToOne
	@JoinColumn(name="claim_id")
	private FsClaim claim;
	
    @ManyToOne
    @JoinColumn(name="post_id")
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
	
	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public FsForumPost getPost() {
		return post;
	}

	public void setPost(FsForumPost post) {
		this.post = post;
	}

}

