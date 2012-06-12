package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import edu.emory.mathcs.backport.java.util.Collections;

@Entity
@Proxy(lazy = false)
public class LFBoxContainer implements Serializable {
	
	private static final long serialVersionUID = 5806495189281178779L;

	@Id
	@GeneratedValue
	private long id;
	
	private Date dateCount;
	
	@OneToMany(mappedBy = "container", fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private List<LFBoxCount> boxCounts=new ArrayList();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDateCount() {
		return dateCount;
	}
	
	public void setDateCount(Date dateCount) {
		this.dateCount = dateCount;
	}
	
	public String getDisDateCount(Agent a) {
		return DateUtils.formatDate(dateCount, a.getDateformat().getFormat(), a.getCurrenttimezone(), null);
	}

	public List<LFBoxCount> getBoxCounts() {
		 Collections.sort(boxCounts, new Comparator(){
			 
	            public int compare(Object o1, Object o2) {
	                LFBoxCount p1 = (LFBoxCount) o1;
	                LFBoxCount p2 = (LFBoxCount) o2;
	               return p1.getSourceName().compareToIgnoreCase(p2.getSourceName());
	            }

				 
		 });
		 return boxCounts;
	}
	
	public void setBoxCount(List<LFBoxCount> boxCounts) {
		this.boxCounts=boxCounts;
	}

	
}
