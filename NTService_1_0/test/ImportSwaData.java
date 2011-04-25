import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tools.AbqProcessor;
import tools.KeyProcessor;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.utilities.SoundexMetaphoneProcessor;
import aero.nettracer.selfservice.fraud.ClaimBean;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import au.com.bytecode.opencsv.CSVReader;

public class ImportSwaData implements KeyProcessor {

	private static final Integer zero = new Integer(0);
	static String user = null;
	static String password = null;
	// static String url = "jnp://127.0.0.1:1199";
	// static String url = "jnp://192.168.2.145:1199";
	static String url = "jnp://184.172.41.2:1199";


	public static void main(String[] args) {

		LinkedHashMap<String, ClaimDto> lhm = new LinkedHashMap<String, ClaimDto>();

		String fileName = null;
		String phoneNumbers = null;
		try {
			fileName = "2008 Claims.csv";
			phoneNumbers = "2008 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());

			fileName = "2009 Claims.csv";
			phoneNumbers = "2009 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());

			fileName = "2010 Claims.csv";
			phoneNumbers = "2010 Phone Numbers.csv";
			loadClaimsFromFile(lhm, fileName, phoneNumbers);
			System.out.println("LHM Size: " + lhm.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List files = new ArrayList<File>();

		try {
			int max = lhm.keySet().size();
			ClaimBean b = new ClaimBean();
			for (String str : lhm.keySet()) {
				ClaimDto dto = lhm.get(str);

				try {
					File f = generateFile(dto);
					files.add(f);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

		Session sess = HibernateWrapper.getSession().openSession();
		HashMap<String, Integer> alreadyProcessed = new HashMap<String, Integer>();
		String sql = "select distinct airlineIncidentId from fsincident where airline = 'WN'";
		SQLQuery query = sess.createSQLQuery(sql);
		query.addScalar("airlineIncidentId", Hibernate.STRING);
		List<String> alreadyProc = query.list();
		
		for (String str: alreadyProc) {
			alreadyProcessed.put(str, zero);
			System.out.println(str);
		}
		
		System.out.println(alreadyProc.size());
		sess.close();

		AbqProcessor processor = new AbqProcessor();
		try {
//			System.exit(0);
			processor.processQueue(files, 20, ImportSwaThread.class, new ImportSwaData(), alreadyProcessed);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static File generateFile(ClaimDto dto) {
		File f = new File();
		FsClaim c = new FsClaim();
		FsIncident i = new FsIncident();
		i.setAirlineIncidentId(dto.getId());
		c.setAirline("WN");
		i.setAirline("WN");
		c.setFile(f);
		f.setClaim(c);
		i.setClaim(c);
		c.setIncident(i);
		i.setFile(f);
		f.setIncident(i);

		LinkedHashSet<Person> persons = new LinkedHashSet<Person>();
		c.setClaimants(persons);
		Person person = new Person();
		person.setClaim(c);

		persons.add(person);
		person.setFirstName(dto.getFirstName());
		person.setLastName(dto.getLastName());
		person.setFirstNameDmp(SoundexMetaphoneProcessor.getDoubleMetaphone(dto.getFirstName()));
		person.setFirstNameSoundex(SoundexMetaphoneProcessor.getSoundex(dto.getFirstName()));
		person.setLastNameDmp(SoundexMetaphoneProcessor.getDoubleMetaphone(dto.getLastName()));
		person.setLastNameSoundex(SoundexMetaphoneProcessor.getSoundex(dto.getLastName()));

		LinkedHashSet<FsAddress> addresses = new LinkedHashSet<FsAddress>();
		person.setAddresses(addresses);
		FsAddress a = new FsAddress();
		addresses.add(a);
		a.setPerson(person);

		a.setAddress1(dto.getAddress1());
		a.setAddress2(dto.getAddress2());
		a.setCity(dto.getCity());
		a.setState(dto.getState());
		a.setCountry(dto.getCountry());
		a.setZip(dto.getZip());

		Set<aero.nettracer.fs.model.Phone> phones = new LinkedHashSet<aero.nettracer.fs.model.Phone>();
		person.setPhones(phones);

		for (Phone phon : dto.getPhone()) {
			aero.nettracer.fs.model.Phone ph = new aero.nettracer.fs.model.Phone();
			ph.setPerson(person);
			phones.add(ph);

			ph.setPhoneNumber(phon.getNumber());

		}

		return f;
	}

	private static void loadClaimsFromFile(LinkedHashMap<String, ClaimDto> lhm, String fileName, String phoneNumbers)
			throws FileNotFoundException, IOException {
		CSVReader reader = new CSVReader(new FileReader(fileName));

		String[] line = new String[0];
		while (line != null) {
			line = reader.readNext();
			if (line != null) {
				ClaimDto dto = new ClaimDto();
				int i = 0;
				dto.setReportType(line[++i]);
				dto.setId(line[++i]);
				dto.setFirstName(line[++i]);
				dto.setLastName(line[++i]);
				dto.setAddress1(line[++i]);
				dto.setAddress2(line[++i]);
				dto.setCity(line[++i]);
				dto.setState(line[++i]);
				dto.setZip(line[++i]);
				dto.setCountry(line[++i]);
				lhm.put(dto.getId(), dto);
			}

		}
		reader.close();

		reader = new CSVReader(new FileReader(phoneNumbers));

		line = new String[0];
		while (line != null) {
			line = reader.readNext();
			if (line != null) {
				String reportNumber = line[1];
				String phoneType = line[2];
				String phoneNumber = line[3] + line[4] + line[5];

				Phone phone = new Phone();
				phone.setNumber(phoneNumber);
				phone.setType(phoneType);
				try {
					ClaimDto dto = lhm.get(reportNumber);
					dto.getPhone().add(phone);
					if (dto.getPhone().size() > 1) {
						// System.out.println("*** MORE THAN 1 PHONE: "
						// + dto.getPhone().size());

					}

				} catch (Exception e) {
					System.out.println("Failed to load: " + reportNumber);
					e.printStackTrace();
				}
			}

		}
		reader.close();

		System.out.println("Done loading claims and phone numbers for: " + fileName + " and " + phoneNumbers);

	}

	@Override
	public String generateKey(Object o) {
		File f = (File) o;
		return f.getIncident().getAirlineIncidentId();
	}

}
