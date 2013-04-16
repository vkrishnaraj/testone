package com.bagnet.nettracer.cronjob;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.htmlparser.Remark;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Bag;
import aero.nettracer.general.services.GeneralServiceBean;
import au.com.bytecode.opencsv.CSVReader;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class ImportClaimDataAS extends ImportClaimData {

	private static Pattern canada_zip = Pattern.compile("([0-9A-Z]{3}\\s{0,1}[0-9A-Z]{3})(\\s{2,}((\\w|\\s)*))?");

	final static int dateopened = 0;
	final static int fileref = 1;
	final static int inctype= 2;
	final static int pnr = 3;
	final static int lastname = 4;
	final static int firstname = 5;
	final static int address1 = 6;
	final static int city = 7;
	final static int state = 8;
	final static int zip = 9;
	final static int country = 10;
	final static int flightdate = 11;
	final static int flightairline = 12;
	final static int flightnum = 13;
	final static int from = 14;
	final static int to = 15;
	final static int segnum = 16;
	
	static List<CountryCode> countries;
	
	static Agent agent = null;
	
	static List<State> states;
	static HashMap<String,FsIncident> incMap=new HashMap<String,FsIncident>();
	static HashMap<String,Integer> statusMap=new HashMap<String,Integer>();
	
	
	@Override
	protected Agent loadAgent() {
		if(agent == null){
			GeneralServiceBean bean = new GeneralServiceBean();
			agent = bean.getAgent("ntadmin", "AS");
		}
		return agent;
	}

	protected static String getCountryCode(String country){
		if(country == null || country.trim().length() == 0){
			return "US";
		}
		if(countries == null){
			GeneralServiceBean gb = new GeneralServiceBean();
			countries = gb.getCountries();
		}
		for(CountryCode c:countries){
			if(country.equalsIgnoreCase(c.getCountry()) || country.equalsIgnoreCase(c.getCountryCode_ID())){
				return c.getCountryCode_ID();
			}
		}
		return null;
	}
	
    public static String getState(String state){
    	if(state == null || state.trim().length() == 0){
//    		System.out.println("no state provided");
    		return null;
    	} else {
    		if(states == null){
    			GeneralServiceBean gb = new GeneralServiceBean();
    			states = gb.getState();
    		}
    		for(State s:states){
    			if(s.getState_ID().equalsIgnoreCase(state) || s.getState().equalsIgnoreCase(state)){
    				return s.getState_ID();
    			}
    		}
    		if("PR".equalsIgnoreCase(state)){
    			return "PR";
    		}
    		if("KS".equalsIgnoreCase(state)){
    			return "KS";
    		}
    	}
		if(!"VI".equalsIgnoreCase(state)){
//			System.out.println("not a state: " + state);
		}
    	return null;
    }
	
	@Override
	protected void importThirdPartyClaims() {
		// TODO Auto-generated method stub
		Date start = new Date();
		Collection<Claim> claims = parseData();
		Claim currentClaim = null;
		int count = 0;
		int save = 0;
		for(Claim claim:claims){
			count++;
			if(!exists(claim.getAirlineClaimId()) && saveClaim(claim)){
//				if(saveClaim(claim)){
					save++;
					currentClaim = claim;
//				queue.add(claim.getFile());
			}
		}
		Date end = new Date();
		System.out.println("FIN: " + currentClaim.getAirlineClaimId() + " " + save + "/" + count + " " + (end.getTime() - start.getTime()));
	}

	
	private static boolean exists(String id){
		String sql = "select id from fsclaim where airline = 'AS' and airlineClaimId = :claimId";
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("claimId", id);
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
		file.setValidatingCompanycode(agent.getCompanycode_ID());
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
	
	public static void populatePerson(Person p, String[]s){		
		p.setLastName(s[lastname]);
		p.setFirstName(s[firstname]);
		populateAddress(p, s);
	}
	
	public static void populateIncident(FsIncident inc, Person p,String[]s){
		if (s[inctype].equalsIgnoreCase("L")) {
			inc.setIncidentType(TracingConstants.LOST_DELAY);
		} else if(s[inctype].equalsIgnoreCase("D")){
			inc.setIncidentType(TracingConstants.DAMAGED_BAG);
		} else if (s[inctype].equalsIgnoreCase("P")) {
			inc.setIncidentType(TracingConstants.MISSING_ARTICLES);
		}
		inc.setAirlineIncidentId(s[fileref]);
		Set passlist=new HashSet();
		passlist.add(p);
		p.setIncident(inc);
		inc.setPassengers(passlist);
		incMap.put(inc.getAirlineIncidentId(), inc);
		
	}
	
	private Collection<Claim> parseData(){
		String path = "C:\\Users\\Sean Fine\\Desktop\\Work files\\ALASKA_DATA.csv";
//		String path = "c:\\upload7\\test3.csv";
		List<String[]> list = parseCSV(filePath + "\\ALASKA_DATA.csv");
		System.out.println("file length: " + list.size());
		System.out.println(list.iterator().next()[0]);
		
		
		GeneralServiceBean bean = new GeneralServiceBean();
		Agent agent = loadAgent();
		
		HashMap<String,Claim> claims = new HashMap<String,Claim>();
		HashMap<String,Claim> incidents = new HashMap<String,Claim>();
//		ArrayList<Claim> claims = new ArrayList<Claim>();
		int i = 0;
		for(String[]s:list){
			String[] infolist=s[0].split(",");
			i++;
			if (i == 1) {
				continue;
			}
			if(i%1000==0){
				System.out.println("current count " + i);
			}
			
			if(claims.containsKey(infolist[fileref])){
				Person p = new Person();
				FsAddress a = new FsAddress();
				FsIncident inc = incMap.get(infolist[fileref]);
				p.setAddress(a);
				a.setPerson(p);
				Set<Person> pset=inc.getPassengers();
				pset.add(p);
				inc.setPassengers(pset);
				populatePerson(p,infolist);
				
				populateIncident(inc,p,infolist);
				Claim claim = (Claim)claims.get(infolist[fileref]);
				claim.getClaimants().add(p);
				p.setClaim(claim);
			} else {
				Claim claim = ClaimUtils.createClaim(agent);
				claim.setAirlineClaimId(infolist[fileref]);
//				System.out.println("DATES: " + infolist[dateopened] + " :: " + infolist[flightdate]);
				claim.setClaimDate(DateUtils.convertToDate(infolist[dateopened], "M/d/yyyy", null));
					
				claim.setAmountClaimed(0);
				claim.setAmountPaid(0);

				claim.getIncident().getReservation().getPnrData().setRecordLocator(infolist[pnr]);
				
				populatePerson(claim.getClaimant(),infolist);
				populateIncident(claim.getIncident(), claim.getClaimant(),infolist);

				aero.nettracer.fs.model.Segment seg = claim.getSegments().iterator().next();
				seg.setFlight(infolist[flightnum]);
				seg.setDeparture(infolist[from]);
				seg.setArrival(infolist[to]);
				seg.setAirline(infolist[flightairline]);
				
				String year = infolist[dateopened].split("/")[2];
				seg.setDate(DateUtils.convertToDate(infolist[flightdate] + "-" + year, "d-MMM-yyyy", null));


				claims.put(infolist[fileref],claim);
				
			}
		}
		return claims.values();
	}
	
		public static List<String[]> parseCSV(String file){
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(file), '\t');
				
				return (List<String[]>) reader.readAll();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		private static String clean(String s){
			return s.trim().replace("\"\'", "");
		}
		
	    public static boolean isIntegerParseInt(String str) {
	        try {
	            Integer.parseInt(str);
	            return true;
	        } catch (NumberFormatException nfe) {}
	        return false;
	    }
		
		private static void populateAddress(Person p, String[]s){
			
			String sAdd = s[address1];
			String sCit = s[city];
			String sSta = s[state];
			String sPos = s[zip];
			String sCt = s[country];
			
			String add = sAdd;
			String cit = sCit;
			String sta = getState(sSta);
			String pos = sPos;
			String ct = sCt;
			
			if (sta == null) {
				sta = getState(sPos);
				if (sta != null) {
					cit = sCit + " " + sSta;
					pos = sCt;
					ct = "";
				} else {
					sta = getState(sCt);
					if (sta != null) {
						add = sAdd + " " + sCit;
						cit = sSta + " " + sPos;
						pos = "";
						ct = "";
					}
				}
			}
			
			if (sta == null) {
				System.out.println("ID " + s[fileref] + " NO STATE: " + sSta + " :: " + sPos + " :: " + sCt);
				sta = sSta;
			}
			
			ct = checkCountry(ct, sSta, sCit);
			if (ct == null) {
				ct = checkCountry(pos, sSta, sCit);
				if (ct != null) {
					pos = sCt;
				} else {
					ct = checkCountry(sSta, sSta, sCit);
				}
			}
			
			if (ct == null) {
				System.out.println("ID " + s[fileref] + " NO COUNTRY: " + sSta + " :: " + sPos + " :: " + sCt);
				if (sPos.trim().length() == 3 && sCt.trim().length() == 3) {
					pos = sPos + " " + sCt;
					ct = "CA";
				}
			}
			
			p.getAddress().setAddress1(add);
			p.getAddress().setCity(cit);
			p.getAddress().setZip(pos);
			p.getAddress().setCountry(ct);
			if ("US".equalsIgnoreCase(ct)) {
				p.getAddress().setState(sta);
			} else {
				p.getAddress().setProvince(sta);
			}
			
			
		}
		
		public static String checkCountry(String ct, String sta, String cit) {
			if((ct.trim().length() == 0 || ct.trim().equalsIgnoreCase("UNITED STATES")
					|| ct.trim().equalsIgnoreCase("USA") || isIntegerParseInt(ct))
					&& getState(sta) != null){
				return "US";
			} else if (ct.trim().equalsIgnoreCase("CANADA") || ct.trim().equalsIgnoreCase("CAN")){				
				return "CA";
			} else if (ct.trim().equalsIgnoreCase("GREAT BRITAIN")){
				return "GB";
			} else if (ct.trim().equalsIgnoreCase("ENGLAND")){
				return "GB";
			} else if (ct.trim().equalsIgnoreCase("UNITED KINGDOM")){
				return "GB";
			} else if (ct.trim().equalsIgnoreCase("ST KITTS NEVIS")){
				return "KN";
			} else if (ct.trim().equalsIgnoreCase("IRAN")){
				return "IR"; 
			} else if (ct.trim().equalsIgnoreCase("ANTIGUA")){
				return "AG";
			} else if (ct.trim().equalsIgnoreCase("AUSTRALIA")){
				return "AU";
			} else if (ct.trim().equalsIgnoreCase("BRITISH V I")){
				return "VI"; 
			} else if (ct.trim().equalsIgnoreCase("VIRGIN ISLANDS") || sta.equalsIgnoreCase("VI")){
				if(cit.equalsIgnoreCase("Tortola") ||
					cit.equalsIgnoreCase("Virgin Gorda") || 
					cit.equalsIgnoreCase("Jost Van Dyke") || 
					cit.equalsIgnoreCase("Road Town") || 
					cit.equalsIgnoreCase("Anegada")){
					return "VG";
				}
				else if (cit.equalsIgnoreCase("St Croix") ||
						cit.equalsIgnoreCase("Csted St Croix") ||
						cit.equalsIgnoreCase("Cruz Bay") ||
						cit.equalsIgnoreCase("Sunny Isle") ||
						cit.equalsIgnoreCase("Kingshill St Croix") ||
						cit.equalsIgnoreCase("Frederiksted") ||
						cit.equalsIgnoreCase("Kingshill") ||
						cit.equalsIgnoreCase("Charlotte Amalie") ||
						cit.equalsIgnoreCase("Christiansted") ||
						cit.equalsIgnoreCase("Christiensted") ||
						cit.equalsIgnoreCase("St John") ||
						cit.equalsIgnoreCase("St Thomas") ||
						cit.equalsIgnoreCase("Fsted St Croix") ||
						
						cit.equalsIgnoreCase("Csted") ||
						cit.equalsIgnoreCase("St. Thomas") ||
						cit.equalsIgnoreCase("St  Croix") ||
						cit.equalsIgnoreCase("Fsted") ||
						
						sta.equalsIgnoreCase("VI")){
					return "VI";
				} else {
					return "VI";
				}
			} else if (ct.trim().equalsIgnoreCase("TAIWAN")){
				return "TW"; 
			} else if (ct.trim().equalsIgnoreCase("RUSSIA")){
				return "RU";
			} else if (ct.trim().equalsIgnoreCase("RUSSIA")){
				return "RU";
			} else if (ct.trim().equalsIgnoreCase("RU")){
				return "RU";
			} else if (ct.trim().equalsIgnoreCase("SYRIA")){
				return "SY";
			} else if (ct.trim().equalsIgnoreCase("ST LUCIA")){
				return "LC";
			} else if (ct.trim().equalsIgnoreCase("SLOVAKIA")){
				return "SK";
			} else if (ct.trim().equalsIgnoreCase("UNITED ARAB EM")){
				return "AE";
			} else if (ct.trim().equalsIgnoreCase("TRINIDAD TOBAGO")){
				return "TT";
			} else if (ct.trim().equalsIgnoreCase("TURKS CAICOS ISLND")){
				return "TC";
			} else if (ct.trim().equalsIgnoreCase("MOLDOVA")){
				return "MD";
			} else if (ct.trim().equalsIgnoreCase("UKRAINE")){
				return "UA";
			} else if (ct.trim().equalsIgnoreCase("MACEDONIA")){
				return "MK";
			} else if (ct.trim().equalsIgnoreCase("Kazakhstan")){
				return "KZ";
			} else if (ct.trim().equalsIgnoreCase("Croatia")){
				return "HR";
			} else if (ct.trim().equalsIgnoreCase("Alberta")){
				return "CA";
			} else if (ct.trim().equalsIgnoreCase("BRITISH COLUMBIA")){
				return "CA";
			} else if (ct.trim().equalsIgnoreCase("SURINAME")){
				return "SR";
			} else if (ct.trim().equalsIgnoreCase("TANZANIA")){
				return "TZ";
			} else if (ct.trim().equalsIgnoreCase("KOREA REPUBLIC")){
				return "KR";
			} else if (ct.trim().equalsIgnoreCase("TOKELAU ISLAND")){
				return "TK";
			} else {
				String ccode = getCountryCode(ct);
				if(ccode != null){
					return ccode;
				} else {
					return null;
				}
			}
		}
		
		public static void main(String[]args){

			statusMap.put("32", 0);
			statusMap.put("30", 1);
			statusMap.put("39", 2);
			statusMap.put("612", 3);
			statusMap.put("38", 4);
			statusMap.put("31", 5);
			ImportClaimDataAS importer = new ImportClaimDataAS();
			if (!importer.setVariablesFromArgs(args)) {
				System.err.println("Usage:\t" + ImportClaimDataAS.class.getSimpleName() + " [username] [password] [company] " +
						"[process(1=import, 2=third party, 3=submit to FS)] [trace active (0 or 1)] [thread count] [max return] " +
						"[queue size] [crm file path(OPTIONAL)]");
				System.err.println("Example:\t" + ImportClaimDataAS.class.getSimpleName() + " ntadmin Ladendead51! US 1 3 500 C:\\crm");
			} else {		
				importer.importClaims();
			}
			System.exit(0);
			
		}
}
