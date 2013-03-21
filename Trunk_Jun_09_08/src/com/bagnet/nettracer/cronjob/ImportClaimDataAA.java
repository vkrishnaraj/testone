package com.bagnet.nettracer.cronjob;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.general.services.GeneralServiceBean;
import au.com.bytecode.opencsv.CSVReader;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

public class ImportClaimDataAA extends ImportClaimData {

	private static Pattern canada_zip = Pattern.compile("([0-9A-Z]{3}\\s{0,1}[0-9A-Z]{3})(\\s{2,}((\\w|\\s)*))?");
	
	final static int lastname = 3;
	final static int firstname = 4;
	final static int middlename = 5;
	final static int address1 = 9;
	final static int address2 = 10;
	final static int city = 11;
	final static int state = 12;
	final static int zip = 13;
	final static int country = 14;
	final static int from = 15;
	final static int to = 16;
	final static int phone1 = 17;
	final static int phone2 = 18;
	final static int phone3 = 19;
	final static int pnr = 1;
	
	static List<CountryCode> countries;
	
	static Agent agent = null;
	
	static List<State> states;
	
	@Override
	protected Agent loadAgent() {
		if(agent == null){
			GeneralServiceBean bean = new GeneralServiceBean();
			agent = bean.getAgent("ntadmin", "AA");
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
			if(country.equalsIgnoreCase(c.getCountry())){
				return c.getCountryCode_ID();
			}
		}
		return null;
	}
	
    public static boolean hasState(String state){
    	if(state == null || state.trim().length() == 0){
    		System.out.println("no state provided");
    		return false;
    	} else {
    		if(states == null){
    			GeneralServiceBean gb = new GeneralServiceBean();
    			states = gb.getState();
    		}
    		for(State s:states){
    			if(s.getState_ID().equalsIgnoreCase(state)){
    				return true;
    			}
    		}
    		if("PR".equalsIgnoreCase(state)){
    			return true;
    		}
    		if("KS".equalsIgnoreCase(state)){
    			return true;
    		}
    	}
		if(!"VI".equalsIgnoreCase(state)){
			System.out.println("not a state: " + state);
		}
    	return false;
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
		String sql = "select id from fsclaim where airline = 'AA' and airlineClaimId = :claimId";
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
		p.setMiddleName(s[middlename]);

		populateAddress(p, s);

		p.setHomePhone(s[phone1]);
		p.setWorkPhone(s[phone2]);
		p.setMobilePhone(s[phone3]);
	}
	
