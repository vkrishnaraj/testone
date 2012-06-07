package aero.nettracer.fs.utilities.tracing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.Person;
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
