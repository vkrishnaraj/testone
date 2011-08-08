package com.nettracer.claims.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;



/**
 * @author Utpal Description: This is needed for binding the Application Data
 *         with the front end
 */

public class Currency{
	
	public static List<SelectItem> currencyList=null;

	public static List<SelectItem> getCurrencies() {
		if (currencyList == null) {
			currencyList = new ArrayList<SelectItem>();
			
	//		currencyList.add(new SelectItem("USD","US Dollar"));
			currencyList.add(new SelectItem("EUR","Euro"));
			currencyList.add(new SelectItem("GBP","British Pound"));
			currencyList.add(new SelectItem("JPY","Japanese Yen"));
			currencyList.add(new SelectItem("ILS","Israeli New Shekel"));
			currencyList.add(new SelectItem("MXP","Mexican Peso"));
			currencyList.add(new SelectItem("AFA","Afghanistan Afghani"));
			currencyList.add(new SelectItem("ALL","Albanian Lek"));
			currencyList.add(new SelectItem("DZD","Algerian Dinar"));
			currencyList.add(new SelectItem("ADP","Andorran Peseta"));
			currencyList.add(new SelectItem("AON","Angolan New Kwanza"));
			currencyList.add(new SelectItem("ARS","Argentine Peso"));
			currencyList.add(new SelectItem("AWG","Aruban Florin"));
			currencyList.add(new SelectItem("AUD","Australian Dollar"));
			currencyList.add(new SelectItem("ATS","Austrian Schilling"));
			currencyList.add(new SelectItem("BSD","Bahamanian Dollar"));
			currencyList.add(new SelectItem("BHD","Bahraini Dinar"));
			currencyList.add(new SelectItem("BDT","Bangladeshi Taka"));
			currencyList.add(new SelectItem("BBD","Barbados Dollar"));
			currencyList.add(new SelectItem("BEF","Belgian Franc"));
			currencyList.add(new SelectItem("BZD","Belize Dollar"));
			currencyList.add(new SelectItem("BMD","Bermudian Dollar"));
			currencyList.add(new SelectItem("BTN","Bhutan Ngultrum"));
			currencyList.add(new SelectItem("BOB","Bolivian Boliviano"));
			currencyList.add(new SelectItem("BWP","Botswana Pula"));
			currencyList.add(new SelectItem("BRL","Brazilian Real"));	
			currencyList.add(new SelectItem("BND","Brunei Dollar"));
			currencyList.add(new SelectItem("BGL","Bulgarian Lev"));
			currencyList.add(new SelectItem("BIF","Burundi Franc"));
			currencyList.add(new SelectItem("KHR","Cambodian Riel"));
			currencyList.add(new SelectItem("CAD","Canadian Dollar"));
			currencyList.add(new SelectItem("CVE","Cape Verde Escudo"));
			currencyList.add(new SelectItem("KYD","Cayman Islands Dolla"));
			currencyList.add(new SelectItem("CFP","Central Pacific Fran"));
			currencyList.add(new SelectItem("XOF","CFA Franc BCEAO"));
			currencyList.add(new SelectItem("XAF","CFA Franc BEAC"));
			currencyList.add(new SelectItem("CLP","Chilean Peso"));
			currencyList.add(new SelectItem("CNY","Chinese Yuan Renminb"));
			currencyList.add(new SelectItem("COP","Colombian Peso"));
			currencyList.add(new SelectItem("KMF","Comoros Franc"));
			currencyList.add(new SelectItem("CRC","Costa Rican Colon"));
			currencyList.add(new SelectItem("HRK","Croatian Kuna"));
			currencyList.add(new SelectItem("CUP","Cuban Peso"));
			currencyList.add(new SelectItem("CYP","Cyprus Pound"));
			currencyList.add(new SelectItem("CSK","Czech Koruna"));
			currencyList.add(new SelectItem("DKK","Danish Krone"));
			currencyList.add(new SelectItem("DJF","Djibouti Franc"));
			currencyList.add(new SelectItem("DOP","Dominican R. Peso"));
			currencyList.add(new SelectItem("NLG","Dutch Guilder"));
			currencyList.add(new SelectItem("XCD","East Caribbean Dolla"));	
			currencyList.add(new SelectItem("XEU","ECU"));	
			currencyList.add(new SelectItem("ECS","Ecuador Sucre"));
			currencyList.add(new SelectItem("EGP","Egyptian Pound"));
			currencyList.add(new SelectItem("SVC","El Salvador Colon"));
			currencyList.add(new SelectItem("EEK","Estonian Kroon"));
			currencyList.add(new SelectItem("ETB","Ethiopian Birr"));
			currencyList.add(new SelectItem("FKP","Falkland Islands Pou"));
			currencyList.add(new SelectItem("FJD","Fiji Dollar"));
			currencyList.add(new SelectItem("FIM","Finnish Markka"));
			currencyList.add(new SelectItem("FRF","French Franc"));
			currencyList.add(new SelectItem("GMD","Gambian Dalasi"));
			currencyList.add(new SelectItem("DEM","German Mark"));	
			currencyList.add(new SelectItem("GHC","Ghanaian Cedi"));
			currencyList.add(new SelectItem("GIP","Gibraltar Pound"));
			currencyList.add(new SelectItem("XAU","Gold (oz.)"));
			currencyList.add(new SelectItem("GRD","Greek Drachma"));
			currencyList.add(new SelectItem("GTQ","Guatemalan Quetzal"));
			currencyList.add(new SelectItem("GNF","Guinea Franc"));	
			currencyList.add(new SelectItem("GYD","Guyanese Dollar"));
			currencyList.add(new SelectItem("HTG","Haitian Gourde"));
			currencyList.add(new SelectItem("HNL","Honduran Lempira"));
			currencyList.add(new SelectItem("HKD","Hong Kong Dollar"));
			currencyList.add(new SelectItem("HUF","Hungarian Forint"));
			currencyList.add(new SelectItem("ISK","Iceland Krona"));
			currencyList.add(new SelectItem("INR","Indian Rupee"));
			currencyList.add(new SelectItem("IDR","Indonesian Rupiah"));	
			currencyList.add(new SelectItem("IRR","Iranian Rial"));
			currencyList.add(new SelectItem("IQD","Iraqi Dinar"));
			currencyList.add(new SelectItem("IEP","Irish Punt"));
			currencyList.add(new SelectItem("ITL","Italian Lira"));
			currencyList.add(new SelectItem("JMD","Jamaican Dollar"));
			currencyList.add(new SelectItem("JOD","Jordanian Dinar"));
			currencyList.add(new SelectItem("KZT","Kazakhstan Tenge"));
			currencyList.add(new SelectItem("KES","Kenyan Shilling"));
			currencyList.add(new SelectItem("KWD","Kuwaiti Dinar"));
			currencyList.add(new SelectItem("LAK","Lao Kip"));
			currencyList.add(new SelectItem("LVL","Latvian Lats"));
			currencyList.add(new SelectItem("LBP","Lebanese Pound"));
			currencyList.add(new SelectItem("LSL","Lesotho Loti"));
			currencyList.add(new SelectItem("LRD","Liberian Dollar"));
			currencyList.add(new SelectItem("LYD","Libyan Dinar"));
			currencyList.add(new SelectItem("LTL","Lithuanian Litas"));
			currencyList.add(new SelectItem("LUF","Luxembourg Franc"));
			currencyList.add(new SelectItem("MOP","Macau Pataca"));
			currencyList.add(new SelectItem("MGF","Malagasy Franc"));
			currencyList.add(new SelectItem("MWK","Malawi Kwacha"));
			currencyList.add(new SelectItem("MYR","Malaysian Ringgit"));
			currencyList.add(new SelectItem("MVR","Maldive Rufiyaa"));
			currencyList.add(new SelectItem("MTL","Maltese Lira"));
			currencyList.add(new SelectItem("MRO","Mauritanian Ouguiya"));
			currencyList.add(new SelectItem("MUR","Mauritius Rupee"));
			currencyList.add(new SelectItem("MNT","Mongolian Tugrik"));
			currencyList.add(new SelectItem("MAD","Moroccan Dirham"));
			currencyList.add(new SelectItem("MZM","Mozambique Metical"));
			currencyList.add(new SelectItem("MMK","Myanmar Kyat"));
			currencyList.add(new SelectItem("NAD","Namibia Dollar"));
			currencyList.add(new SelectItem("NPR","Nepalese Rupee"));
			currencyList.add(new SelectItem("NZD","New Zealand Dollar"));
			currencyList.add(new SelectItem("NIO","Nicaraguan Cordoba O"));
			currencyList.add(new SelectItem("NGN","Nigerian Naira"));
			currencyList.add(new SelectItem("ANG","NL Antillian Guilder"));
			currencyList.add(new SelectItem("KPW","North Korean Won"));
			currencyList.add(new SelectItem("NOK","Norwegian Kroner"));
			currencyList.add(new SelectItem("OMR","Omani Rial"));
			currencyList.add(new SelectItem("PKR","Pakistan Rupee"));
			currencyList.add(new SelectItem("XPD","Palladium (oz.)"));
			currencyList.add(new SelectItem("PAB","Panamanian Balboa"));
			currencyList.add(new SelectItem("PGK","Papua New Guinea Kin"));
			currencyList.add(new SelectItem("PYG","Paraguay Guarani"));
			currencyList.add(new SelectItem("PEN","Peruvian Nuevo Sol"));	
			currencyList.add(new SelectItem("PHP","Philippine Peso"));
			currencyList.add(new SelectItem("XPT","Platinum (oz.)"));	
			currencyList.add(new SelectItem("PLZ","Polish Zloty"));
			currencyList.add(new SelectItem("PTE","Portuguese Escudo"));	
			currencyList.add(new SelectItem("QAR","Qatari Rial"));
			currencyList.add(new SelectItem("ROL","Romanian Leu"));
			currencyList.add(new SelectItem("RUB","Russian Rouble"));
			currencyList.add(new SelectItem("WST","Samoan Tala"));
			currencyList.add(new SelectItem("STD","Sao Tome/Principe Do"));		
			currencyList.add(new SelectItem("SAR","Saudi Riyal"));	
			currencyList.add(new SelectItem("SCR","Seychelles Rupee"));	
			currencyList.add(new SelectItem("SLL","Sierra Leone Leone"));
			currencyList.add(new SelectItem("XAG","Silver (oz.)"));	
			currencyList.add(new SelectItem("SGD","Singapore Dollar"));	
			currencyList.add(new SelectItem("SKK","Slovak Koruna"));
			currencyList.add(new SelectItem("SIT","Slovenian Tolar"));
			currencyList.add(new SelectItem("SBD","Solomon Islands Doll"));
			currencyList.add(new SelectItem("SOS","Somali Shilling"));	
			currencyList.add(new SelectItem("ZAR","South African Rand"));
			currencyList.add(new SelectItem("KRW","South-Korean Won"));
			currencyList.add(new SelectItem("ESP","Spanish Peseta"));
			currencyList.add(new SelectItem("LKR","Sri Lanka Rupee"));
			currencyList.add(new SelectItem("SHP","St. Helena Pound"));
			currencyList.add(new SelectItem("SDD","Sudanese Dinar"));
			currencyList.add(new SelectItem("SDP","Sudanese Pound"));
			currencyList.add(new SelectItem("SRG","Suriname Guilder"));
			currencyList.add(new SelectItem("SZL","Swaziland Lilangeni"));
			currencyList.add(new SelectItem("SEK","Swedish Krona"));	
			currencyList.add(new SelectItem("CHF","Swiss Franc"));	
			currencyList.add(new SelectItem("SYP","Syrian Pound"));	
			currencyList.add(new SelectItem("TWD","Taiwan Dollar"));	
			currencyList.add(new SelectItem("TZS","Tanzanian Shilling"));
			currencyList.add(new SelectItem("THB","Thai Baht"));
			currencyList.add(new SelectItem("TOP","Tonga Pa'anga"));	
			currencyList.add(new SelectItem("TTD","Trinidad/Tobago Doll"));	
			currencyList.add(new SelectItem("TND","Tunisian Dinar"));
			currencyList.add(new SelectItem("TRL","Turkish Lira"));
			currencyList.add(new SelectItem("UGS","Uganda Shilling"));
			currencyList.add(new SelectItem("UAH","Ukraine Hryvnia"));
			currencyList.add(new SelectItem("UYP","Uruguayan Peso"));	
			currencyList.add(new SelectItem("AED","Utd. Arab Emir. Dirh"));
			currencyList.add(new SelectItem("VUV","Vanuatu Vatu"));	
			currencyList.add(new SelectItem("VEB","Venezuelan Bolivar"));	
			currencyList.add(new SelectItem("VND","Vietnamese Dong"));
			currencyList.add(new SelectItem("YUN","Yugoslav Dinar"));
			currencyList.add(new SelectItem("ZMK","Zambian Kwacha"));		
			currencyList.add(new SelectItem("ZWD","Zimbabwe Dollar"));
		}
		return currencyList;
	}

	
	
}
