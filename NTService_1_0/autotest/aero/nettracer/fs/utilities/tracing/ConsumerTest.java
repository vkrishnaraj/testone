package aero.nettracer.fs.utilities.tracing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.junit.Test;

import aero.nettracer.fs.model.CreditCard;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchHistory;

public class ConsumerTest {
	@Test
	public void testProcessPersonName(){
		MatchHistory match = createMatchHistory(new File(), new File());
		
		//Direct Name Match - single match, score 20
		Person p1 = createPerson("Patrick","Schneider","F");
		Person p2 = createPerson("Patrick","Schneider","F");
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		MatchDetail detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() == 20.0);
		assertTrue(Consumer.EXACT_NAME_MATCH.equals(detail.getDescription()));
		
		//Similar name match - single match, score [18,20)
		p1.setFirstName("Patrick F");
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() >= 18 && detail.getPercent() < 20);
		assertTrue(Consumer.SIMILAR_NAME.equals(detail.getDescription()));
		
		//Nick Name Match - single match, score 10
		p1.setFirstName("Pat");
//		p1.setParent(createPerson("Patrick","Schneider","F"));
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() == 10.0);
		assertTrue(Consumer.EXACT_NICKNAME_MATCH.equals(detail.getDescription()));
		
		//Similar nick name match - single match, score [9,10)
		p1.setFirstName("Patrick");
		p2.setFirstName("Pat F");
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() >= 9.0 && detail.getPercent() < 10);
		assertTrue(Consumer.SIMILAR_NICKNAME_MATCH.equals(detail.getDescription()));
		
		//SoundEx - single match, score 4
		p1.setFirstName("James");
		p1.setParent(null);
		p1.setFirstNameSoundex("AAAA");
		p2.setFirstNameSoundex("AAAA");
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() == 4);
		assertTrue(Consumer.SOUNDEX_MATCH.equals(detail.getDescription()));
		
		//DMP - single match, score 4
		p1.setFirstName("James");
		p1.setParent(null);
		p1.setFirstNameDmp("AAAA");
		p2.setFirstNameDmp("AAAA");
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
		detail = match.getDetails().iterator().next();
		assertTrue(detail.getPercent() == 4);
		assertTrue(Consumer.DOUBLE_METAPHONE_MATCH.equals(detail.getDescription()));
		
		
//		//SoundEx nick name - single match, score 2
//		p1.setFirstName("James");
////		p1.setParent(createPerson("Patrick","Schneider","F"));
//		p1.setFirstNameSoundex("AAAA");
//		p2.setFirstNameSoundex("AAAA");
//		match.setDetails(new HashSet<MatchDetail>());
//		Consumer.processName(match,p1,p2);
//		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
//		detail = match.getDetails().iterator().next();
//		assertTrue(detail.getPercent() == 2);
//		assertTrue(Consumer.SOUNDEX_NICKNAME_MATCH.equals(detail.getDescription()));
//		
//		//DMP nick name - single match, score 2
//		p1.setFirstName("James");
////		p1.setParent(createPerson("Patrick","Schneider","F"));
//		p1.setFirstNameDmp("AAAA");
//		p2.setFirstNameDmp("AAAA");
//		match.setDetails(new HashSet<MatchDetail>());
//		Consumer.processName(match,p1,p2);
//		assertTrue(match.getDetails() != null && match.getDetails().size() == 1);
//		detail = match.getDetails().iterator().next();
//		assertTrue(detail.getPercent() == 2);
//		assertTrue(Consumer.DOUBLE_METAPHONE_NICKNAME_MATCH.equals(detail.getDescription()));
		
		//SoundEx and DMP - 2 matches total score 8
		p1.setFirstName("James");
		p1.setParent(null);
		p1.setFirstNameSoundex("AAAA");
		p1.setFirstNameDmp("AAAA");
		p2.setFirstNameSoundex("AAAA");
		p2.setFirstNameDmp("AAAA");
		match.setDetails(new HashSet<MatchDetail>());
		Consumer.processName(match,p1,p2);
		assertTrue(match.getDetails() != null && match.getDetails().size() == 2);
		Iterator<MatchDetail> i = match.getDetails().iterator();
		detail = i.next();
		double totalscore = detail.getPercent();
		detail = i.next();
		totalscore += detail.getPercent();
		assertTrue(totalscore == 8);
		