	private Collection<Claim> parseData(){
		String path = "c:\\upload7\\CBS2009-12-AS400RecordsFile-all3.txt";
//		String path = "c:\\upload7\\test3.csv";
		List<String[]> list = parseCSV(filePath + "\\CBS2009-12-AS400RecordsFile-all3.txt");
		System.out.println("file length: " + list.size());
		System.out.println(list.iterator().next()[0]);
		
		
		GeneralServiceBean bean = new GeneralServiceBean();
		Agent agent = loadAgent();
		
		HashMap<String,Claim> claims = new HashMap<String,Claim>();
//		ArrayList<Claim> claims = new ArrayList<Claim>();
		int i = 0;
		for(String[]s:list){
			i++;
//			if(i%1000==0){
//				System.out.println("current count " + i);
//			}
			
			if(claims.containsKey(s[0])){
				Person p = new Person();
				FsAddress a = new FsAddress();
				p.setAddress(a);
				a.setPerson(p);
				populatePerson(p,s);
				Claim claim = (Claim)claims.get(s[0]);
				claim.getClaimants().add(p);
				p.setClaim(claim);
			} else {
			
				Claim claim = ClaimUtils.createClaim(agent);

				claim.setAirlineClaimId(s[0]);

				claim.setAmountClaimed(0);//TODO claimed or paid
				//			claim.setAmountPaid(new Double(s[1]));

				claim.getIncident().getReservation().getPnrData().setRecordLocator(s[pnr]);

				populatePerson(claim.getClaimant(), s);

				aero.nettracer.fs.model.Segment seg = claim.getSegments().iterator().next();
				seg.setDeparture(s[from]);
				seg.setArrival(s[to]);


				claims.put(s[0],claim);
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

			
			p.getAddress().setAddress1(s[address1]);
			p.getAddress().setAddress2(s[address2]);
			p.getAddress().setCity(s[city]);
			p.getAddress().setZip(s[zip]);
			
			String c = s[country];
			String ct = c.split("\\s{2,}")[0];
			
			if((ct.trim().length() == 0 || ct.trim().equalsIgnoreCase("UNITED STATES")
					|| ct.trim().equalsIgnoreCase("USA") || isIntegerParseInt(ct))
					&& hasState(s[state])){//US addr
				p.getAddress().setState(s[state]);
				p.getAddress().setCountry("US");
			} else if (ct.trim().equalsIgnoreCase("CANADA")){
				Matcher matches = canada_zip.matcher(s[zip]);
				if(matches.find()){
					p.getAddress().setZip(matches.group(1));
					p.getAddress().setProvince(matches.group(3));
				} else {
					p.getAddress().setZip(null);
				}
				
				p.getAddress().setCountry("CA");
			} else if (ct.trim().equalsIgnoreCase("GREAT BRITAIN")){
				p.getAddress().setCountry("GB");
			} else if (ct.trim().equalsIgnoreCase("ENGLAND")){
				//TODO zip
				p.getAddress().setCountry("GB");
			} else if (ct.trim().equalsIgnoreCase("UNITED KINGDOM")){
				p.getAddress().setCountry("GB");
			} else if (ct.trim().equalsIgnoreCase("ST KITTS NEVIS")){
				p.getAddress().setCountry("KN");
			} else if (ct.trim().equalsIgnoreCase("IRAN")){
				p.getAddress().setCountry("IR"); 
			} else if (ct.trim().equalsIgnoreCase("ANTIGUA")){
				p.getAddress().setCountry("AG"); 
			} else if (ct.trim().equalsIgnoreCase("BRITISH V I")){
				p.getAddress().setCountry("VI"); 
			} else if (ct.trim().equalsIgnoreCase("VIRGIN ISLANDS") || s[state].equalsIgnoreCase("VI")){
				if(s[city].equalsIgnoreCase("Tortola") ||
					s[city].equalsIgnoreCase("Virgin Gorda") || 
					s[city].equalsIgnoreCase("Jost Van Dyke") || 
					s[city].equalsIgnoreCase("Road Town") || 
					s[city].equalsIgnoreCase("Anegada")){
					p.getAddress().setCountry("VG");
				}
				else if (s[city].equalsIgnoreCase("St Croix") ||
						s[city].equalsIgnoreCase("Csted St Croix") ||
						s[city].equalsIgnoreCase("Cruz Bay") ||
						s[city].equalsIgnoreCase("Sunny Isle") ||
						s[city].equalsIgnoreCase("Kingshill St Croix") ||
						s[city].equalsIgnoreCase("Frederiksted") ||
						s[city].equalsIgnoreCase("Kingshill") ||
						s[city].equalsIgnoreCase("Charlotte Amalie") ||
						s[city].equalsIgnoreCase("Christiansted") ||
						s[city].equalsIgnoreCase("Christiensted") ||
						s[city].equalsIgnoreCase("St John") ||
						s[city].equalsIgnoreCase("St Thomas") ||
						s[city].equalsIgnoreCase("Fsted St Croix") ||
						
						s[city].equalsIgnoreCase("Csted") ||
						s[city].equalsIgnoreCase("St. Thomas") ||
						s[city].equalsIgnoreCase("St  Croix") ||
						s[city].equalsIgnoreCase("Fsted") ||
						
						s[state].equalsIgnoreCase("VI")){
					p.getAddress().setCountry("VI");
				} else {
					System.out.println(s[0] + " " + ct + ":" + s[city]);
				}
			} else if (ct.trim().equalsIgnoreCase("TAIWAN")){
				p.getAddress().setCountry("TW"); 
			} else if (ct.trim().equalsIgnoreCase("RUSSIA")){
				p.getAddress().setCountry("RU");
			} else if (ct.trim().equalsIgnoreCase("RUSSIA")){
				p.getAddress().setCountry("RU");
			} else if (ct.trim().equalsIgnoreCase("RU")){
				p.getAddress().setCountry("RU");
			} else if (ct.trim().equalsIgnoreCase("SYRIA")){
				p.getAddress().setCountry("SY");
			} else if (ct.trim().equalsIgnoreCase("ST LUCIA")){
				p.getAddress().setCountry("LC");
			} else if (ct.trim().equalsIgnoreCase("SLOVAKIA")){
				p.getAddress().setCountry("SK");
			} else if (ct.trim().equalsIgnoreCase("UNITED ARAB EM")){
				p.getAddress().setCountry("AE");
			} else if (ct.trim().equalsIgnoreCase("TRINIDAD TOBAGO")){
				p.getAddress().setCountry("TT");
			} else if (ct.trim().equalsIgnoreCase("TURKS CAICOS ISLND")){
				p.getAddress().setCountry("TC");
			} else if (ct.trim().equalsIgnoreCase("MOLDOVA")){
				p.getAddress().setCountry("MD");
			} else if (ct.trim().equalsIgnoreCase("UKRAINE")){
				p.getAddress().setCountry("UA");
			} else if (ct.trim().equalsIgnoreCase("MACEDONIA")){
				p.getAddress().setCountry("MK");
			} else if (ct.trim().equalsIgnoreCase("Kazakhstan")){
				p.getAddress().setCountry("KZ");
			} else if (ct.trim().equalsIgnoreCase("Croatia")){
				p.getAddress().setCountry("HR");
			} else if (ct.trim().equalsIgnoreCase("Alberta")){
				p.getAddress().setCountry("CA");
			} else if (ct.trim().equalsIgnoreCase("BRITISH COLUMBIA")){
				p.getAddress().setCountry("CA");
			} else if (ct.trim().equalsIgnoreCase("SURINAME")){
				p.getAddress().setCountry("SR");
			} else if (ct.trim().equalsIgnoreCase("TANZANIA")){
				p.getAddress().setCountry("TZ");
			} else if (ct.trim().equalsIgnoreCase("KOREA REPUBLIC")){
				p.getAddress().setCountry("KR");
			} else if (ct.trim().equalsIgnoreCase("TOKELAU ISLAND")){
				p.getAddress().setCountry("TK");
			} else {
				String ccode = getCountryCode(ct);
				if(ccode != null){
					p.getAddress().setCountry(ccode);
				} else {
					p.getAddress().setCountry(null);
					System.out.println(s[0] + ": " + ct);
				}
			}
		}
		
		public static void main(String[]args){
			
			ImportClaimDataAA importer = new ImportClaimDataAA();
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
