/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="OHD_Passenger"
 */
public class OHD_Passenger implements Serializable {
	public int passenger_id;
	public String firstname = "";
	public String middlename = "";
	public String lastname = "";
	public int isprimary = 1;
	public Set addresses;
	
	private OHD ohd;
	
	public JRBeanCollectionDataSource getAddressesForReport() {
		if (addresses == null || addresses.size() < 1) return null;

		return new JRBeanCollectionDataSource(new ArrayList(addresses));
	}

	public String toXML() {
	
		StringBuffer sb = new StringBuffer();
		sb.append("<passenger>");
		sb.append("<Passenger_ID>" + passenger_id + "</Passenger_ID>");
		sb.append("<firstname>" + firstname + "</firstname>");
		sb.append("<middlename>" + middlename + "</middlename>");
		sb.append("<lastname>" + lastname + "</lastname>");
		sb.append("<isprimary>" + isprimary + "</isprimary>");
		sb.append("<addresses>");
		if (getAddresses() != null && getAddresses().size() > 0) {
			for (Iterator j = getAddresses().iterator(); j.hasNext();) {
				OHD_Address addr = (OHD_Address) j.next();
				sb.append(addr.toXML());
			}
		}
		sb.append("</addresses>");
		sb.append("</passenger>");
		return sb.toString();
	}
	
	
	
	public static OHD_Passenger XMLtoObject(ElementNode root) {
		OHD_Passenger obj = new OHD_Passenger();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;
		
		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Passenger_ID")) {
				obj.setPassenger_id(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("firstname")) {
				obj.setFirstname(child.getTextContents());
			} else if (child.getType().equals("middlename")) {
				obj.setMiddlename(child.getTextContents());	
			} else if (child.getType().equals("lastname")) {
				obj.setLastname(child.getTextContents());
			} else if (child.getType().equals("isprimary")) {
				obj.setIsprimary(NumberUtils.parseInt(child.getTextContents()));	
			}else if (child.getType().equals("addresses")) {
				ArrayList al = new ArrayList();
				ArrayList c = (ArrayList)child.getChildren();
				for (int z=0;z<c.size();z++) {
					al.add(OHD_Address.XMLtoObject((ElementNode)c.get(z)));
				}
				obj.setAddresses(new HashSet(al));
			}
		}

		return obj;
	}
	
	/**
	 * @hibernate.property type="integer"
	 * @return Returns the isprimary.
	 */
	public int getIsprimary() {
		return isprimary;
	}

	/**
	 * @param isprimary
	 *          The isprimary to set.
	 */
	public void setIsprimary(int isprimary) {
		this.isprimary = isprimary;
	}

	
	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="true"
	 * 
	 */
	public OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}
	
	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="Address_ID"
	 * @hibernate.key column="passenger_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Address"
	 * @return Returns the addresses.
	 */
	public Set getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *          The addresses to set.
	 */
	public void setAddresses(Set addresses) {
		this.addresses = addresses;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.id generator-class="native" type="integer" column="passenger_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_passenger_0"
	 * 
	 * @return Returns the passenger_id.
	 */
	public int getPassenger_id() {
		return passenger_id;
	}

	/**
	 * @param passenger_id
	 *          The passenger_id to set.
	 */
	public void setPassenger_id(int passenger_id) {
		this.passenger_id = passenger_id;
	}

	public OHD_Address getAddress(int i) {
		if (this.getAddresses() != null && i < this.getAddresses().size()) {
			ArrayList t = new ArrayList(this.getAddresses());
			return (OHD_Address) t.get(i);
		} else return null;
	}

	public void addAddress(OHD_Address address) {
		if (this.getAddresses() == null) this.setAddresses(new HashSet());
		this.getAddresses().add(address);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Passenger ID=" + this.getPassenger_id());
		sb.append(" Firstname=" + this.getFirstname());
		sb.append(" Middlename=" + this.getMiddlename());
		sb.append(" Lastname=" + this.getLastname());
		sb.append(" IsPrimary=" + this.getIsprimary());
		if (this.getAddresses() != null) {
			for (Iterator i = this.getAddresses().iterator(); i.hasNext();) {
				OHD_Address address = (OHD_Address) i.next();
				System.out.println(address);
			}
		}
		return sb.toString();
	}
}