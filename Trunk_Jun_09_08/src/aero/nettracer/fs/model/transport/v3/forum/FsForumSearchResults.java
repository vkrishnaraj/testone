package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;
import java.util.List;

public class FsForumSearchResults implements
		aero.nettracer.fs.model.transport.v0.forum.FsForumSearchResults,
		Serializable {

	private static final long serialVersionUID = 1L;

	private int total;
	private List<FsForumTag> tags;
	private List<FsForumThreadInfo> threads;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<FsForumTag> getTags() {
		return tags;
	}

	public void setTags(List<FsForumTag> tags) {
		this.tags = tags;
	}

	public List<FsForumThreadInfo> getThreads() {
		return threads;
	}

	public void setThreads(List<FsForumThreadInfo> threads) {
		this.threads = threads;
	}

}
