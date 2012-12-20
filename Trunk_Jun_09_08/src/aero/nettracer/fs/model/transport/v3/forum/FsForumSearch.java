package aero.nettracer.fs.model.transport.v3.forum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class FsForumSearch implements aero.nettracer.fs.model.transport.v0.forum.FsForumSearch, Serializable {

	private static final long serialVersionUID = 1L;

	private String text;
	private Date startDate;
	private Date endDate;
	private String createAgent;
	private String createAirline;
	private List<String> tags;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