//		//SoundEx and DMP nick name - 2 matches total score 4
//		p1.setFirstName("James");
////		p1.setParent(createPerson("Patrick","Schneider","F"));
//		p1.setFirstNameSoundex("AAAA");
//		p1.setFirstNameDmp("AAAA");
//		p2.setFirstNameSoundex("AAAA");
//		p2.setFirstNameDmp("AAAA");
//		match.setDetails(new HashSet<MatchDetail>());
//		Consumer.processName(match,p1,p2);
//		assertTrue(match.getDetails() != null && match.getDetails().size() == 2);
//		i = match.getDetails().iterator();
//		detail = i.next();
//		totalscore = detail.getPercent();
//		detail = i.next();
//		totalscore += detail.getPercent();
//		assertTrue(totalscore == 4);

		
	}
	
	@Test
	public void testProcessPerson(){
		File f1 = createFile();
		File f2 = createFile();
		
		//single name - 1 match
		MatchHistory match = createMatchHistory(f1,f2);
		Consumer.processPerson(match);
		assertTrue(match.getDetails().size()==1);
		
		//Multi same name - 1 match
		f1.getClaims().iterator().next().setClaimants(new HashSet<Person>());
		f2.getClaims().iterator().next().setClaimants(new HashSet<Person>());
		f1.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		f2.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		f1.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		f2.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		match = createMatchHistory(f1,f2);
		Consumer.processPerson(match);
		assertTrue(match.getDetails().size()==1);
		
		//Multi different names - 2 matches
		f1.getClaims().iterator().next().setClaimants(new HashSet<Person>());
		f2.getClaims().iterator().next().setClaimants(new HashSet<Person>());
		f1.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		f2.getClaims().iterator().next().getClaimants().add(createPerson("John","Doe","F"));
		f1.getClaims().iterator().next().getClaimants().add(createPerson("Chris","Fine","F"));
		f2.getClaims().iterator().next().getClaimants().add(createPerson("Chris","Fine","F"));
		match = createMatchHistory(f1,f2);
		Consumer.processPerson(match);
		assertTrue(match.getDetails().size()==2);
	}
	
	@Test
	public void testProcessCC(){
		MatchHistory mh = new MatchHistory();
		CreditCard c1 = new CreditCard();
		CreditCard c2 = new CreditCard();
		String cc1 = "1234123412341234";
		String cc2 = "4321432143214321";
		String last4a = "1234";
		String last4b = "4321";
		
		
		c1.setCcNumber(cc1);
		c2.setCcNumber(cc1);
		c1.setCcNumLastFour(last4a);
		c2.setCcNumLastFour(last4a);
		c1.setCcExpMonth(2);
		c2.setCcExpMonth(2);
		c1.setCcExpYear(12);
		c2.setCcExpYear(12);
		c1.setCcType("VI");
		c2.setCcType("VI");
		
		//full 16 match
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Full Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//16 and type with null expiration
		c2.setCcExpYear(0);
		c2.setCcExpMonth(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Number and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//16 and type with different expiration
		c2.setCcExpYear(14);
		c2.setCcExpMonth(6);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Number and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//16 and exp with null type
		c2.setCcExpYear(14);
		c2.setCcExpMonth(6);
		c1.setCcExpYear(14);
		c1.setCcExpMonth(6);
		c1.setCcType(null);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Number and Expiration Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//16 and exp with different type
		c2.setCcExpYear(14);
		c2.setCcExpMonth(6);
		c1.setCcExpYear(14);
		c1.setCcExpMonth(6);
		c1.setCcType("VI");
		c2.setCcType("AX");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Number and Expiration Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//16 only
		c2.setCcExpYear(0);
		c2.setCcExpMonth(0);
		c1.setCcExpYear(0);
		c1.setCcExpMonth(0);
		c1.setCcType("VI");
		c2.setCcType("AX");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card Number Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 full match
		c1.setCcNumber(null);
		c2.setCcNumber(null);
		c1.setCcNumLastFour(last4a);
		c2.setCcNumLastFour(last4a);
		c1.setCcExpMonth(6);
		c2.setCcExpMonth(6);
		c1.setCcExpYear(14);
		c2.setCcExpYear(14);
		c1.setCcType("VI");
		c2.setCcType("VI");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit, type and expiration Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c1 null exp year
		c1.setCcExpYear(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c2 null exp year
		c1.setCcExpYear(14);
		c2.setCcExpYear(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c1 null exp month
		c1.setCcExpYear(14);
		c2.setCcExpYear(14);
		c1.setCcExpMonth(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c1 null exp month/yeah
		c1.setCcExpYear(0);
		c2.setCcExpYear(14);
		c1.setCcExpMonth(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c1/c2 null exp
		c1.setCcExpYear(0);
		c2.setCcExpYear(0);
		c1.setCcExpMonth(0);
		c2.setCcExpMonth(0);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Type Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 type c1/c2 exp diff
		c1.setCcExpYear(14);
		c2.setCcExpYear(12);
		c1.setCcExpMonth(6);
		c2.setCcExpMonth(6);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 0);
		
		//4 exp c1 type null
		c1.setCcExpYear(14);
		c2.setCcExpYear(14);
		c1.setCcExpMonth(6);
		c2.setCcExpMonth(6);
		c1.setCcType(null);
		c2.setCcType("VI");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Expiration Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 exp c2 type null
		c2.setCcType(null);
		c1.setCcType("VI");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit and Expiration Match".equals(mh.getDetails().iterator().next().getDescription()));
		
		//4 exp type diff
		c2.setCcType("AX");
		c1.setCcType("VI");
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 0);
		
		//4 only
		c1.setCcExpYear(0);
		c2.setCcExpYear(0);
		c1.setCcExpMonth(0);
		c2.setCcExpMonth(0);
		c1.setCcType(null);
		c2.setCcType(null);
		mh.setDetails(new LinkedHashSet<MatchDetail>());
		Consumer.proccessCreditCard(c1, c2, mh);
		assertTrue(mh.getDetails() != null && mh.getDetails().size() == 1 
				&& "Credit Card 4 digit Match".equals(mh.getDetails().iterator().next().getDescription()));
		
	}
	
	private static File createFile(){
		File file = new File();
		HashSet<FsClaim>claims = new HashSet<FsClaim>();
		FsClaim claim = createClaim();
		claim.setFile(file);
		claims.add(claim);
		file.setClaims(claims);
		return file;
	}
	
	private static FsClaim createClaim(){
		FsClaim claim = new FsClaim();
		HashSet<Person> claimants = new HashSet<Person>();
		claimants.add(createPerson("John","Doe","F"));
		claim.setClaimants(claimants);
		return claim;
	}
	
	private static Person createPerson(String firstName, String lastName, String middleName){
		Person person = new Person();
		person.setLastName(lastName);
		person.setFirstName(firstName);
		person.setMiddleName(middleName);
		
		return person;
	}
	
	private static MatchHistory createMatchHistory(File f1, File f2){
		MatchHistory match = new MatchHistory();
		match.setFile1(f1);
		match.setFile2(f2);
		match.setCreatedate(new Date());
		match.setDetails(new HashSet<MatchDetail>());
		return match;
	}
	
}
