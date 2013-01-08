package aero.nettracer.fs.model.forum;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class FsForumPost_ClaimPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private long claim_id;

	@Column(length=2)
	private String claim_airline;
	
	@ManyToOne
	@JoinColumn(name = "post_id", nullable=false)
    private FsForumPost post;
	
	public long getClaim_id() {
		return claim_id;
	}
	
	public void setClaim_id(long claim_id) {
		this.claim_id = claim_id;
	}
	
	public String getClaim_airline() {
		return claim_airline;
	}
	
	public void setClaim_airline(String claim_airline) {
		this.claim_airline = claim_airline;
	}
	
	public FsForumPost getPost() {
		return post;
	}
	
	public void setPost(FsForumPost post) {
		this.post = post;
	}
	
}
