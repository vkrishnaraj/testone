package aero.nettracer.fs.model.transport.v2.detection;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class MatchDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866214757924255851L;

	public static enum MatchType {
		name("name"), address("address"), 
		phone("phone"), email("email"), 
		ssn("ssn"), ffn("ffn"), 
		passport("passport"), drivers("drivers"), 
		cc("cc"), pnrloc("pnrloc"), 
		pnrdata("pnrdata"), traveldate("traveldate"), 
		ticketamount("ticketamount"), dob("dob"), 
		itin("itin");
	
		private String label;
		
		private MatchType(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}

	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.detection.MatchHistory.class)
	private MatchHistory match;
	private String content1;
	private String content2;
	private String description;
	private MatchType matchtype;
	
	@Column(name="percentMatch")
	private double percent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchHistory getMatch() {
		return match;
	}

	public void setMatch(MatchHistory match) {
		this.match = match;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	
	public void setMatchtype(MatchType matchtype) {
		this.matchtype = matchtype;
	}
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length = 20, name="matchtype" )
	public MatchType getMatchtype() {
		return matchtype;
	}
}
