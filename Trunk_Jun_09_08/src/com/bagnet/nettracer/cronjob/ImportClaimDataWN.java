package com.bagnet.nettracer.cronjob;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.general.services.GeneralServiceBean;
import au.com.bytecode.opencsv.CSVReader;

import com.bagnet.nettracer.cronjob.ImportClaimDataWN.Data;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class ImportClaimDataWN extends ImportClaimData {

	HashMap<Long,Data> claims;
	
	private static String DATE_FORMAT = "MM/dd/yyyy hh:mm:ss";
	
	/**
	 * @author Loupas
	 *
	 *	The WN data included sequence numbers that is index 3 for all their tables.
	 *	Created comparator so that we can order the data.
	 *
	 */
	public class StringArrayComparator implements Comparator<String[]>{

		@Override
		public int compare(String[] o1, String[] o2) {
			//For the WN data, the seq number is always index 3.
			return (new Integer(((String[])o1)[3])) - (new Integer(((String[])o2)[3]));
		}
		
	}
	
	/**
	 * @author Loupas
	 *
	 *  WN provided data across 5 tables.  Creating custom data object to be used in a Map inorder to group claim data
	 */
	public class Data{
		public String[] getMain() {
			return main;
		}
		public void setMain(String[] main) {
			this.main = main;
		}
		public ArrayList<String[]> getAddress() {
			return address;
		}
		public void setAddress(ArrayList<String[]> address) {
			this.address = address;
		}
		public ArrayList<String[]> getPhone() {
			return phone;
		}
		public void setPhone(ArrayList<String[]> phone) {
			this.phone = phone;
		}
		public ArrayList<String[]> getItin() {
			return itin;
		}
		public void setItin(ArrayList<String[]> itin) {
			this.itin = itin;
		}
		public ArrayList<String[]> getNames() {
			return names;
		}
		public void setNames(ArrayList<String[]> names) {
			this.names = names;
		}
		String [] main;
		ArrayList<String[]>address = new ArrayList<String[]>();
		ArrayList<String[]>phone = new ArrayList<String[]>();
		ArrayList<String[]>itin = new ArrayList<String[]>();
		ArrayList<String[]>names = new ArrayList<String[]>();
	}
	
	@Override
	protected Agent loadAgent() {
		if(agent == null){
			GeneralServiceBean bean = new GeneralServiceBean();
			agent = bean.getAgent("ntadmin", "WN");
		}
		return agent;
	}

	public static List<String[]> parseCSV(String file){
		CSVReader reader;
		try {
			//Creating reader with comma delimiter, double quote and skip first list args.
			reader = new CSVReader(new FileReader(file), ',','\"',1);
			return (List<String[]>) reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected HashMap<Long, Data>  parseData() throws Exception{
		HashMap<Long,Data> claims = new HashMap<Long,Data>();
		parseDataMain(claims);//main has to run first since it creates the claim Data objects for the hash map
		parseDataPhone(claims);
		parseDataAddress(claims);
		parseDataFlt(claims);
		parseDataName(claims);
		return claims;
	}
	
	protected void parseDataMain(HashMap<Long, Data> claims){
		List<String[]> list = parseCSV(filePath + "\\ClaimMain2013.csv");
		list.addAll(parseCSV(filePath + "\\ClaimMain2012.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimMain2011.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimMain2010.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimMain2009.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimMain2008.csv"));
		
		for(String[] s:list){
			Long claimId = new Long(s[2]);
			if(claims.containsKey(claimId)){
				//during trail runs there was no duplicate claim numbers
				System.out.println("Duplicate claim number: " + claimId);
			} else {
				Data data = new Data();
				data.setMain(s);
				claims.put(claimId, data);
			}
		}
		System.out.println(claims.size());
		
	}
	
	protected void parseDataPhone(HashMap<Long, Data> claims){
		List<String[]> list = parseCSV(filePath + "\\ClaimPhone2013.csv");
		list.addAll(parseCSV(filePath + "\\ClaimPhone2012.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimPhone2011.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimPhone2010.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimPhone2009.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimPhone2008.csv"));
		
		for(String[] s:list){
			Long claimId = new Long(s[2]);
			if(!claims.containsKey(claimId)){
				//During trail runs, there was no ophaned data
				System.out.println("Claim number does not exist for Phone: " + claimId);
			} else {
				claims.get(claimId).getPhone().add(s);
			}
		}
	}
	
	protected void parseDataAddress(HashMap<Long, Data> claims){
		List<String[]> list = parseCSV(filePath + "\\ClaimAddress2013.csv");
		list.addAll(parseCSV(filePath + "\\ClaimAddress2012.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimAddress2011.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimAddress2010.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimAddress2009.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimAddress2008.csv"));
		
		for(String[] s:list){
			Long claimId = new Long(s[2]);
			if(!claims.containsKey(claimId)){
				//During trail runs, there was no ophaned data
				System.out.println("Claim number does not exist for Address: " + claimId);
			} else {
				claims.get(claimId).getAddress().add(s);
			}
		}
	}
	
	protected void parseDataFlt(HashMap<Long, Data> claims){
		List<String[]> list = parseCSV(filePath + "\\ClaimFlt2013.csv");
		list.addAll(parseCSV(filePath + "\\ClaimFlt2012.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimFlt2011.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimFlt2010.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimFlt2009.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimFlt2008.csv"));
		
		for(String[] s:list){
			Long claimId = new Long(s[2]);
			if(!claims.containsKey(claimId)){
				//During trail runs, there was no ophaned data
				System.out.println("Claim number does not exist for FLT: " + claimId);
			} else {
				claims.get(claimId).getItin().add(s);
			}
		}
	}
	
	protected void parseDataName(HashMap<Long, Data> claims) throws Exception{
		List<String[]> list = parseCSV(filePath + "\\ClaimName2013.csv");
		list.addAll(parseCSV(filePath + "\\ClaimName2012.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimName2011.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimName2010.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimName2009.csv"));
		list.addAll(parseCSV(filePath + "\\ClaimName2008.csv"));
		
		for(String[] sdata:list){
			if(sdata[4].length() > 50){
				throw new Exception("ClaimName2008 has bad data at line 5060.  This has been manually corrected and you should not see this message.  If you do see this message, please fix this file and run process again");
			}
			Long claimId = new Long(sdata[2]);
			if(!claims.containsKey(claimId)){
				//During trail runs, there was no ophaned data
				System.out.println("Claim number does not exist for Name: " + claimId);
			} else {
				claims.get(claimId).getNames().add(sdata);
			}
		}
	}
	
	protected Claim createFSClaim(Data data){
		Claim claim = ClaimUtils.createClaim(agent);
		popMain(data, claim);
		popClaimants(data, claim);
		popAddresses(data, claim);
		popPhones(data, claim);
		popItin(data, claim);
		return claim;
	}
	
	protected void popMain(Data data, Claim claim){
		String[] sdata = data.getMain();
		String airlineClaimId = sdata[2];
		String claimDate = sdata[5];
		String amountPaid = sdata[9];
		String amountClaim = sdata[10];
		String status = sdata[3];
		String ffn = sdata[20];

		claim.setAirlineClaimId(airlineClaimId);
		claim.setClaimDate(DateUtils.convertToDate(claimDate, DATE_FORMAT, null));
		
		if(amountPaid != null && !amountPaid.isEmpty()){
			String amount = amountPaid.replaceAll("[,#]", "");
			if(!amount.isEmpty()){
				claim.setAmountPaid(new Double(amount));
			}
		}
		claim.setAmountPaidCurrency("USD");
		if(amountClaim != null && !amountClaim.isEmpty()){
			String amount = amountClaim.replaceAll("[,#]", "");
			if(!amount.isEmpty()){
				claim.setAmountClaimed(new Double(amount));
			}
		}
		claim.setAmountClaimedCurrency("USD");
		
		if(status != null && !status.isEmpty()){
			Status ntstatus = new Status();
			if("DENY".equalsIgnoreCase(status)){
				ntstatus.setStatus_ID(612);//Denied
			} else if ("SETTLE".equalsIgnoreCase(status)){
				if(claim.getAmountPaid() > 0 && claim.getAmountPaid() == claim.getAmountClaimed()){
					ntstatus.setStatus_ID(611);//Paid-full
				} else if (claim.getAmountPaid() > 0 && claim.getAmountPaid() != claim.getAmountClaimed()){
					ntstatus.setStatus_ID(34);//Paid-Exclusions applied
				} else {
					ntstatus.setStatus_ID(39);//Closed
				}
			} else if ("SPECIAL HANDLING".equalsIgnoreCase(status)){
				ntstatus.setStatus_ID(32);//Resolved
			} else if ("BAGS RETURNED".equalsIgnoreCase(status)){
				ntstatus.setStatus_ID(32);//Resolved
			} else {
				ntstatus.setStatus_ID(31);//In Process (default)
			}
			claim.setStatus(ntstatus);
		}
		
		if(ffn != null && !ffn.isEmpty() && !"NONE".equalsIgnoreCase(ffn)){
			claim.getClaimant().setFfNumber(ffn);
		}
		if(claim.getClaimant().getFfNumber() != null && !claim.getClaimant().getFfNumber().isEmpty()){
			claim.getClaimant().setFfAirline("WN");
		}
		
	}
	
	private Person createNewPerson(Claim claim){
		Person p = new Person();
		claim.getClaimants().add(p);
		p.setClaim(claim);
		p.setAddress(new FsAddress());
		p.getAddress().setPerson(p);
		p.getAddress().setCountry("US");
		
		LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();
		Phone phone = new Phone();
		phone.setPerson(p);
		phone.setType(0);
		phones.add(phone);
		p.setPhones(phones);
		return p;
	}
	
	protected void popClaimants(Data data, Claim claim){
		ArrayList<String[]> list = data.getNames();
		Collections.sort(list, new StringArrayComparator());
		for(String[] sdata:list){
			String firstName = sdata[4];
			String middleName = sdata[5];
			String lastName = sdata[6];
			String ssn = sdata[7];
			
			Person p = null;
			if(new Integer(sdata[3]) == 1){
				//as part of ClaimUtils.createClaim, the claim already comes with a empty claimant.
				//If this is the first claimant we are importing for the claim, use this empty claimant object
				p = claim.getClaimant();
			} else {
				//more than one person, creating new person object with empty phone/address
				p = createNewPerson(claim);
			}
			p.setFirstName(firstName);
			p.setMiddleName(middleName);
			p.setLastName(lastName);
			if(ssn != null && !ssn.isEmpty()){
				p.setSocialSecurity(ssn);
			}
		}
		
	}
	
	protected void popAddresses(Data data, Claim claim){
		ArrayList<String[]> list = data.getAddress();
		Collections.sort(list, new StringArrayComparator());
		for(String[] sdata:list){
			String address1 = sdata[4];
			String address2 = sdata[5];
			String city = sdata[6];
			String state = sdata[7];
			String zip = sdata[8];
			String country = sdata[9];
			
			if(address1 == null || address1.isEmpty()
					|| address1.contains("*")
					|| address1.contains("REMARK")){
				continue;//do not save empty address or remarks
			}
			
			FsAddress address = null;
			for(Person p:claim.getClaimants()){
				if(p.getAddress().getAddress1() == null || p.getAddress().isEmpty()){
					address = p.getAddress();
					break;
				}
			}
			if(address == null){
//				System.out.println("More addresses exists than claimants for claim: " + claim.getAirlineClaimId() + " - " + sdata[3]);
				Person p = createNewPerson(claim);
				address = p.getAddress();
			}
			
			address.setAddress1(address1);
			address.setAddress2(address2);
			address.setCity(city);
			if(country == null || country.isEmpty() || "US".equalsIgnoreCase(country)){
				address.setState(state);
				address.setCountry("US");
			} else {
				address.setProvince(state);
				address.setCountry(country);
			}
			address.setZip(zip);
		}
	}
	
	protected void popPhones(Data data, Claim claim){
		ArrayList<String[]> list = data.getPhone();
		Collections.sort(list, new StringArrayComparator());
		HashMap<String,Object> dupPhoneMap = new HashMap<String,Object>();
		for(String[] sdata:list){
			if(sdata[4] != null && !sdata[4].isEmpty() && !dupPhoneMap.containsKey(sdata[4])){
				dupPhoneMap.put(sdata[4], null);
				Phone p = new Phone();
				p.setClaim(claim);
				p.setPhoneNumber(sdata[4]);
				//which we have the capability of capturing type for additional phones
				//these phones types are not accessible through the UI
				p.setType(Phone.HOME);
				claim.getPhones().add(p);
			}
		}
	}
	
	protected void popItin(Data data, Claim claim){
		ArrayList<String[]> list = data.getItin();
		Collections.sort(list, new StringArrayComparator());
		for(String[] sdata:list){
			Segment seg = null;
			if(new Integer(sdata[3]) == 1){
				//An empty segment is created by default, if first segment, use this empty segment
				seg = claim.getSegments().iterator().next();
			} else {
				seg = new Segment();
				claim.getSegments().add(seg);
			}
			seg.setClaim(claim);
			seg.setAirline(sdata[4]);
			seg.setFlight(sdata[5]);
			seg.setDeparture(sdata[6]);
			seg.setArrival(sdata[7]);
			seg.setDate(DateUtils.convertToDate(sdata[8], DATE_FORMAT, null));

		}
	}
	
	protected boolean claimAlreadyExists(Long claimId){
		String sql = "select id from fsclaim where airline = 'WN' and airlineClaimId = :claimId";
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("claimId", claimId);
			List l = query.list();
//			if(l.size()>0)System.out.println("ID exists " + id);
			return l.size()>0;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sess.close();
		}
		return false;
	}
	
	
	@Override
	protected void importThirdPartyClaims() {
		HashMap<Long, Data> dataMap;
		try {
			dataMap = parseData();
			loadAgent();
			for(long claimId: dataMap.keySet()){
				if(!claimAlreadyExists(claimId)){
					Claim claim = createFSClaim(dataMap.get(claimId));
					saveClaim(claim);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void deleteEmptyNames(FsClaim claim) {
		ArrayList<Person> people = new ArrayList<Person>(claim.getClaimants());
		for (Person p: people) {
			if (p.isEmpty()) {
				claim.getClaimants().remove(p);
			}
		}
	}
	
	private boolean saveClaim(Claim claim){
		File file = new File();
		file.setValidatingCompanycode("WN");
		file.setClaims(new LinkedHashSet<FsClaim>());
		
		file.getClaims().add(claim);
		claim.setFile(file);
		
		file.setIncident(claim.getIncident());
		claim.getIncident().setFile(file);
		
		file.setCreateDate(new Date());
		
		
		deleteEmptyNames(claim);
		boolean firstSave = claim.getId() == 0;
		boolean claimSaved = FileDAO.saveFile(claim.getFile(), firstSave);
		if (claimSaved) {
			ClaimUtils.enterAuditClaimEntry(agent.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
//			claim = ClaimDAO.loadClaim(claim.getId());
		} else {
			System.out.println(claim.getAirlineClaimId() + " has failed to save");
			return false;//TODO log error
		}
		return true;
	}
	
	
	public static void main(String [] args){
//		ImportClaimDataWN importer = new ImportClaimDataWN();
//		importer.filePath = "C:\\claimData\\Southwest";
//		HashMap<Long, Data> dataMap;
//		try {
//			dataMap = importer.parseData();
//			importer.loadAgent();
//			for(long claimId: dataMap.keySet()){
//				List<String[]> a = dataMap.get(claimId).getNames();
//				for(String[] s:a){
//					if(s[4].length() > 50){
//						System.out.println(s[4]);
//					}
//				}
////				if(!importer.claimAlreadyExists(claimId)){
////					Claim claim = importer.createFSClaim(dataMap.get(claimId));
////					importer.saveClaim(claim);
////				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		ImportClaimDataWN importer = new ImportClaimDataWN();
		if (!importer.setVariablesFromArgs(args)) {
			System.err.println("Usage:\t" + ImportClaimDataUS.class.getSimpleName() + " [username] [password] [company] " +
					"[process(1=import, 2=third party, 3=submit to FS)] [trace active (0 or 1)] [thread count] [max return] " +
					"[queue size] [crm file path(OPTIONAL)]");
			System.err.println("Example:\t" + ImportClaimDataUS.class.getSimpleName() + " ntadmin Ladendead51! US 1 3 500 C:\\crm");
		} else {		
			importer.importClaims();
		}
		System.exit(0);
		
		
	}

}
