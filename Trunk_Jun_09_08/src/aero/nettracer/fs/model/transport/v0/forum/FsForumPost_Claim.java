package aero.nettracer.fs.model.transport.v0.forum;

public interface FsForumPost_Claim {
			
	public long getClaim_id();
	
	public void setClaim_id(long claim_id);
	
	public String getClaim_airline();
	
	public void setClaim_airline(String claim_airline);
	
	public FsForumPost getPost();
	
	public void setPost(FsForumPost post);
	
}
