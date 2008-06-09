/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_articles"
 */
public class Audit_Articles implements Serializable {
	private int audit_articles_id;
	private int Articles_ID;
	private String article;
	private String description;
	private Date purchasedate;
	private double cost;
	private String currency_ID;

	private String _DATEFORMAT;
	
	private Audit_Incident audit_incident;

	/**
	 * @return Returns the audit_articles_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_articles_0"
	 *  
	 */
	public int getAudit_articles_id() {
		return audit_articles_id;
	}

	/**
	 * @param audit_articles_id
	 *          The audit_articles_id to set.
	 */
	public void setAudit_articles_id(int audit_articles_id) {
		this.audit_articles_id = audit_articles_id;
	}

	/**
	 * @return Returns the audit_incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"
	 *                        column="audit_incident_id"
	 */
	public Audit_Incident getAudit_incident() {
		return audit_incident;
	}
	/**
	 * @param audit_incident The audit_incident to set.
	 */
	public void setAudit_incident(Audit_Incident audit_incident) {
		this.audit_incident = audit_incident;
	}
	
	
	/**
	 * @return Returns the article.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getArticle() {
		return article;
	}

	/**
	 * @param article
	 *          The article to set.
	 */
	public void setArticle(String article) {
		this.article = article;
	}

	/**
	 * @return Returns the articles_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getArticles_ID() {
		return Articles_ID;
	}

	/**
	 * @param articles_ID
	 *          The articles_ID to set.
	 */
	public void setArticles_ID(int articles_ID) {
		Articles_ID = articles_ID;
	}

	/**
	 * @return Returns the cost.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *          The cost to set.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDiscost() {
		return TracingConstants.DECIMALFORMAT.format(getCost());
	}

	public void setDiscost(String s) {
		setCost(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the currency_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the purchasedate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getPurchasedate() {
		return purchasedate;
	}

	public String getDispurchasedate() {
		return DateUtils.formatDate(purchasedate, get_DATEFORMAT(), null, null);
	}

	public void setDispurchasedate(String s) {
		setPurchasedate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	/**
	 * @param purchasedate
	 *          The purchasedate to set.
	 */
	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {

		_DATEFORMAT = _dateformat;
	}

}