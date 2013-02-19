package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;

public class FsForumPost_Claim implements aero.nettracer.fs.model.transport.v0.forum.FsForumPost_Claim, Serializable{

	private static final long serialVersionUID = 1L;
		
	private long claim_id;

	private String claim_airline;
	
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
	
	public void setPost(aero.nettracer.fs.model.transport.v0.forum.FsForumPost post) {
		this.post = (FsForumPost) post;
	}
	
}