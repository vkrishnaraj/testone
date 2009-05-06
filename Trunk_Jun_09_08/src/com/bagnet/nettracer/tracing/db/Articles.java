/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.ListIterator;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.CurrencyUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Articles"
 */
public class Articles implements Serializable {
	private int Articles_ID;
	private String article;
	private String description = "";
	private Date purchasedate;
	private double cost;
	private String currency_ID;
	private Incident incident;

	private String _DATEFORMAT;
	private String locale;


	/**
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *          The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID" not-null="true"
	 */
	public Incident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
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
	 * @hibernate.id generator-class="native" type="integer" column="Articles_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="articles_0"
	 * 
	 *  
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

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<ma>");
		sb.append("<Articles_ID>" + getArticles_ID() + "</Articles_ID>");
		sb.append("<article>" + getArticle() + "</article>");
		sb.append("<description>" + getDescription() + "</description>");
		sb.append("<purchasedate>" + (getPurchasedate() != null ? getPurchasedate().toString() : "")
				+ "</purchasedate>");
		sb.append("<cost>" + getCost() + "</cost>");
		sb.append("<currency_ID>" + getCurrency_ID() + "</currency_ID>");
		sb.append("<locale>" + getLocale() + "</locale>");
		sb.append("</ma>");

		return sb.toString();
	}

	public static Articles XMLtoObject(ElementNode root) {
		Articles obj = new Articles();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;


		boolean break_main = false;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Articles_ID")) {
				obj.setArticles_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("article")) {
				obj.setArticle(child.getTextContents());
			} else if (child.getType().equals("description")) {
				obj.setDescription(child.getTextContents());
			} else if (child.getType().equals("purchasedate")) {
				obj.setPurchasedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("cost")) {
				obj.setCost(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("currency_ID")) {
				obj.setCurrency_ID(child.getTextContents());
			} else if (child.getType().equals("locale")) {
				obj.setLocale(child.getTextContents());
			}

		}

		return obj;
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		if (_DATEFORMAT == null && incident != null) _DATEFORMAT = incident.getAgent().getDateformat()
				.getFormat();
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {

		_DATEFORMAT = _dateformat;
	}

	public String getCurrency() {
		String ret = "";

		if (currency_ID != null && currency_ID.length() > 0 && !currency_ID.equals("0")) {
			ret = CurrencyUtils.getCurrency(currency_ID).getDescription();
		}

		return ret;
	}
}