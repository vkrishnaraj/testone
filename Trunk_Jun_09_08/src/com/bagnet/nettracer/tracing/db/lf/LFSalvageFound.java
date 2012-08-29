package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import aero.nettracer.lf.services.LFUtils;

import com.bagnet.nettracer.tracing.utils.DateUtils;


//@Entity
//@Table(name = "LFSalvageFound")
//@Proxy(lazy = false)
public class LFSalvageFound implements Serializable {
	
	private static final long serialVersionUID = 5806495189281178779L;

	private LFSalvage salvage;
	private String foundID;
	private String foundBarcode;
	private Date receivedDate;
	private String brand;
	private String model;
	private String serialNumber;
	private String color;
	private String description;
	private String longDescription;
	private String extendedDescription;
	private String username;
	private String currentTimeZone;
	private String salvageBoxId;
	private long category;
	private long subCategory;
	
	public LFSalvageFound(String id, String barcode, Date receivedDate2,
			String brand2, String model2, String serialNumber2, String color2,
			String description2, String longDescription2, String username2,
			String currenttimezone2, long category2, long subCategory2) {
		foundID=id;
		foundBarcode=barcode;
		receivedDate=receivedDate2;
		brand=brand2;
		model=model2;
		serialNumber=serialNumber2;
		color=color2;
		description=description2;
		longDescription=longDescription2;
		username=username2;
		currentTimeZone=currenttimezone2;
		category=category2;
		subCategory=subCategory2;
	}
	
	public LFSalvageFound()
	{
		
	}

	public LFSalvage getSalvage() {
		return salvage;
	}
	
	public void setSalvage(LFSalvage salvage) {
		this.salvage = salvage;
	}
	
	public String getFoundID() {
		return foundID;
	}
	
	public void setFoundID(String foundID) {
		this.foundID = foundID;
	}

	public String getFoundBarcode() {
		return foundBarcode;
	}
	
	public void setFoundBarcode(String foundBarcode) {
		this.foundBarcode = foundBarcode;
	}
	
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getSalvageBoxId() {
		return salvageBoxId;
	}
	
	public void setSalvageBoxId(String salvageBoxId) {
		this.salvageBoxId = salvageBoxId;
	}

	public String getModel() {
		return foundBarcode;
	}
	
	public void setModel(String modelBarcode) {
		this.model = model;
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

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongDescription() {
		return longDescription;
	}
	
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	public String getExtendedDescription() {
		return extendedDescription;
	}
	
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public long getCategory() {
		return category;
	}
	
	public void setCategory(long category) {
		this.category= category;
	}

	public long getSubcategory() {
		return subCategory;
	}
	
	public void setSubcategory(long subcategory) {
		this.subCategory= subcategory;
	}
	public String getCurrentTimeZone() {
		return currentTimeZone;
	}
	
	public void setCurrentTimeZone(String currentTimeZone) {
		this.currentTimeZone = currentTimeZone;
	}
	
	public String getDisCreatedDate(String dateFormat) {
		return DateUtils.formatDate(receivedDate, dateFormat, currentTimeZone, null);
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
}
