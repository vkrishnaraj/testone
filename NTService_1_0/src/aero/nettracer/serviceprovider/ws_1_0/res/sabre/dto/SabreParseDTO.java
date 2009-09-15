package aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto;

import java.util.ArrayList;

public class SabreParseDTO {
	private ArrayList<SabreParsedBags> bags = new ArrayList<SabreParsedBags>();

	public ArrayList<SabreParsedBags> getBags() {
		return bags;
	}

	public void setBags(ArrayList<SabreParsedBags> bags) {
		this.bags = bags;
	}

	public ArrayList<SabreParsedBagItin> getItin() {
		return itin;
	}

	public void setItin(ArrayList<SabreParsedBagItin> itin) {
		this.itin = itin;
	}

	private ArrayList<SabreParsedBagItin> itin = new ArrayList<SabreParsedBagItin>();
}
