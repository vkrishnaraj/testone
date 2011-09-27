package com.bagnet.nettracer.cronjob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.struts.action.ActionMessages;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ImportClaimDataUS extends ImportClaimData {
	
//	private static Logger logger = Logger.getLogger(ImportClaimDataUS.class);
	
	private final int KEY = 0;
	private final int VALUE = 1;
	
	private final int CLAIM_AMOUNT_CLAIMED = 1;
	private final int CLAIM_AMOUNT_COMPENSATED = 2;
	private final int CLAIM_INCIDENT_TYPE = 3;
	private final int CLAIM_INCIDENT_ID = 4;
	private final int CLAIM_DATE_OF_OCCURRENCE = 5;
	private final int CLAIM_STATION = 6;
	private final int CLAIM_STATUS = 7;
	private final int CLAIM_ITEM_TYPE = 9;
	private final int CLAIM_CRM_ID = 10;
	private final int CLAIM_LAST_NAME = 11;
	private final int CLAIM_FIRST_NAME = 12;
	private final int CLAIM_MIDDLE_NAME = 13;
	private final int CLAIM_ADDRESS1 = 14;
	private final int CLAIM_ADDRESS2 = 15;
	private final int CLAIM_CITY = 17;
	private final int CLAIM_STATE = 18;
	private final int CLAIM_ZIP = 19;
	private final int CLAIM_COUNTRY = 20;
	private final int CLAIM_EMAIL = 23;
	private final int CLAIM_BUS_PHONE = 27;
	private final int CLAIM_HOME_PHONE = 28; 
	private final int CLAIM_MOBILE_PHONE = 29;
	private final int CLAIM_FAX_PHONE = 30;
	private final int CLAIM_PAGER_PHONE = 31;
	
	private final int RELATED_CREATED_ON = 2;
	private final int RELATED_CRM_ID = 3;
	private final int RELATED_LAST_NAME = 4;
	private final int RELATED_FIRST_NAME = 5;
	private final int RELATED_MIDDLE_NAME = 6;
	private final int RELATED_ADDRESS1 = 7;
	private final int RELATED_ADDRESS2 = 8;
	private final int RELATED_PROVINCE = 9;
	private final int RELATED_CITY = 10;
	private final int RELATED_STATE = 11;
	private final int RELATED_ZIP = 12;
	private final int RELATED_COUNTRY = 13;
	private final int RELATED_MEMBERSHIP_NUMBER = 14;
	private final int RELATED_MEMBERSHIP_LEVEL = 15;
	private final int RELATED_EMAIL = 16;
	private final int RELATED_BUS_PHONE = 20;
	private final int RELATED_HOME_PHONE = 21;
	private final int RELATED_MOBILE_PHONE = 22;
	private final int RELATED_FAX_PHONE = 23;
	private final int RELATED_PAGER_PHONE = 24;
	
	private final String INCIDENT_ID_REGEX = "[a-zA-Z]{5}[0-9]+?";
	
	private LinkedHashMap<String, String> countryList;
	private LinkedHashMap<String, Integer> statusList;
	private LinkedHashMap<String, String> crmIncidentIds;
	private LinkedHashMap<String, ArrayList<String[]>> importedClaims;
	private LinkedHashMap<String, ArrayList<String[]>> relatedPassengers;
	private Vector<String> processedClaims;
	private BufferedWriter processedClaimsFile;
	
	public ImportClaimDataUS() {
		// TODO: change ntUser to true before deploying and running
		ntUser = false;
		importedClaims = new LinkedHashMap<String, ArrayList<String[]>>();
		relatedPassengers = new LinkedHashMap<String, ArrayList<String[]>>();
	}

	@Override
	protected Agent loadAgent() {
		return SecurityUtils.authUser("ntadmin", "Ladendead51!", "US",
				0, new ActionMessages());
	}

	@Override
	protected void importThirdPartyClaims() {
		loadCountryList();
		loadStatusList();
		
		System.out.print("Loading CRM table data...");
		loadCrmIncidentIds();
		System.out.println("\tdone!\t" + crmIncidentIds.size() + " rows loaded.");
		try {
			loadProcessedClaimsFromFile();
			readThirdPartyDataFiles();
			openProcessedClaimsFile();
			createClaimsFromCrmData();
			closeProcessedClaimsFile();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void loadCountryList() {
		countryList = new LinkedHashMap<String, String>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select country,countrycode_ID from countrycode order by country;";
			SQLQuery query = session.createSQLQuery(sql);
			List rows = query.list();
			
			Object[] row;
			String country;
			String countryCodeId;
			for (int i = 0; i < rows.size(); ++i) {
				row = (Object[]) rows.get(i);
				country = (String) row[KEY];
				countryCodeId = (String) row[VALUE];
				countryList.put(country, countryCodeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void loadStatusList() {
		statusList = new LinkedHashMap<String, Integer>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select description,Status_ID from status where table_ID = 7;";
			SQLQuery query = session.createSQLQuery(sql);
			List rows = query.list();
			
			Object[] row;
			String description;
			Integer status_ID;
			for (int i = 0; i < rows.size(); ++i) {
				row = (Object[]) rows.get(i);
				description = (String) row[KEY];
				status_ID = (Integer) row[VALUE];
				statusList.put(description, status_ID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void loadCrmIncidentIds() {
		crmIncidentIds = new LinkedHashMap<String, String>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select crm_key,incident_id from crm where crm_key is not null;";
			SQLQuery query = session.createSQLQuery(sql);
			List rows = query.list();
			
			Object[] row;
			String crmKey;
			String incidentId;
			for (int i = 0; i < rows.size(); ++i) {
				row = (Object[]) rows.get(i);
				crmKey = (String) row[KEY];
				incidentId = (String) row[VALUE];
				crmIncidentIds.put(crmKey, incidentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
			
			if (crmIncidentIds == null) {
				crmIncidentIds = new LinkedHashMap<String, String>();
			}
		}
	}

	private void readThirdPartyDataFiles() throws IOException {
		System.out.print("Reading CRM data file...");
		readDataFromFile("..\\..\\clients\\us\\crm\\crm_claims.csv", importedClaims, CLAIM_CRM_ID);
		System.out.println("\tdone!\t" + importedClaims.size() + " claims read.");
		
		System.out.print("Reading CRM passengers file...");
		readDataFromFile("..\\..\\clients\\us\\crm\\crm_related_passengers.csv", relatedPassengers, RELATED_CRM_ID);
		System.out.println("\tdone!\t" + relatedPassengers.size() + " CRM passengers read.");
	}
	
	private void readDataFromFile(String fileName, LinkedHashMap<String, ArrayList<String[]>> map, int keyIndex) throws IOException {
		BufferedReader br = null;
		InputStream in = ImportClaimDataUS.class.getResourceAsStream(fileName);
		if (in != null) {
			
			br = new BufferedReader(new InputStreamReader(in));

			String line = br.readLine();
			String[] array = null;
			String key;
			while ((line = br.readLine()) != null) {
				array = line.split(",");
				key = array[keyIndex];
				
				if (key != null && !key.isEmpty() && !key.contains("~")) {
				
					if (map.containsKey(key)) {
						map.get(key).add(array);
					} else {
						ArrayList<String[]> list = new ArrayList<String[]>();
						list.add(array);
						map.put(key, list);
					}
					
				}
				
			}
			
		}	
		
		if (br != null) {
			br.close();
		}
		in.close();

	}
	
	private void createClaimsFromCrmData() throws IOException {
		FsClaim claim;
		
		System.out.println("\nCreating claims...");
		for (String key: importedClaims.keySet()) {
			if (!processedClaims.contains(key)) {
				// CODEREVIEW: CAPTURE AND OUTPUT EXCEPTIONS
				System.out.println("Processing claim: " + key);
				double start = System.currentTimeMillis();

				claim = createClaimFromCrmData(crmIncidentIds.get(key), importedClaims.get(key));
				claim = createRelatedPassengerData(claim, relatedPassengers.get(key));
//				FileDAO.saveFile(claim.getFile(), true);
				
				writeToProcessedClaimsFile(key);
				double end = System.currentTimeMillis();
				double duration = (end - start) / 1000;
				System.out.println("Done! Claim processed in: " + df.format(duration) + " seconds.\n");
			}
		}
	}
	
	private void loadProcessedClaimsFromFile() {
		processedClaims = new Vector<String>();
		// CODEREVIEW: Can we run out of a jar.
		InputStream in = ImportClaimDataUS.class.getResourceAsStream("..\\..\\clients\\us\\crm\\processed_claims.txt");
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			do {
				try {
					line = br.readLine();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					break;
				}
				processedClaims.add(line);
			} while (line != null);
		}
		// CODEREVIEW: CLOSE input stream
	}
	
	private void openProcessedClaimsFile() throws IOException {
		String outputFilePath = ImportClaimDataUS.class.getResource("../../clients/us/crm/processed_claims.txt").getPath().substring(1);
		processedClaimsFile = new BufferedWriter(new FileWriter(outputFilePath, true));
	}
	
	private void closeProcessedClaimsFile() throws IOException {
		processedClaimsFile.close();
	}
	
	private void writeToProcessedClaimsFile(String line) throws IOException {
		processedClaimsFile.write(line);
		processedClaimsFile.newLine();
		processedClaimsFile.flush();	
	}
	
	private FsClaim createClaimFromCrmData(String ntIncidentId, ArrayList<String[]> list) {
		String value;
		String[] data = list.remove(0);
		IncidentBMO bmo = new IncidentBMO();
		
		FsClaim toReturn;
		if (ntIncidentId == null && !data[CLAIM_INCIDENT_ID].contains("~")) {
			ntIncidentId = normalizeIncidentId(data[CLAIM_INCIDENT_ID]);
		}

		if (ntIncidentId != null) {
			toReturn = ClaimUtils.createClaim(agent, bmo.findIncidentByID(ntIncidentId));
		} else {
			toReturn = ClaimUtils.createClaim(agent);
		}
		
		value = data[CLAIM_AMOUNT_CLAIMED];
		if (!value.equals("~")) {
			toReturn.setAmountClaimed(Double.valueOf(data[CLAIM_AMOUNT_CLAIMED]));
		}

		value = data[CLAIM_AMOUNT_COMPENSATED];
		if (!value.equals("~")) {
			toReturn.setAmountPaid(Double.valueOf(data[CLAIM_AMOUNT_COMPENSATED]));
		}

		value = data[CLAIM_AMOUNT_COMPENSATED];
		if (!value.equals("~")) {
			toReturn.setClaimType(getClaimType(data[CLAIM_INCIDENT_TYPE]));
		}
		
		value = data[CLAIM_DATE_OF_OCCURRENCE];
		if (!value.equals("~")) {
			toReturn.setClaimDate(DateUtils.convertToGMTDate(value, agent.getDateformat().getFormat()));
		}
		
		Person claimant = toReturn.getClaimant();
		value = data[CLAIM_LAST_NAME];
		if (!value.equals("~")) {
			claimant.setLastName(value);
		}

		value = data[CLAIM_FIRST_NAME];
		if (!value.equals("~")) {
			claimant.setFirstName(value);
		}
		
		value = data[CLAIM_MIDDLE_NAME];
		if (!value.equals("~")) {
			claimant.setMiddleName(value);
		}
		
		value = data[CLAIM_EMAIL];
		if (!value.equals("~")) {
			claimant.setEmailAddress(value);
		}
		
		FsAddress address = claimant.getAddresses().iterator().next();
		value = data[CLAIM_ADDRESS1];
		if (!value.equals("~")) {
			address.setAddress1(value);
		}

		value = data[CLAIM_ADDRESS2];
		if (!value.equals("~")) {
			address.setAddress2(value);
		}
		
		value = data[CLAIM_CITY];
		if (!value.equals("~")) {
			address.setCity(value);
		}
		
		value = data[CLAIM_COUNTRY];
		if (!value.equals("~")) {
			value = countryList.get(value.trim());
			if (value == null) {
				address.setCountry("US");
			} else {
				address.setCountry(value);
			}
		}
		
		value = data[CLAIM_STATE];
		if (!value.equals("~")) {
			if (address.getCountry().equals("US")) {
				address.setState(value);
			} else {
				address.setProvince(value);
			}
		}
		
		value = data[CLAIM_ZIP];
		if (!value.contains("~")) {
			address.setZip(value);
		}
		
		Phone phone;
		Set<Phone> phones = new LinkedHashSet<Phone>();
		value = data[CLAIM_BUS_PHONE];
		if (!value.equals("~")) {			
			phone = createPhone(value, Phone.WORK, claimant);
			if (phone != null) {
				phones.add(phone);
			}
		}
		
		value = data[CLAIM_HOME_PHONE];
		if (!value.equals("~")) {			
			phone = createPhone(value, Phone.HOME, claimant);
			if (phone != null) {
				phones.add(phone);
			}
		}
		value = data[CLAIM_MOBILE_PHONE];
		if (!value.equals("~")) {			
			phone = createPhone(value, Phone.MOBILE, claimant);
			if (phone != null) {
				phones.add(phone);
			}
		}
		value = data[CLAIM_FAX_PHONE];
		if (!value.equals("~")) {			
			phone = createPhone(value, Phone.ALTERNATE, claimant);
			if (phone != null) {
				phones.add(phone);
			}
		}
		value = data[CLAIM_PAGER_PHONE];
		if (!value.equals("~")) {			
			phone = createPhone(value, Phone.PAGER, claimant);
			if (phone != null) {
				phones.add(phone);
			}
		}
		
		if (!phones.isEmpty()) {
			claimant.setPhones(phones);
		}
		
		value = data[CLAIM_STATUS];
		if (!value.equals("~")) {
			toReturn.setStatusId(getStatusIdFromString(value));
		} else {
			toReturn.setStatusId(statusList.get("Closed"));
		}
		
		return toReturn;
	}
	
	private FsClaim createRelatedPassengerData(FsClaim claim, ArrayList<String[]> dataList) {
		if (dataList != null && !dataList.isEmpty()) {
				
			String[] data;
			String value;
			Person person;
			for (int i = 0; i < dataList.size(); ++i) {
				data = dataList.get(i);
				person = new Person();
				person.setClaim(claim);
				person.setAddresses(new LinkedHashSet<FsAddress>());
				
				value = data[RELATED_LAST_NAME];
				if (!value.equals("~")) {
					person.setLastName(value);
				}
	
				value = data[RELATED_FIRST_NAME];
				if (!value.equals("~")) {
					person.setFirstName(value);
				}
				
				value = data[RELATED_MIDDLE_NAME];
				if (!value.equals("~")) {
					person.setMiddleName(value);
				}
				
				value = data[RELATED_EMAIL];
				if (!value.equals("~")) {
					person.setEmailAddress(value);
				}
				
				value = data[RELATED_MEMBERSHIP_NUMBER];
				if (!value.equals("~")) {
					person.setFfNumber(value);
				}
				
				FsAddress address = new FsAddress();
				address.setPerson(person);
				
				value = data[RELATED_ADDRESS1];
				if (!value.equals("~")) {
					address.setAddress1(value);
				}
	
				value = data[RELATED_ADDRESS2];
				if (!value.equals("~")) {
					address.setAddress2(value);
				}
				
				value = data[RELATED_CITY];
				if (!value.equals("~")) {
					address.setCity(value);
				}
				
				value = data[RELATED_COUNTRY];
				if (!value.equals("~")) {
					value = countryList.get(value.trim());
					if (value == null) {
						address.setCountry("US");
					} else {
						address.setCountry(value);
					}
				} else {
					address.setCountry("US");
				}
				
				if (address.getCountry().equals("US")) {
					value = data[RELATED_STATE];
					if (!value.equals("~")) {
						address.setState(value);
					}
				} else {
					value = data[RELATED_PROVINCE];
					if (!value.equals("~")) {
						address.setProvince(value);
					}
				}
				
				value = data[RELATED_ZIP];
				if (!value.equals("~")) {
					address.setZip(value);
				}
				
				person.getAddresses().add(address);
				
				Phone phone;
				Set<Phone> phones = new LinkedHashSet<Phone>();
				value = data[RELATED_BUS_PHONE];
				if (!value.equals("~")) {			
					phone = createPhone(value, Phone.WORK, person);
					if (phone != null) {
						phones.add(phone);
					}
				}
				
				value = data[RELATED_HOME_PHONE];
				if (!value.equals("~")) {			
					phone = createPhone(value, Phone.HOME, person);
					if (phone != null) {
						phones.add(phone);
					}
				}
				value = data[RELATED_MOBILE_PHONE];
				if (!value.equals("~")) {			
					phone = createPhone(value, Phone.MOBILE, person);
					if (phone != null) {
						phones.add(phone);
					}
				}
				value = data[RELATED_FAX_PHONE];
				if (!value.equals("~")) {			
					phone = createPhone(value, Phone.ALTERNATE, person);
					if (phone != null) {
						phones.add(phone);
					}
				}
				value = data[RELATED_PAGER_PHONE];
				if (!value.equals("~")) {			
					phone = createPhone(value, Phone.PAGER, person);
					if (phone != null) {
						phones.add(phone);
					}
				}
				
				if (!phones.isEmpty()) {
					person.setPhones(phones);
				}
				claim.getClaimants().add(person);
			}
		}
		return claim;
	}
	
	private Phone createPhone(String phoneNumber, int phoneType, Person p) {
		Phone phone = null;
		phoneNumber = TracerUtils.normalizePhoneNumber(phoneNumber);
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			phone = new Phone();
			phone.setPerson(p);
			phone.setPhoneNumber(phoneNumber);
			phone.setType(phoneType);
		}
		
		return phone;
	}
	
	private int getClaimType(String type) {
		int toReturn = 0;
		type = type.toLowerCase();
		if (type.contains("loss")) {
			toReturn = TracingConstants.LOST_DELAY;
		} else if (type.contains("damage")) {
			toReturn = TracingConstants.DAMAGED_BAG;
		} else if (type.contains("pilferage")) {
			toReturn = TracingConstants.MISSING_ARTICLES;
		}
		return toReturn;
	}
	
	private int getStatusIdFromString(String value) {
		int statusId = -1;
		if (value != null && !value.isEmpty()) {
			value = value.toLowerCase();
			if (value.contains("denied")) {
				statusId = statusList.get("Denied");
			} else if (value.contains("paid")) {
				if (value.contains("full")) {
					statusId = statusList.get("Paid - Full");
				} else if (value.contains("liability")) {
					statusId = statusList.get("Paid - Maximum Liability");
				}
			} else if (value.contains("partial")) {
				statusId = statusList.get("Paid - Partial payment supporting documents not in order");
			}
		}
		
		if (statusId == -1) {
			statusId = statusList.get("Closed");
		}
		
		return statusId;
		
	}
	
	private String normalizeIncidentId(String incidentId) {
		if (incidentId == null) {
			return null;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(incidentId, "/&-");
		incidentId = tokenizer.nextToken();
		if (incidentId == null || incidentId.isEmpty() || !incidentId.matches(INCIDENT_ID_REGEX)) {
			return null;
		}
		
		String toReturn = incidentId.toUpperCase();
		toReturn = toReturn.replaceAll(" ", "");
		int i = 0;
		for (; i < toReturn.length(); ++i) {
			if (Character.isDigit(toReturn.charAt(i))) {
				break;
			}
		}
		
		if (i == toReturn.length()) {
			return null;
		}
		
		String left = toReturn.substring(0, i);
		String right = toReturn.substring(i);
		
		int padNum = 13 - (left.length() + right.length());
		if (padNum > 0) {
			for (int j = 0; j < padNum; ++j) {
				left += "0";
			}
		}
		
		toReturn = left + right;		
		return toReturn;
	}
	
	public static void main(String[] args) {
		new ImportClaimDataUS().importClaims();
	}

}
