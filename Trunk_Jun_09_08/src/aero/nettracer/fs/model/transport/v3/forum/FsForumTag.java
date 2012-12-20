package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;

public class FsForumTag implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private int numThreads;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

}
