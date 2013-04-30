package com.bagnet.nettracer.tracing.db.lf;

import java.util.Iterator;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Proxy(lazy = false)
public class LFItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6744394382570717223L;

	@Id
	@GeneratedValue
	private long id;
	
	private String serialNumber;
	
	private String brand;
	
	private String description;
	
	private long category;
	
	private long subCategory;
	
	private String color;
	
	private String trackingNumber;
	
	private int type;
	
	private String longDescription;
	
	private int value;
	
	private float weight;
	
	private boolean deliveryRejected;
	
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getCaseColor() {
		return caseColor;
	}

	public void setCaseColor(String caseColor) {
		this.caseColor = caseColor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public LFPhone getPhone() {
		return phone;
	}

	public void setPhone(LFPhone phone) {
		this.phone = phone;
	}

	private String caseColor;
	
	private String model;
	
	private String itemCondition;
	
	private String size;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPhone.class, cascade = CascadeType.ALL)
	private LFPhone phone;
	
	@ManyToOne
	@JoinColumn(name = "disposition_status_ID", nullable = true)
	private Status disposition;
	
	@ManyToOne
	@JoinColumn(name = "lost_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFFound.class, cascade = CascadeType.ALL)
	private LFFound found;
	
	@Transient
	private boolean selected;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public long getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(long subCategory) {
		this.subCategory = subCategory;
	}

	public Status getDisposition() {
		return disposition;
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public int getDispositionId() {
		return getDisposition().getStatus_ID();
	}
	
	public void setDispositionId(int dispositionId) {
		if (disposition != null) {
			getDisposition().setStatus_ID(dispositionId);
		}
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public LFFound getFound() {
		return found;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getExtendedSummaryDesc() {
		String toReturn = getSummaryDesc() + ", ";
		
		if (description != null && !description.isEmpty()) {
			toReturn += description + ",";
		}
		
		if (longDescription != null && !longDescription.isEmpty()) {
			toReturn += longDescription + ",";
		}
		toReturn = toReturn.substring(0, toReturn.lastIndexOf(','));
		return toReturn;
	}
	
	public String getSummaryDesc() {
		String toReturn = getCatDesc() + ",\n";
		
		if (brand != null && !brand.isEmpty()) {
			toReturn += brand + ",\n";
		}
		
		if (model != null && !model.isEmpty()) {
			toReturn += model + ",\n";
		}

		if (serialNumber != null && !serialNumber.isEmpty()) {
			toReturn += serialNumber + ",\n";
		}

		if (color != null && !color.isEmpty()) {
			toReturn += color + ",\n";
		}
		toReturn = toReturn.substring(0, toReturn.lastIndexOf(",\n"));
		return toReturn;
	}
	
	private String getCatDesc() {
		String toReturn = "";
		LFCategory cat = LFUtils.loadCategory(category);
		if (cat != null) {
			toReturn = cat.getDescription();
			if (subCategory > 0) {
				Iterator<LFSubCategory> it = cat.getSubcategories().iterator();
				while (it.hasNext()) {
					LFSubCategory subcat = it.next();
					if (subcat.getId() == subCategory) {
						toReturn += ",\n" + subcat.getDescription();
						break;
					}
				}
			}
		}
		return toReturn;
	}
	
	public String getDispPhone() {
		if (phone != null && phone.getDecryptedPhoneNumber() != null && phone.getDecryptedPhoneNumber()!="") {
			return phone.getDecryptedPhoneNumber();
		}
		return "";
	}
	
	public void setDispPhone(String dispPhone) {
		if (dispPhone != null && !dispPhone.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setDecryptedPhoneNumber(dispPhone);
		}
	}
	
	public String getDispCountry() {
		if (phone != null && phone.getDecryptedCountry() != null && phone.getDecryptedCountry()!="") {
			return phone.getDecryptedCountry();
		}
		return "";
	}
	
	public void setDispCountry(String dispCountry) {
		if (dispCountry != null && !dispCountry.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setDecryptedCountry(dispCountry);
		}
	}
	
	public String getDispArea() {
		if (phone != null && phone.getDecryptedArea() != null && phone.getDecryptedArea()!="") {
			return phone.getDecryptedArea();
		}
		return "";
	}
	
	public void setDispArea(String dispArea) {
		if (dispArea != null && !dispArea.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setDecryptedArea(dispArea);
		}
	}
	
	public String getDispExchange() {
		if (phone != null && phone.getDecryptedExchange() != null && phone.getDecryptedExchange()!="") {
			return phone.getDecryptedExchange();
		}
		return "";
	}
	
	public void setDispExchange(String dispExchange) {
		if (dispExchange != null && !dispExchange.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setDecryptedExchange(dispExchange);
		}
	}
	
	public String getDispLine() {
		if (phone != null && phone.getDecryptedLine() != null && phone.getDecryptedLine()!="") {
			return phone.getDecryptedLine();
		}
		return "";
	}
	
	public void setDispLine(String dispLine) {
		if (dispLine != null && !dispLine.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setDecryptedLine(dispLine);
		}
	}
	
	public String getDispExtension() {
		if (phone != null && phone.getExtension() != null && phone.getExtension()!="") {
			return phone.getExtension();
		}
		return "";
	}
	
	public void setDispExtension(String dispExtension) {
		if (dispExtension != null && !dispExtension.trim().equals("")) {
			if (phone == null) {
				phone = new LFPhone();
			}
			phone.setExtension(dispExtension);
		}
	}

	public boolean getDeliveryRejected() {
		return deliveryRejected;
	}

	public void setDeliveryRejected(boolean deliveryRejected) {
		this.deliveryRejected = deliveryRejected;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}
