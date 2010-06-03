package aero.nettracer.integrations.us.scanners.data;

import java.io.Serializable;
import java.util.List;

public class Forward implements Serializable {
	private static final long serialVersionUID = 5207196137893230313L;
	private String comment;
	private String onHandId;
	private String tagNumber;
	private List<Segment> segments;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOnHandId() {
		return onHandId;
	}

	public void setOnHandId(String onHandId) {
		this.onHandId = onHandId;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}

}
