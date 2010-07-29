package aero.nettracer.selfservice.claims;


public class Baggage {
	private int id;

	private String type;
	private String color;
	private String manufacturer;
	private String purchaseDate;
	private float originalCost;
	private String currencyType;
	private String exteriorDescription;
	private String interiorDescription;
	
	
	
	private Contents[] contents;

	// JETBLUE
	private boolean didTsaInspectBagInPresense;
	private String inspectionLocation;
	private boolean noteInsertedConfirmingSearch;
	private boolean hardSided;
	private boolean softSided;
	private boolean locks;
	private boolean feet;
	private boolean wheelsRollers;
	private boolean zippers;
	private boolean pullStrap;
	private boolean retractableHandle;
	private boolean ribbons;
	private String ribbonDescription;
	private boolean trim;
	private boolean pockets;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	public String getExteriorDescription() {
		return exteriorDescription;
	}
	public void setExteriorDescription(String exteriorDescription) {
		this.exteriorDescription = exteriorDescription;
	}
	public String getInteriorDescription() {
		return interiorDescription;
	}
	public void setInteriorDescription(String interiorDescription) {
		this.interiorDescription = interiorDescription;
	}
	public boolean isDidTsaInspectBagInPresense() {
		return didTsaInspectBagInPresense;
	}
	public void setDidTsaInspectBagInPresense(boolean didTsaInspectBagInPresense) {
		this.didTsaInspectBagInPresense = didTsaInspectBagInPresense;
	}
	public String getInspectionLocation() {
		return inspectionLocation;
	}
	public void setInspectionLocation(String inspectionLocation) {
		this.inspectionLocation = inspectionLocation;
	}
	public boolean isNoteInsertedConfirmingSearch() {
		return noteInsertedConfirmingSearch;
	}
	public void setNoteInsertedConfirmingSearch(boolean noteInsertedConfirmingSearch) {
		this.noteInsertedConfirmingSearch = noteInsertedConfirmingSearch;
	}
	public boolean isHardSided() {
		return hardSided;
	}
	public void setHardSided(boolean hardSided) {
		this.hardSided = hardSided;
	}
	public boolean isSoftSided() {
		return softSided;
	}
	public void setSoftSided(boolean softSided) {
		this.softSided = softSided;
	}
	public boolean isLocks() {
		return locks;
	}
	public void setLocks(boolean locks) {
		this.locks = locks;
	}
	public boolean isFeet() {
		return feet;
	}
	public void setFeet(boolean feet) {
		this.feet = feet;
	}
	public boolean isWheelsRollers() {
		return wheelsRollers;
	}
	public void setWheelsRollers(boolean wheelsRollers) {
		this.wheelsRollers = wheelsRollers;
	}
	public boolean isZippers() {
		return zippers;
	}
	public void setZippers(boolean zippers) {
		this.zippers = zippers;
	}
	public boolean isPullStrap() {
		return pullStrap;
	}
	public void setPullStrap(boolean pullStrap) {
		this.pullStrap = pullStrap;
	}
	public boolean isRetractableHandle() {
		return retractableHandle;
	}
	public void setRetractableHandle(boolean retractableHandle) {
		this.retractableHandle = retractableHandle;
	}
	public boolean isRibbons() {
		return ribbons;
	}
	public void setRibbons(boolean ribbons) {
		this.ribbons = ribbons;
	}
	public String getRibbonDescription() {
		return ribbonDescription;
	}
	public void setRibbonDescription(String ribbonDescription) {
		this.ribbonDescription = ribbonDescription;
	}
	public boolean isTrim() {
		return trim;
	}
	public void setTrim(boolean trim) {
		this.trim = trim;
	}
	public boolean isPockets() {
		return pockets;
	}
	public void setPockets(boolean pockets) {
		this.pockets = pockets;
	}
	public void setContents(Contents[] contents) {
		this.contents = contents;
	}
	
		
}
