package aero.nettracer.fs.model.forum;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="FsForumPost_Claim")
public class FsForumPost_Claim implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	FsForumPost_ClaimPK key = new FsForumPost_ClaimPK();
	
	public FsForumPost_ClaimPK getKey() {
		return key;
	}

	public void setKey(FsForumPost_ClaimPK key) {
		this.key = key;
	}
	
	public long getClaim_id() {
		return key.getClaim_id();
	}

	public void setClaim_id(long claim_id) {
		this.key.setClaim_id(claim_id);
	}

	public String getClaim_airline() {
		return key.getClaim_airline();
	}

	public void setClaim_airline(String claim_airline) {
		this.key.setClaim_airline(claim_airline);
	}

	public FsForumPost getPost() {
		return key.getPost();
	}

	public void setPost(FsForumPost post) {
		this.key.setPost(post);
	}
	
}
