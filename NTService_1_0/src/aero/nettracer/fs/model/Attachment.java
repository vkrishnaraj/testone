/*
 * Created on Nov 14, 2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Attachment implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private int attachment_id;
	private Date createDate;
	private String description;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	@Fetch(FetchMode.SELECT)
	private FsClaim claim;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getAttachment_id() {
		return attachment_id;
	}

	public void setAttachment_id(int attachment_id) {
		this.attachment_id = attachment_id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FsClaim getClaim() {
		return claim;
	}
	
	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
//
//	public Agent getAgent() {
//		return agent;
//	}
//
//	public void setAgent(Agent agent) {
//		this.agent = agent;
//	}
}

