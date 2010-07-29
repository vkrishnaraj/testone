package aero.nettracer.selfservice.claims;

import java.util.Calendar;

public class Contents {
	private int id;
	private String article;
	private int howMany;
	private String labelBrandSizeColorMaterial;
	private Gender gender;
	private String purchasedFrom;
	private Calendar datePurchased;
	private float originalCost;
	private String currencyType;
	private String file;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public int getHowMany() {
		return howMany;
	}
	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}
	public String getLabelBrandSizeColorMaterial() {
		return labelBrandSizeColorMaterial;
	}
	public void setLabelBrandSizeColorMaterial(String labelBrandSizeColorMaterial) {
		this.labelBrandSizeColorMaterial = labelBrandSizeColorMaterial;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getPurchasedFrom() {
		return purchasedFrom;
	}
	public void setPurchasedFrom(String purchasedFrom) {
		this.purchasedFrom = purchasedFrom;
	}
	public Calendar getDatePurchased() {
		return datePurchased;
	}
	public void setDatePurchased(Calendar datePurchased) {
		this.datePurchased = datePurchased;
	}
	public float getOriginalCost() {
		return originalCost;
	}
	public void setOriginalCost(float originalCost) {
		this.originalCost = originalCost;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
