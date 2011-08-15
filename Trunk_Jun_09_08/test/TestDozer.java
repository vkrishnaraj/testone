import java.util.LinkedHashSet;
import java.util.Set;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCBag;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCContents;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCItinerary;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPassenger;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCPhone;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;
import com.bagnet.nettracer.ws.onlineclaims.Bag;
import com.bagnet.nettracer.ws.onlineclaims.Claim;
import com.bagnet.nettracer.ws.onlineclaims.Contents;
import com.bagnet.nettracer.ws.onlineclaims.Itinerary;


public class TestDozer {

//	@org.junit.Test
//	public void testCopy() {
//		Person person = new Person();
//		person.setEmailAddress("Test");
//		Person newPerson = person;
//		newPerson.setEmailAddress("TEST2");
//		System.out.println();
//	}

	private static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	
	@org.junit.Test
	public void testDozerMapping() {
		Claim claim = new Claim();
		
		Bag[] bags = new Bag[3];
		for (int i = 0; i < 3; i++) {
			Bag bag = new Bag();
			bag.setBagValue(i + "" + i + "" + i);
			Contents[] conts = new Contents[6];
			for (int j = 0; j < 6; j++) {
				Contents cont = new Contents();
				cont.setBrand(j + "" + j + "" + j);
				conts[j] = cont;
			}
			bag.setContents(conts);
			bags[i] = bag;
		}
		claim.setBag(bags);
		
		Itinerary[] itins = new Itinerary[6];
		for (int i = 0; i < 6; i++) {
			Itinerary itin = new Itinerary();
			itin.setFlightNum(i + "" + i + "" + i);
			itins[i] = itin;
		}
		claim.setItinerary(itins);
		
		mapAndCheck(claim);
		mapAndCheck(claim);
		mapAndCheck(claim);
		
		OnlineClaimsDao dao = new OnlineClaimsDao();
		OnlineClaim oc = dao.getOnlineClaim(24);
		String tmp = "OC MAPPED ";
		for (OCItinerary itin : oc.getItinerary()) {
			tmp += itin.getFlightNum() + " ";
		}
		tmp += "TO";
		ocMapAndCheck(oc, tmp);
		ocMapAndCheck(oc, tmp);
		ocMapAndCheck(oc, tmp);
		
	}
	
	private void mapAndCheck(Claim claim) {
		OnlineClaim ocFrame = new OnlineClaim();
		ocFrame.setFile(new LinkedHashSet<OCFile>());
		ocFrame.setItinerary(new LinkedHashSet<OCItinerary>());
		ocFrame.setPassenger(new LinkedHashSet<OCPassenger>());
		ocFrame.setPhone(new LinkedHashSet<OCPhone>());
		mapper.map(claim, ocFrame);
		
		Set<OCBag> bagsFrame = new LinkedHashSet<OCBag>();
		for (Bag bag : claim.getBag()) {
			OCBag bagFrame = new OCBag();
			bagFrame.setContents(new LinkedHashSet<OCContents>());
			mapper.map(bag, bagFrame);
			bagsFrame.add(bagFrame);
		}
		ocFrame.setBag(bagsFrame);
		
		Set<OCBag> ocbags = ocFrame.getBag();
		System.out.println("MAPPED 000 111 222 333 444 555 TO");
		for (OCBag bag : ocbags) {
			if (bag != null) {
				System.out.print("BAG" + bag.getBagValue() + " ");
				if (bag.getContents() != null) {
					for (OCContents cont : bag.getContents()) {
						System.out.print(cont.getBrand() + " ");
					}
				}
				System.out.println();
			}
		}
		Set<OCItinerary> ocitins = ocFrame.getItinerary();
		System.out.print("ITINS  ");
		for (OCItinerary itin : ocitins) {
			System.out.print(itin.getFlightNum() + " ");
		}
		System.out.println(" ");
		System.out.println(" ");
	}
	
	private void ocMapAndCheck(OnlineClaim claim, String tmp) {
		Claim oc = mapper.map(claim, Claim.class);
		Itinerary[] ocitins = oc.getItinerary();
		System.out.println(tmp);
		System.out.print("          ");
		for (Itinerary itin : ocitins) {
			System.out.print(itin.getFlightNum() + " ");
		}
		System.out.println(" ");
	}
	
}
