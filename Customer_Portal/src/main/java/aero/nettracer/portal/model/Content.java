package aero.nettracer.portal.model;

public class Content {

	private int male;
	private String article;
	private String color;
	private String size;
	private String brandOrDescription;
	private String storePurchased;
	private String purchasedDate;
	private Double price;
	private String currency;
	private String priceString;
	private String contentOwner;
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(String contentOwner) {
		this.contentOwner = contentOwner;
	}

	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getBrandOrDescription() {
		return brandOrDescription;
	}

	public void setBrandOrDescription(String brandOrDescription) {
		this.brandOrDescription = brandOrDescription;
	}

	public String getStorePurchased() {
		return storePurchased;
	}

	public void setStorePurchased(String storePurchased) {
		this.storePurchased = storePurchased;
	}

	public String getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMaleDisp() {
		switch(male) {
			case 1:
				return "Male";
			case 2:
				return "Female";
			case 3:
				return "Male Child";
			case 4:
				return "Infant";
			case 5:
				return "Female Child";
			default:
				return "N/A";
		}
	}

}
