/*
 * Created on Jul 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Ohd_DTO {

	private String ohd_ID = "";
	private String ticketnumber = "";
	private String OHD_categorytype_ID = "";
	private String description = "";

	private String s_createtime = "";
	private String e_createtime = "";
	private String s_inventorydate = "";
	private String e_inventorydate= "";
	private String routingdate = "";
	
	private String airline = "";
	private String flightnum = "";
	private String firstname = "";
	private String middlename = "";
	private String lastname = "";
	private String station = "";

	private String status_ID = "";

	private String foundStation = "";
	private String heldStation = "";
	private String routingstation = "";

	private String foundCompany = "";
	private String heldCompany = "";

	private String color = "";
	private String type = "";
	private boolean closed;

	private String agent;
	private String wt_id;
	private boolean wtConditionOr;
	private String claimcheck;
	private String nineDigitWildcardTag;
	private String genericTag;
	private boolean intelligentTagSearch;
	private int intelligentTagSearchType;
	private String claimcheck2;
	
	private String posId;

	public void populate(OHD ohd) {

		this.setAgent(ohd.getAgent().getUsername());
		this.setOhd_ID(ohd.getOHD_ID());
		this.setTicketnumber(ohd.getClaimnum());
		this.setColor(ohd.getColor());
		this.setType(ohd.getType());
		this.setStatus_ID("" + ohd.getStatus().getStatus_ID());
		this.setStation(ohd.getFoundAtStation().getStationcode());

		this.setFoundStation(ohd.getFoundAtStation().getStationcode());
		this.setHeldStation(ohd.getHoldingStation().getStationcode());
		ArrayList a = new ArrayList(ohd.getPassengers());
		if (a != null && a.size() > 0) {
			OHD_Passenger p = (OHD_Passenger) a.get(0);
			firstname = p.getFirstname();
			middlename = p.getMiddlename();
			lastname = p.getLastname();
		}
		if (ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_OPEN) closed = false;
		else closed = true;
		this.posId = ohd.getPosId();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the oHD_categorytype_ID.
	 */
	public String getOHD_categorytype_ID() {
		return OHD_categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setOHD_categorytype_ID(String ohd_categorytype_id) {
		OHD_categorytype_ID = ohd_categorytype_id;
	}

	/**
	 * @return Returns the color.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *          The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *          The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the closed.
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed
	 *          The closed to set.
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/**
	 * @return Returns the e_createtime.
	 */
	public String getE_createtime() {
		return e_createtime;
	}

	/**
	 * @param e_createtime
	 *          The e_createtime to set.
	 */
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	/**
	 * @return Returns the firstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *          The firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 *          The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	/**
	 * @return Returns the flightnum.
	 */
	public String getFlightnum() {
		return flightnum;
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	/**
	 * @return Returns the lastname.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return Returns the middlename.
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return Returns the ohd_ID.
	 */
	public String getOhd_ID() {
		return ohd_ID;
	}

	/**
	 * @param ohd_ID
	 *          The ohd_ID to set.
	 */
	public void setOhd_ID(String ohd_ID) {
		this.ohd_ID = ohd_ID;
	}

	/**
	 * @return Returns the s_createtime.
	 */
	public String getS_createtime() {
		return s_createtime;
	}

	/**
	 * @param s_createtime
	 *          The s_createtime to set.
	 */
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}

	/**
	 * @return Returns the station.
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * @return Returns the ticketnumber.
	 */
	public String getTicketnumber() {
		return ticketnumber;
	}

	/**
	 * @param ticketnumber
	 *          The ticketnumber to set.
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("<tr>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getOhd_ID());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getTicketnumber());
		sb.append("</td>");

		/*
		 * sb .append("
		 * <td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">
		 * "); sb.append(this.getS_createtime()); sb.append(" </td> ");
		 */
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getColor());
		sb.append("</td>");

		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getType());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getStation());
		sb.append("</td>");

		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getFirstname() + " " + this.getMiddlename() + " " + this.getLastname());

		sb.append("</td>");

		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append("<A HREF=\"addOnHandBag.do?ohd_ID=" + this.getOhd_ID() + "\">Edit</a>");
		sb.append("</td>");
		if (!this.isClosed()) {
			sb
					.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
			sb.append(" <A HREF=\"forward_on_hand.do?ohd_ID=" + this.getOhd_ID() + "\">Forward</a>");
			sb.append(" </td> ");
			sb
					.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
			sb.append(" <A HREF=\"request_on_hand.do?ohd_ID=" + this.getOhd_ID() + "\">Request</a>");
			sb.append(" </td> ");
		}
		sb.append("</tr>");
		return sb.toString();
	}

	public String getHtml2() {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("<tr>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getOhd_ID());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getTicketnumber());
		sb.append("</td>");
		/*
		 * sb .append("
		 * <td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">
		 * "); sb.append(this.getS_createtime()); sb.append(" </td> ");
		 */
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getColor());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getType());
		sb.append("</td>");

		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getStation());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append(this.getFirstname() + " " + this.getMiddlename() + " " + this.getLastname());
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		sb.append("<A HREF=\"addOnHandBag.do?ohd_ID=" + this.getOhd_ID() + "\">Edit</a>");
		sb.append("</td>");
		sb
				.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
		if (!this.isClosed()) {
			sb
					.append("<td align=\"left\" width=\"100\" bgcolor=\"#EFEFEF\" class=\"grayTxt\" style=\"border-right-style: none; border-right-width: medium\">");
			sb.append(" <A HREF=\"forward_on_hand.do?ohd_ID=" + this.getOhd_ID() + "\">Forward</a>");
			sb.append(" </td> ");
		}
		sb.append("</tr>");
		return sb.toString();
	}

	/**
	 * @return Returns the foundStation.
	 */
	public String getFoundStation() {
		return foundStation;
	}

	/**
	 * @param foundStation
	 *          The foundStation to set.
	 */
	public void setFoundStation(String foundStation) {
		this.foundStation = foundStation;
	}

	/**
	 * @return Returns the heldStation.
	 */
	public String getHeldStation() {
		return heldStation;
	}

	/**
	 * @param heldStation
	 *          The heldStation to set.
	 */
	public void setHeldStation(String heldStation) {
		this.heldStation = heldStation;
	}

	/**
	 * @return Returns the status_ID.
	 */
	public String getStatus_ID() {
		return status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(String status_ID) {
		this.status_ID = status_ID;
	}

	/**
	 * @return Returns the foundCompany.
	 */
	public String getFoundCompany() {
		return foundCompany;
	}

	/**
	 * @param foundCompany
	 *          The foundCompany to set.
	 */
	public void setFoundCompany(String foundCompany) {
		this.foundCompany = foundCompany;
	}

	/**
	 * @return Returns the heldCompany.
	 */
	public String getHeldCompany() {
		return heldCompany;
	}

	/**
	 * @param heldCompany
	 *          The heldCompany to set.
	 */
	public void setHeldCompany(String heldCompany) {
		this.heldCompany = heldCompany;
	}
	
	public String getWt_id() {
		return wt_id == null ? "" : wt_id.trim();
	}

	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}

	public boolean isWtConditionOr() {
		return wtConditionOr;
	}

	public void setWtConditionOr(boolean wtConditionOr) {
		this.wtConditionOr = wtConditionOr;
	}

	
	public String getClaimcheck() {
		return claimcheck;
	}

	public void setClaimcheck(String claimcheck) {
		this.claimcheck = claimcheck;
	}

	public String getNineDigitWildcardTag() {
		return nineDigitWildcardTag;
	}

	public void setNineDigitWildcardTag(String nineDigitWildcardTag) {
		this.nineDigitWildcardTag = nineDigitWildcardTag;
	}

	public String getGenericTag() {
		return genericTag;
	}

	public void setGenericTag(String genericTag) {
		this.genericTag = genericTag;
	}

	public boolean isIntelligentTagSearch() {
		return intelligentTagSearch;
	}

	public void setIntelligentTagSearch(boolean intelligentTagSearch) {
		this.intelligentTagSearch = intelligentTagSearch;
	}

	public int getIntelligentTagSearchType() {
		return intelligentTagSearchType;
	}

	public void setIntelligentTagSearchType(int intelligentTagSearchType) {
		this.intelligentTagSearchType = intelligentTagSearchType;
	}

	public String getClaimcheck2() {
		return claimcheck2;
	}

	public void setClaimcheck2(String claimcheck2) {
		this.claimcheck2 = claimcheck2;
	}

	public String getPosId() {
		return posId != null ? posId : "";
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getS_inventorydate() {
		return s_inventorydate;
	}

	public void setS_inventorydate(String s_inventorydate) {
		this.s_inventorydate = s_inventorydate;
	}

	public String getE_inventorydate() {
		return e_inventorydate;
	}

	public void setE_inventorydate(String e_inventorydate) {
		this.e_inventorydate = e_inventorydate;
	}

	public String getRoutingstation() {
		return routingstation;
	}

	public void setRoutingstation(String routingstation) {
		this.routingstation = routingstation;
	}

	public String getRoutingdate() {
		return routingdate;
	}

	public void setRoutingdate(String routingdate) {
		this.routingdate = routingdate;
	}
	
}