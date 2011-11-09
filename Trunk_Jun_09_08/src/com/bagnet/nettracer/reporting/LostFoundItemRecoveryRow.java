package com.bagnet.nettracer.reporting;

import java.text.DecimalFormat;


public class LostFoundItemRecoveryRow {
	
	private String station;
	private String company;
	private int itemsReported;
	private int openItems;
	private int openNotMatched;
	private int matchedPendingAction;
	private int toBeSalvaged;
	private int closed;
	private int delivered;
	private int pickedUpByCustomer;
	private int salvaged;
	private int closedOther;
	private String returnRate;
	private String salvagedRate;
	private int closedMatchedByNt;
	
	public String getStation() {
		return station;
	}
	
	public void setStation(String station) {
		this.station = station;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public int getItemsReported() {
		return itemsReported;
	}
	
	public void setItemsReported(int itemsReported) {
		this.itemsReported = itemsReported;
	}
	
	public int getOpenItems() {
		return openItems;
	}
	
	public void setOpenItems(int openItems) {
		this.openItems = openItems;
	}
	
	public int getOpenNotMatched() {
		return openNotMatched;
	}
	
	public void setOpenNotMatched(int openNotMatched) {
		this.openNotMatched = openNotMatched;
	}
	
	public int getMatchedPendingAction() {
		return matchedPendingAction;
	}
	
	public void setMatchedPendingAction(int matchedPendingAction) {
		this.matchedPendingAction = matchedPendingAction;
	}
	
	public int getToBeSalvaged() {
		return toBeSalvaged;
	}

	public void setToBeSalvaged(int toBeSalvaged) {
		this.toBeSalvaged = toBeSalvaged;
	}

	public int getClosed() {
		return closed;
	}
	
	public void setClosed(int closed) {
		this.closed = closed;
	}
	
	public int getDelivered() {
		return delivered;
	}
	
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	
	public int getPickedUpByCustomer() {
		return pickedUpByCustomer;
	}
	
	public void setPickedUpByCustomer(int pickedUpByCustomer) {
		this.pickedUpByCustomer = pickedUpByCustomer;
	}
	
	public int getClosedOther() {
		return closedOther;
	}
	
	public void setClosedOther(int closedOther) {
		this.closedOther = closedOther;
	}
	
	public String getReturnRate() {
		return returnRate;
	}
	
	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}
	
	public String getSalvagedRate() {
		return salvagedRate;
	}

	public void setSalvagedRate(String salvagedRate) {
		this.salvagedRate = salvagedRate;
	}

	public int getClosedMatchedByNt() {
		return closedMatchedByNt;
	}
	
	public void setClosedMatchedByNt(int closedMatchedByNt) {
		this.closedMatchedByNt = closedMatchedByNt;
	}
	
	public int getSalvaged() {
		return salvaged;
	}

	public void setSalvaged(int salvaged) {
		this.salvaged = salvaged;
	}

	public void addItemsReported(int itemCount) {
		this.itemsReported += itemCount;
	}
	
	public void addOpenItems(int openItemCount) {
		this.openItems += openItemCount;
	}
	
	public void addOpenNotMatched(int openNotMatchedCount) {
		this.openNotMatched += openNotMatchedCount;
	}
	
	public void addMatchedPendingAction(int matchedPendingActionCount) {
		this.matchedPendingAction += matchedPendingActionCount;
	}
	
	public void addClosed(int closedCount) {
		this.closed += closedCount;
	}
	
	public void addDelivered(int deliveredCount) {
		this.delivered += deliveredCount;
	}
	
	public void addPickedUpByCustomer(int pickedUpByCustomerCount) {
		this.pickedUpByCustomer += pickedUpByCustomerCount;
	}
	
	public void addClosedOther(int closedOtherCount) {
		this.closedOther += closedOtherCount;
	}

	public void addSalvaged(int salvagedCount) {
		this.salvaged += salvagedCount;
	}

	public void calculateAndSetReturnRate() {
		if (closed > 0) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double del = new Double(delivered);
			Double pubc = new Double(pickedUpByCustomer);
			Double clsd = new Double(closed);
			
			String retRate = df.format(((del + pubc) / clsd) * 100);
			setReturnRate(retRate);
		} else {
			setReturnRate("0.00");
		}
	}

	public void calculateAndSetSalvagedRate() {
		if (closed > 0) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double slvgd = new Double(salvaged);
			Double clsd = new Double(closed);

			String slvgdRate = df.format((slvgd / clsd) * 100);
			setSalvagedRate(slvgdRate);
		} else {
			setSalvagedRate("0.00");
		}
	}
	
}
