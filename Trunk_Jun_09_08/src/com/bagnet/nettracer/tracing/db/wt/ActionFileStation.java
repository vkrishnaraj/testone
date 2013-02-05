package com.bagnet.nettracer.tracing.db.wt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

@Entity
public class ActionFileStation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2309734754822528500L;

	private long id;

	private Date lastUpdated;

	private String companyCode;

	private String stationCode;

	private Collection<ActionFileCount> countList;

	@Column(name = "company_code", length = 2)
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "station_code", length = 3)
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	@ElementCollection(targetClass = ActionFileCount.class)
	@JoinTable(name = "actionfile_station_counts", joinColumns = @JoinColumn(name = "af_station_id"))
	public Collection<ActionFileCount> getCountList() {
		return countList;
	}

	public void setCountList(Collection<ActionFileCount> countList) {
		this.countList = countList;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean summaryLoaded(ActionFileType afType, int day, String seq) {
		for(ActionFileCount counts:countList){
			if(counts.getAf_type().equals(afType) 
					&& counts.getAf_seq().equals(seq)){
				switch (day) {
				case 1:
					return counts.isDayOneLoaded();
				case 2:
					return counts.isDayTwoLoaded();
				case 3:
					return counts.isDayThreeLoaded();
				case 4:
					return counts.isDayFourLoaded();
				case 5:
					return counts.isDayFiveLoaded();
				case 6:
					return counts.isDaySixLoaded();
				case 7:
					return counts.isDaySevenLoaded();
				default:
					return false;
				}
			}
		}
		return false;
	}
}
